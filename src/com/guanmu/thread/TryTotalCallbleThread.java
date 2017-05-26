/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;
import com.guanmu.utils.SortMethod;
import com.guanmu.utils.SortMethod.FunctionSortByAB;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.thread
 * @author wangquan 2017-5-22
 * 
 */
public class TryTotalCallbleThread  implements Callable<ExFunction> {

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
	public ExFunction call() throws Exception {
		Thread.currentThread().setName("TryTotalCallbleThread");
		logger.info("###start try total function");
		
		long startTime = new Date().getTime();
		
		ExecutorService nearTryExec = ExThreadPool.getInstance().getNearTyExec();
		
		List<Future<ExFunction>> nearTryResults = new ArrayList<>();
		
		double min = 0;
		for (double max = 1;max <= ExConfig.MAX_A;max = max + 1) {
			
			logger.info("NearTryCallbleThread submit before.[{},{}]",min,max);
			ExFunction minF = new ExFunction(min, 0, 0, 0);
			ExFunction maxF = new ExFunction(max, 0, 0, 0);
			Future<ExFunction> tryResult = nearTryExec.submit(new NearTryCallbleThread(monitor,pointData,precision,minF,maxF,true,10));
			logger.info("NearTryCallbleThread submit after.[{},{}]",min,max);
			
			nearTryResults.add(tryResult);
			
			min = max;
		}
		
		nearTryExec.shutdown();
		if (!nearTryExec.awaitTermination(10, TimeUnit.MINUTES)) {
			logger.error("try compute time out.");
		}
		
		long fistNearEndTime = new Date().getTime();
		logger.info("$$$first near time:" + (fistNearEndTime - startTime)/1000 + "s");
		
		int digit = 100;
		
		int firstError = 0;
		List<ExFunction> firstFunctions = new ArrayList<>();
		for(Future<ExFunction> future : nearTryResults) {
			
			if (!future.isDone()) {
				firstError++;
				continue;
			}
			
			ExFunction function = future.get();
			if (function == null) {
				firstError++;
				continue;
			}
			
			
			firstFunctions.add(function);
		}
		
		if (firstError > 0) {
			logger.error("firstError number.[{}]",firstError);
		}
		
		// 去掉null元素
		firstFunctions.removeAll(Collections.singleton(null));
		
		if (!firstFunctions.isEmpty()) {
			Collections.sort(firstFunctions, new SortMethod.FunctionSortByDeterCoeffMax());			
			ExFunction firstNearFunction = firstFunctions.get(0); 
			
			if (firstNearFunction != null && firstNearFunction.getDeterCoeff() >= precision) {
				logger.debug("firstNearFunction meet precision.[{}]",firstNearFunction);
				return firstNearFunction;
			}
			
			Collections.sort(firstFunctions, new SortMethod.FunctionSortByParam());
			ExecutorService tryExec = ExThreadPool.getInstance().getNearTyExec();
			
			List<Future<ExFunction>> tryFutures = new ArrayList<>();
			Future<ExFunction> result0 = tryExec.submit(new NearTryCallbleThread(monitor, pointData, precision, null,firstFunctions.get(0),false,digit));
			tryFutures.add(result0);
			
			for(int i = 0;i < firstFunctions.size() - 1;i++) {
				Future<ExFunction> result = tryExec.submit(new NearTryCallbleThread(monitor, pointData, precision, firstFunctions.get(i),firstFunctions.get(i + 1),true,digit));
				tryFutures.add(result);
			}
			
			Future<ExFunction> result5 = tryExec.submit(new NearTryCallbleThread(monitor, pointData, precision, firstFunctions.get(firstFunctions.size() - 1),null,true,digit));
			tryFutures.add(result5);			
		}

		
		
		
		ExFunction function1 = null;
		ExFunction function2 = null;
		ExFunction function3 = null;
		
		List<ExFunction> functionResult = new ArrayList<>();
		if (nearTryExec.awaitTermination(10, TimeUnit.MINUTES)) {
			
			long endTime = new Date().getTime();
			
			logger.info("$$$total time:" + (endTime - startTime)/1000 + "s");
			
			if (nearTryResults.isEmpty()) {
				logger.info("tryResults is empty.");
			} else {
				for(int i = 0;i < nearTryResults.size();i++) {
					Future<ExFunction> tryResult = nearTryResults.get(i); 
					ExFunction function = tryResult.get();
					
//					logger.info("###try result[{}]:{}",i,function);
					
					if (function != null) {
						functionResult.add(function);		
						
						if (function1 == null) {
							function1 = function;
							continue;
						} else {
							if (function.getDeterCoeff() > function1.getDeterCoeff()) {
								function3 = function2;
								function2 = function1;
								
								function1 = function;
								continue;
							}
						}
						
						if (function2 == null) {
							function2 = function;
							continue;
						} else {
							if (function.getDeterCoeff() > function2.getDeterCoeff()) {
								function3 = function2;
								
								function2 = function;
								continue;
							}
						}
						
						if (function3 == null) {
							function3 = function;
							continue;
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
		
		if (function1 == null) {
			logger.error("the function1 is null.");
			return null;
		}
		
		if (function1.getDeterCoeff() >= precision) {
			return function1;
		}
		
		List<ExFunction> sortFunctions = new ArrayList<>();
		sortFunctions.add(function1);
		sortFunctions.add(function2);
		sortFunctions.add(function3);			
		
		Collections.sort(sortFunctions, new SortMethod.FunctionSortByAB());
		
		ExecutorService tryExec = ExThreadPool.getInstance().getNearTyExec();
		
		List<Future<ExFunction>> tryFutures = new ArrayList<>();
		Future<ExFunction> result0 = tryExec.submit(new TryCallbleThread(monitor, pointData, precision, null,sortFunctions.get(0),false));
		tryFutures.add(result0);
		
		for(int i = 0;i < sortFunctions.size() - 1;i++) {
			Future<ExFunction> result = tryExec.submit(new TryCallbleThread(monitor, pointData, precision, sortFunctions.get(i),sortFunctions.get(i + 1),true));
			tryFutures.add(result);
		}
		
		Future<ExFunction> result5 = tryExec.submit(new TryCallbleThread(monitor, pointData, precision, sortFunctions.get(sortFunctions.size() - 1),null,true));
		tryFutures.add(result5);
		
		tryExec.shutdown();
		
		while(!tryExec.awaitTermination(1, TimeUnit.SECONDS)) {
			
			for(Future<ExFunction> future : tryFutures) {
				
				if (future.isDone()) {
					ExFunction tryFunction = future.get();
					
					if (tryFunction != null) {
						tryExec.shutdownNow();
						return tryFunction;
					}
				}
				
			}
			
		}
		
		logger.info("the function1 is most result.");
		return function1;
				
	}

}
