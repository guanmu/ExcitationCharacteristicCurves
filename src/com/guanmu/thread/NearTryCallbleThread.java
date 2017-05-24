package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

public class NearTryCallbleThread implements Callable<ExFunction> {

	private static final Logger logger = RootLogger.getLog(NearTryCallbleThread.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double min;
	
	private double max;
	
	public NearTryCallbleThread(CurvesProgressMonitor monitor,
			PointData pointData, double precision, double min,
			double max) {
		super();
		this.monitor = monitor;
		this.pointData = pointData;
		this.min = min;
		this.max = max;
	}


	@Override
	public ExFunction call() throws Exception {
		
		Thread.currentThread().setName("NearTryCallbleThread[" + min +"-" + max + "]");

		ExFunction tryFunction = computeFunctionByNearTry();
		
		monitor.addProgress(1);
		
		return tryFunction;
	}


	private ExFunction computeFunctionByNearTry() throws InterruptedException {
		
		ExFunction nearFunction = null;
		
		for(double a = min;a < max;a = a + ExConfig.NEAR_STEP_A) {
			
			for(double b = ExConfig.MIN_B;b < ExConfig.MAX_B;b = b + ExConfig.NEAR_STEP_B) {
				
				ExFunction function = new ExFunction(a, b, 0, 0, pointData);
				
				if (nearFunction == null) {
					nearFunction = function;
				} else {
					double nearDeterCoeff = nearFunction.getDeterCoeff();
					double nowDeterCoeff = function.getDeterCoeff();
					
					if (nowDeterCoeff > nearDeterCoeff) {
						nearFunction = function;
						logger.debug("-----" + function);
					}
				}
						
			}
			
			Thread.sleep(1);
		}
		

		
		logger.debug("a in [" + min + "," + max + ") near function.[{}]",nearFunction);
		return nearFunction;
	}

}
