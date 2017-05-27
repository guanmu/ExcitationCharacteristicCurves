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
		
		ExFunction mostNearFunction = null;
		try {

			List<ExFunction> initFunctions = new ArrayList<ExFunction>();
			for (double max = 1; max <= ExConfig.MAX_A; max = max + 1) {

				ExFunction maxF = new ExFunction(max, 0, 0, 0, pointData);

				initFunctions.add(maxF);
			}

			List<ExFunction> results = null;
			List<ExFunction> tmpFunctions = new ArrayList<>(initFunctions);
			int digit = 10;
			for (int i = 1; i <= ExConfig.MAX_DIGIT; i++) {
				digit = (int) Math.pow(10, i);

				try {
					results = tryFunctions(tmpFunctions, digit);
				} catch (Exception e) {
					logger.error("tryFunctions exception.", e);
				}

				if (results == null) {
					continue;
				}

				if (!results.isEmpty()) {
					Collections.sort(results, new SortMethod.FunctionSortByDeterCoeffMax());
					ExFunction nearFunction = results.get(0);
					mostNearFunction = nearFunction;
					
					if (nearFunction != null && nearFunction.getDeterCoeff() >= precision) {
						logger.debug("######nearFunction meet precision.[{}]", nearFunction);
						return nearFunction;
					}

					logger.debug("this time near function.[{}]", nearFunction);

					results.add(new ExFunction(ExConfig.MIN_A, ExConfig.MIN_B, ExConfig.MIN_C, ExConfig.MIN_D));
					results.add(new ExFunction(ExConfig.MAX_A, ExConfig.MAX_B, ExConfig.MAX_C, ExConfig.MAX_D));

					tmpFunctions = results;
				}

			}

			// 低精度逼近失败
			if (results == null || results.isEmpty()) {
				logger.error("the results is null or empty.[{}]", results);
				results = initFunctions;
			}

			ExecutorService tryExec = ExThreadPool.getInstance().getTryExec();
			List<Future<ExFunction>> tryFutures = new ArrayList<>();
			for (int i = 0; i < results.size() - 1; i++) {

				ExFunction minF = results.get(i);
				ExFunction maxF = results.get(i + 1);

				logger.info("NearTryCallbleThread submit before.[{},{}]", minF, maxF);
				Future<ExFunction> tryResult = tryExec.submit(new NearTryCallbleThread(monitor, pointData, precision,
						minF, maxF, true, digit));
				logger.info("NearTryCallbleThread submit after.[{},{}]", minF, maxF);

				tryFutures.add(tryResult);

			}

			tryExec.shutdown();

			while (!tryExec.awaitTermination(1, TimeUnit.SECONDS)) {

				for (Future<ExFunction> future : tryFutures) {

					if (future.isDone()) {
						ExFunction tryFunction = future.get();

						if (tryFunction != null) {
							tryExec.shutdownNow();
							return tryFunction;
						}
					}

				}
				
				Thread.sleep(1);
			}

			Collections.sort(results, new SortMethod.FunctionSortByDeterCoeffMax());

			mostNearFunction = results.get(0);
			logger.info("###the function is most result.[{}]", mostNearFunction);

		} catch (Exception ie) {
			logger.error("the call shutdowned.",ie);
		}
		
		return mostNearFunction;
				
	}



	/**
	 * @param probableFunctions
	 * @return
	 * @throws InterruptedException 
	 */
	private List<ExFunction> tryFunctions(List<ExFunction> probableFunctions, int digit) throws Exception {
		logger.info("### start tryFunctions.[{}]",digit);
		List<ExFunction> functions = new ArrayList<>();
		if (probableFunctions == null || probableFunctions.isEmpty()) {
			logger.error("the probableFunctions is null or empty.[{},{}]",digit,probableFunctions);
			return functions;
		}
		
		long startTime = new Date().getTime();
		List<ExFunction> tmpFunctions = new ArrayList<>(probableFunctions);
		
		Collections.sort(tmpFunctions, new SortMethod.FunctionSortByParam());
		
		ExecutorService nearTryExec = ExThreadPool.getInstance().getNearTryExec();
		List<Future<ExFunction>> nearTryResults = new ArrayList<>();
		for (int i = 0;i < tmpFunctions.size() - 1;i++) {
			
			ExFunction minF = tmpFunctions.get(i);
			ExFunction maxF = tmpFunctions.get(i + 1);
			
			boolean isAdd = true;
			if (minF.getDeterCoeff() > maxF.getDeterCoeff()) {
				isAdd = false;
			}
			
			logger.debug("NearTryCallbleThread submit before.[{},{}]",minF,maxF);
			Future<ExFunction> tryResult = nearTryExec.submit(new NearTryCallbleThread(monitor,pointData,precision,minF,maxF,isAdd,digit));
			logger.debug("NearTryCallbleThread submit after.[{},{}]",minF,maxF);
			
			nearTryResults.add(tryResult);
			
		}
		
		nearTryExec.shutdown();
		
		while(!nearTryExec.awaitTermination(1, TimeUnit.SECONDS)) {
			
			for(Future<ExFunction> future : nearTryResults) {
				
				if (future.isDone()) {
					ExFunction tryFunction = future.get();
					
					if (tryFunction != null) {
						
						if (tryFunction.getDeterCoeff() >= precision) {
							nearTryExec.shutdownNow();
							functions.add(tryFunction);
							return functions;							
						}

					}
				}
				
			}
		}
		
		long endTime = new Date().getTime();
		logger.info("$$$near time:" + (endTime - startTime)/1000 + "s");
		
		int errorNumber = 0;
		for(Future<ExFunction> future : nearTryResults) {
			if (!future.isDone()) {
				errorNumber++;
				continue;
			}
			
			ExFunction function = future.get();
			if (function == null) {
				errorNumber++;
				continue;
			}
			
			
			functions.add(function);			
		}
		
		if (errorNumber > 0) {
			logger.error("errorNumber number.[{}]",errorNumber);
		}
		
		functions.removeAll(Collections.singleton(null));
		logger.info("### end tryFunctions.[{}]",digit);		
		return functions;
	}

}
