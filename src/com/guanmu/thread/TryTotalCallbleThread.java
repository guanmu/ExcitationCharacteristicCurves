/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
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
	
	private List<PointValue> pointValues;
	
	private double precision;
	
	/**
	 * @param monitor
	 * @param pointValues
	 * @param precision
	 */
	public TryTotalCallbleThread(CurvesProgressMonitor monitor, List<PointValue> pointValues, double precision) {
		super();
		this.monitor = monitor;
		this.pointValues = pointValues;
		this.precision = precision;
	}



	@Override
	public List<ExFunction> call() throws Exception {
		logger.info("###start try total function");
		
		ExecutorService nearTryExec = Executors.newCachedThreadPool();
		
		List<Future<ExFunction>> tryResults = new ArrayList<>();
		
		double min = 0;
		for (double max = 10;max <= ExConfig.MAX_A;max = max + 10) {
			
			logger.info("NearTryCallbleThread submit before.[{},{}]",min,max);
			Future<ExFunction> tryResult = nearTryExec.submit(new NearTryCallbleThread(monitor,pointValues,precision,min,max));
			logger.info("NearTryCallbleThread submit after.[{},{}]",min,max);
			
			tryResults.add(tryResult);
			
			min = max;
		}
		
		
		nearTryExec.shutdown();
		
		List<ExFunction> functionResult = new ArrayList<>();
		if (nearTryExec.awaitTermination(10, TimeUnit.MINUTES)) {
			
			if (tryResults.isEmpty()) {
				logger.info("tryResults is empty.");
			} else {
				for(int i = 0;i < tryResults.size();i++) {
					Future<ExFunction> tryResult = tryResults.get(i); 
					ExFunction function = tryResult.get();
					
					logger.info("###try result{}:{}",i,function);
					
					if (function != null) {
						functionResult.add(function);						
					}
				}
			}
			
		} else {
			logger.error("try compute time out.");
		}
		
		return functionResult;
	}

}
