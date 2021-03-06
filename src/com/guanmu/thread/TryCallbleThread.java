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

public class TryCallbleThread implements Callable<ExFunction> {

	private static final Logger logger = RootLogger.getLog(TryCallbleThread.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double precision;
	
	private ExFunction minF;
	
	private ExFunction maxF;
	
	private boolean isAdd = true;
	
	public TryCallbleThread(CurvesProgressMonitor monitor,
			PointData pointData, double precision, ExFunction min,
			ExFunction max,boolean isAdd) {
		super();
		this.monitor = monitor;
		this.pointData = pointData;
		this.precision = precision;
		this.minF = min;
		this.maxF = max;
		this.isAdd = isAdd;
	}


	@Override
	public ExFunction call() throws Exception {
		
		Thread.currentThread().setName("TryCallbleThread[" + minF +"-" + maxF + "]");
		
		Thread.sleep(500);
		
		
		ExFunction tryFunction = computeFunctionByTry();
		
		
		return tryFunction;
	}


	private ExFunction computeFunctionByTry() throws InterruptedException {
		
		double minA = (minF == null) ? ExConfig.MIN_A : minF.getA();
		double minB = (minF == null) ? ExConfig.MIN_B : minF.getB();
		
		double maxA = (maxF == null) ? ExConfig.MAX_A : maxF.getA();
		double maxB = (maxF == null) ? ExConfig.MAX_B : maxF.getB();
		
		if (isAdd) {
			boolean isFinish = false;
			for(double a = minA;a <= maxA && !isFinish;a = a + ExConfig.STEP_A) {
				
				for(double b = ExConfig.MIN_B;b <= ExConfig.MAX_B;b = b + ExConfig.STEP_B) {
					
					if (minF != null && a <= minA && b < minB) {
						continue;
					}
					
					if (maxF != null && a >= maxA && b > maxB) {
						isFinish = true;
						break;
					}
					
					for(double c = ExConfig.MIN_C;c < ExConfig.MAX_C;c = c + ExConfig.STEP_C) {
						
						for(double d = ExConfig.MIN_D;d < ExConfig.MAX_D;d = d + ExConfig.STEP_D) {
							ExFunction function = new ExFunction(a, b, c, d, pointData);
							
							boolean isFit = function.getDeterCoeff() >= precision;
							if (isFit) {
								return function;
							}
							
						}
						
						Thread.sleep(1);
					}
					
				}
				
			}			
		} else {
			boolean isFinish = false;
			for(double a = maxA;a >= minA && !isFinish;a = a - ExConfig.STEP_A) {
				
				for(double b = ExConfig.MAX_B;b >= ExConfig.MIN_B;b = b - ExConfig.STEP_B) {
					
					if (maxF != null && a >= maxA && b > maxB) {
						continue;
					}
					
					if (minF != null && a <= minA && b < minB) {
						isFinish = true;
						break;
					}
					
					for(double c = ExConfig.MAX_C;c >= ExConfig.MIN_C;c = c - ExConfig.STEP_C) {
						
						for(double d = ExConfig.MAX_D;d >= ExConfig.MIN_D;d = d - ExConfig.STEP_D) {
							ExFunction function = new ExFunction(a, b, c, d, pointData);
							
							boolean isFit = function.getDeterCoeff() >= precision;
							if (isFit) {
								return function;
							}
							
						}
						
						Thread.sleep(1);
					}
					
				}
				
				
			}			
		}

		
		logger.debug("a in [" + minF + "," + maxF + ") not result." );
		return null;
	}

}
