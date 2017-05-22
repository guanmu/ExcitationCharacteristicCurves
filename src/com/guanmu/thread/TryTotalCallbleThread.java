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

import com.guanmu.model.ExcitationFunction;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExcitationConfig;
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
public class TryTotalCallbleThread  implements Callable<List<ExcitationFunction>> {

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
	public List<ExcitationFunction> call() throws Exception {
		logger.info("###start try total function");
		
		ExecutorService tryExec = Executors.newCachedThreadPool();
		
		List<Future<ExcitationFunction>> tryResults = new ArrayList<>();
		
		double min = 0;
		for (double max = 10;max <= ExcitationConfig.MAX_A;max = max + 10) {
			
			logger.info("TryCallbleThread submit before.[{},{}]",min,max);
			Future<ExcitationFunction> tryResult = tryExec.submit(new TryCallbleThread(monitor,pointValues,precision,min,max));
			logger.info("TryCallbleThread submit after.[{},{}]",min,max);
			
			tryResults.add(tryResult);
			
			min = max;
		}
		
		
		tryExec.shutdown();
		
		List<ExcitationFunction> functionResult = new ArrayList<>();
		if (tryExec.awaitTermination(30, TimeUnit.MINUTES)) {
			
			if (tryResults.isEmpty()) {
				logger.info("tryResults is empty.");
			} else {
				for(int i = 0;i < tryResults.size();i++) {
					Future<ExcitationFunction> tryResult = tryResults.get(i); 
					ExcitationFunction function = tryResult.get();
					
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
