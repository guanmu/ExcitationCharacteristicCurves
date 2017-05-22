package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;

import com.guanmu.model.ExcitationFunction;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExcitationConfig;
import com.guanmu.utils.RootLogger;

public class NearTryCallbleThread implements Callable<ExcitationFunction> {

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
	public ExcitationFunction call() throws Exception {
		
		Thread.currentThread().setName("NearTryCallbleThread[" + min +"-" + max + "]");

		ExcitationFunction tryFunction = computeFunctionByNearTry();
		
		return tryFunction;
	}


	private ExcitationFunction computeFunctionByNearTry() {
		
		ExcitationFunction nearFunction = null;
		
		for(double a = min;a < max;a = a + ExcitationConfig.NEAR_STEP_A) {
			
			for(double b = ExcitationConfig.MIN_B;b < ExcitationConfig.MAX_B;b = b + ExcitationConfig.NEAR_STEP_B) {
				
				for(double c = ExcitationConfig.MIN_C;c < ExcitationConfig.MAX_C;c = c + ExcitationConfig.NEAR_STEP_C) {
					
					for(double d = ExcitationConfig.MIN_D;d < ExcitationConfig.MAX_D;d = d + ExcitationConfig.NEAR_STEP_D) {
						ExcitationFunction function = new ExcitationFunction(a, b, c, d, pointValues);
						
						
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
