package com.guanmu.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.guanmu.utils.ExcitationConfig;
import com.guanmu.utils.RootLogger;

public class ExcitationFunction {
	
	private static final Logger logger = RootLogger.getLog(ExcitationFunction.class.getName());	
	
	private static final BigDecimal BIG_E = new BigDecimal(Math.E);
	
	private double a;
	private double b;
	private double c;
	private double d;
	
	private BigDecimal bigA = null;
	private BigDecimal bigB = null;
	private BigDecimal bigC = null;
	private BigDecimal bigD = null;
	
	private List<PointValue> points = new ArrayList<PointValue>();
	
	private double avgError = 0;
	
	public ExcitationFunction(double a, double b, double c, double d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		
		bigA = new BigDecimal(a);
		bigB = new BigDecimal(b);
		bigC = new BigDecimal(c);
		bigD = new BigDecimal(d);
	}

	public ExcitationFunction(double a, double b, double c, double d,
			List<PointValue> points) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.points = points;
		
		bigA = new BigDecimal(a);
		bigB = new BigDecimal(b);
		bigC = new BigDecimal(c);
		bigD = new BigDecimal(d);
		
		avgError = computeAverageError(points);
	}


	public double getYValue(double x) {
		
		// TODO 无法精确计算
		double value = a * Math.pow(Math.E, b * x) + c * Math.pow(Math.E, d * x);
		
		return value;
	}

	public String getFunctionStr() {
		return "y=" + a + "*e^(" + b + "*x) + " + c + "*e^(" + d + "*x)" ;
	}

	public double getAvgError() {
		return avgError;
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

	public double computeAverageError(List<PointValue> pointValues) {
		
		if (pointValues == null || pointValues.isEmpty()) {
			return 1;
		}
		
		double errorSum = 0;
		int pointNum = pointValues.size();
		for(PointValue point : pointValues) {
			double x = point.getX();
			double y = point.getY();
			
			double functionY = getYValue(x);
			
			double error = Math.abs((y - functionY)) / y;
			
			if (Double.isInfinite(error)) {
				System.out.println(error);
			}
			error = ExcitationConfig.formatDouble(error);
			
			errorSum += error;
		}
		
		double averageError = errorSum / pointNum;
		averageError = ExcitationConfig.formatDouble(averageError);
		
		return averageError;
	}

	@Override
	public String toString() {
		return "ExcitationFunction [a=" + a + ", b=" + b + ", c=" + c + ", d="
				+ d + ", averageError=" + avgError + "]";
	}
	

	
}
