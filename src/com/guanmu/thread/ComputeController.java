package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
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

public class ComputeController {
	
	private static final Logger logger = RootLogger.getLog(ComputeController.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double precision;
	
	public ComputeController(CurvesProgressMonitor monitor, PointData pointData, double precision) {
		this.monitor = monitor;
		this.pointData = pointData;
		this.precision = precision;
	}

	public void start() throws Exception {
		
		Thread.currentThread().setName("ComputeController Thread");
		
		logger.info("###start compute function");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		logger.info("FitCallbleThread submit before");
		Future<ExFunction> fitResult = exec.submit(new FitCallbleThread(monitor,pointData,precision));
		logger.info("FitCallbleThread submit after");

		Future<List<ExFunction>> tryResult = exec.submit(new TryTotalCallbleThread(monitor, pointData, precision));

		
		exec.shutdown();
		
		if (exec.awaitTermination(30, TimeUnit.MINUTES)) {
			
			ExFunction fitFunction = fitResult.get();
			logger.info("###fit result:" + fitFunction);
			
			if (tryResult == null) {
				logger.info("tryResult is null.");
			} else {
				
				List<ExFunction> functions = tryResult.get();
				for(int i = 0;i < functions.size();i++) {
					ExFunction function = functions.get(i);
					
					logger.info("###try result[{}]:{}",i,function);
				}
			}
			
		} else {
			logger.error("compute time out.");
		}
		
	}

}
