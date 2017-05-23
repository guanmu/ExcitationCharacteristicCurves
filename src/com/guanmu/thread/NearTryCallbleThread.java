package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

public class NearTryCallbleThread implements Callable<ExFunction> {

	private static final Logger logger = RootLogger.getLog(NearTryCallbleThread.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private List<PointValue> pointValues;
	
	private double min;
	
	private double max;
	
	public NearTryCallbleThread(CurvesProgressMonitor monitor,
			List<PointValue> pointValues, double precision, double min,
			double max) {
		super();
		this.monitor = monitor;
		this.pointValues = pointValues;
		this.min = min;
		this.max = max;
	}


	@Override
	public ExFunction call() throws Exception {
		
		Thread.currentThread().setName("NearTryCallbleThread[" + min +"-" + max + "]");

		ExFunction tryFunction = computeFunctionByNearTry();
		
		return tryFunction;
	}


	private ExFunction computeFunctionByNearTry() {
		
		ExFunction nearFunction = null;
		
		for(double a = min;a < max;a = a + ExConfig.NEAR_STEP_A) {
			
			for(double b = ExConfig.MIN_B;b < ExConfig.MAX_B;b = b + ExConfig.NEAR_STEP_B) {
				
				for(double c = ExConfig.MIN_C;c < ExConfig.MAX_C;c = c + ExConfig.NEAR_STEP_C) {
					
					for(double d = ExConfig.MIN_D;d < ExConfig.MAX_D;d = d + ExConfig.NEAR_STEP_D) {
						ExFunction function = new ExFunction(a, b, c, d, pointValues);
						
						
						if (nearFunction == null) {
							nearFunction = function;
						} else {
							double nearError = nearFunction.getAvgError();
							double nowError = function.getAvgError();
							
							if (nearError > nowError) {
								nearFunction = function;
								logger.debug("-----" + function);
							}
						}
						
					}
				}
				
			}
			
		}
		

		
		logger.debug("a in [" + min + "," + max + ") near function.[{}]",nearFunction);
		return nearFunction;
	}

}
