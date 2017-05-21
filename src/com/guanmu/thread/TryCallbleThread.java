package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;

import com.guanmu.model.ExcitationFunction;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExcitationConfig;
import com.guanmu.utils.RootLogger;

public class TryCallbleThread implements Callable<ExcitationFunction> {

	private static final Logger logger = RootLogger.getLog(TryCallbleThread.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private List<PointValue> pointValues;
	
	private double precision;
	
	private double min;
	
	private double max;
	
	public TryCallbleThread(CurvesProgressMonitor monitor,
			List<PointValue> pointValues, double precision, double min,
			double max) {
		super();
		this.monitor = monitor;
		this.pointValues = pointValues;
		this.precision = precision;
		this.min = min;
		this.max = max;
	}


	@Override
	public ExcitationFunction call() throws Exception {
		
		Thread.currentThread().setName("TryCallbleThread[" + min +"-" + max + "]");
		
		monitor.addProgress(1);
		
		ExcitationFunction tryFunction = computeFunctionByTry();
		
		monitor.addProgress(10);
		
		return tryFunction;
	}


	private ExcitationFunction computeFunctionByTry() {
		
		for(double a = min;a < max;a = a + ExcitationConfig.STEP_A) {
			
			for(double b = ExcitationConfig.MIN_B;b < ExcitationConfig.MAX_B;b = b + ExcitationConfig.STEP_B) {
				
				for(double c = ExcitationConfig.MIN_C;c < ExcitationConfig.MAX_C;c = c + ExcitationConfig.STEP_C) {
					
					for(double d = ExcitationConfig.MIN_D;d < ExcitationConfig.MAX_D;d = d + ExcitationConfig.STEP_D) {
						ExcitationFunction function = new ExcitationFunction(a, b, c, d);
						
						boolean isFit = function.checkFitPointValues(pointValues,precision);
						if (isFit) {
							return function;
						}
					}
				}
				
			}
			
		}
		
		logger.debug("a in [" + min + "," + max + ") not result." );
		return null;
	}

}
