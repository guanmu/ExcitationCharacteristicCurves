package com.guanmu.model;

import java.util.List;

import org.slf4j.Logger;

import com.guanmu.thread.TryCallbleThread;
import com.guanmu.utils.RootLogger;

public class ExcitationFunction {
	
	private static final Logger logger = RootLogger.getLog(ExcitationFunction.class.getName());	
	
	private double a;
	private double b;
	private double c;
	private double d;
	
	public ExcitationFunction(double a, double b, double c, double d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public double getYValue(double x) {
		double value = a * Math.pow(Math.E, b * x) + c * Math.pow(Math.E, d * x);
		
		return value;
	}

	public String getFunctionStr() {
		return "y=" + a + "*e^(" + b + "*x) + " + c + "*e^(" + d + "*x)" ;
	}

	public boolean checkFitPointValues(List<PointValue> pointValues,
			double precision) {
		if (pointValues == null) {
			logger.error("pointValues is null.");
			return false;
		}
		
		if (pointValues.isEmpty()) {
			logger.error("pointValues is empty.");
			return false;
		}
		
		double absPrecision = Math.abs(precision);
		
		for(int i = 0; i < pointValues.size();i++) {
			PointValue pointValue = pointValues.get(i);
			
			
			double x = pointValue.getX();
			double y = pointValue.getY();
			
			double functionY = getYValue(x);
			
			if (Math.abs(functionY - y) > absPrecision) {
				return false;
			}
			
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "ExcitationFunction [a=" + a + ", b=" + b + ", c=" + c + ", d="
				+ d + "]";
	}
	
	
}
