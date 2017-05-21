package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
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

public class ComputeController {
	
	private static final Logger logger = RootLogger.getLog(ComputeController.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private List<PointValue> pointValues;
	
	private double precision;
	
	public ComputeController(CurvesProgressMonitor monitor, List<PointValue> pointValues, double precision) {
		this.monitor = monitor;
		this.pointValues = pointValues;
		this.precision = precision;
	}

	public void start() throws Exception {
		
		logger.info("###start compute function");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		logger.info("FitCallbleThread submit before");
		Future<ExcitationFunction> fitResult = exec.submit(new FitCallbleThread(monitor,pointValues,precision));
		logger.info("FitCallbleThread submit after");
		
		List<Future<ExcitationFunction>> tryResults = new ArrayList<>();
		double min = 0;
		for (double max = 10;max <= ExcitationConfig.MAX_A;max = max + 10) {
			
			logger.info("TryCallbleThread submit before.[{},{}]",min,max);
			Future<ExcitationFunction> tryResult = exec.submit(new TryCallbleThread(monitor,pointValues,precision,min,max));
			logger.info("TryCallbleThread submit after.[{},{}]",min,max);
			
			tryResults.add(tryResult);
			
			min = max;
		}
		
		
		exec.shutdown();
		
		if (exec.awaitTermination(30, TimeUnit.MINUTES)) {
			
			ExcitationFunction fitFunction = fitResult.get();
			logger.info("###fit result:" + fitFunction);
			
			if (tryResults.isEmpty()) {
				logger.info("tryResults is empty.");
			} else {
				for(int i = 0;i < tryResults.size();i++) {
					Future<ExcitationFunction> tryResult = tryResults.get(i); 
					ExcitationFunction function = tryResult.get();
					
					logger.info("###try result{}:{}",i,function);
				}
			}
			
		} else {
			logger.error("compute time out.");
		}
		
	}

}
