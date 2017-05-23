/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.thread
 * @author wangquan 2017-5-22
 * 
 */
public class TryTotalCallbleThread  implements Callable<List<ExFunction>> {

	private static final Logger logger = RootLogger.getLog(TryTotalCallbleThread.class.getName());	

	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double precision;
	
	/**
	 * @param monitor
	 * @param pointValues
	 * @param precision
	 */
	public TryTotalCallbleThread(CurvesProgressMonitor monitor, PointData pointData, double precision) {
		super();
		this.monitor = monitor;
		this.pointData = pointData;
		this.precision = precision;
	}



	@Override
	public List<ExFunction> call() throws Exception {
		Thread.currentThread().setName("TryTotalCallbleThread");
		logger.info("###start try total function");
		
		double deterCoeff = new ExFunction(61.37, 0.02292, 0.000003053, 0.1417,pointData).getDeterCoeff();
		System.out.println(deterCoeff);
		
		long startTime = new Date().getTime();
		
		ExecutorService nearTryExec = Executors.newCachedThreadPool();
		
		List<Future<ExFunction>> tryResults = new ArrayList<>();
		
		double min = 0;
		for (double max = 1;max <= ExConfig.MAX_A;max = max + 1) {
			
			logger.info("NearTryCallbleThread submit before.[{},{}]",min,max);
			Future<ExFunction> tryResult = nearTryExec.submit(new NearTryCallbleThread(monitor,pointData,precision,min,max));
			logger.info("NearTryCallbleThread submit after.[{},{}]",min,max);
			
			tryResults.add(tryResult);
			
			min = max;
		}
		
		
		nearTryExec.shutdown();
		
		ExFunction function1 = null;
		ExFunction function2 = null;
		ExFunction function3 = null;
		
		List<ExFunction> functionResult = new ArrayList<>();
		if (nearTryExec.awaitTermination(10, TimeUnit.MINUTES)) {
			
			long endTime = new Date().getTime();
			
			logger.info("$$$total time:" + (endTime - startTime)/1000 + "s");
			
			if (tryResults.isEmpty()) {
				logger.info("tryResults is empty.");
			} else {
				for(int i = 0;i < tryResults.size();i++) {
					Future<ExFunction> tryResult = tryResults.get(i); 
					ExFunction function = tryResult.get();
					
					logger.info("###try result[{}]:{}",i,function);
					
					if (function != null) {
						functionResult.add(function);		
						
						if (function1 == null) {
							function1 = function;
						} else {
							if (function.getDeterCoeff() > function1.getDeterCoeff()) {
								function3 = function2;
								function2 = function1;
								
								function1 = function;
							}
						}
						
						if (function2 == null) {
							if (function != function1) {
								function2 = function;
							}
						} else {
							if (function.getDeterCoeff() > function2.getDeterCoeff()) {
								function3 = function2;
								
								function2 = function;
							}
						}
						
						if (function3 == null) {
							if (function != function1 && function != function2) {
								function3 = function;
							}
						} else {
							if (function.getDeterCoeff() > function3.getDeterCoeff()) {
								function3 = function;
							}
						}
					}
				}
			}
			
		} else {
			logger.error("try compute time out.");
		}
		
		logger.info("function1:" + function1);
		logger.info("function2:" + function2);
		logger.info("function3:" + function3);
		
		
		return functionResult;
	}

}
