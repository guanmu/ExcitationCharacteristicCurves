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

public class FirstNearTryCallbleThread implements Callable<ExFunction> {

	private static final Logger logger = RootLogger.getLog(FirstNearTryCallbleThread.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double min;
	
	private double max;
	
	private int digit;	
	
	public FirstNearTryCallbleThread(CurvesProgressMonitor monitor,
			PointData pointData, double precision, double min,
			double max,int digit) {
		super();
		this.monitor = monitor;
		this.pointData = pointData;
		this.min = min;
		this.max = max;
		this.digit = digit;
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

		double nearAStep = ExConfig.NEAR_STEP_A/digit;
		double nearBStep = ExConfig.NEAR_STEP_B/digit;
		double nearCStep = ExConfig.NEAR_STEP_C/digit;
		double nearDStep = ExConfig.NEAR_STEP_D/digit;
		
		for (double a = min; a < max; a = a + nearAStep) {

			for (double b = ExConfig.MIN_B; b < ExConfig.MAX_B; b = b + nearBStep) {
				
				for(double c = ExConfig.MIN_C;c < ExConfig.MAX_C;c = c + nearCStep) {
					
					for(double d = ExConfig.MIN_D;d < ExConfig.MAX_D;d = d + nearDStep) {
						ExFunction function = new ExFunction(a, b, c, d, pointData);

						if (nearFunction == null) {
							nearFunction = function;
						} else {
							double nearDeterCoeff = nearFunction.getDeterCoeff();
							double nowDeterCoeff = function.getDeterCoeff();

							if (nowDeterCoeff > nearDeterCoeff) {
								nearFunction = function;
//								logger.debug("-----" + function);
							}
						}						
					}
				}	
			}

			Thread.sleep(1);
		}
		

		
		logger.debug("a in [" + min + "," + max + ") near function.[{}]",nearFunction);
		return nearFunction;
	}

}
