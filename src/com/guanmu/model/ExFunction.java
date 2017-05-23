package com.guanmu.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.guanmu.exception.NotDoubleValueException;
import com.guanmu.exception.PowerEByondException;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

public class ExFunction {
	
	private static final Logger logger = RootLogger.getLog(ExFunction.class.getName());	
	
	private double a;
	private double b;
	private double c;
	private double d;
	
	private PointData pointData;
	private List<PointValue> points = new ArrayList<PointValue>();
	
	private double avgError = 0;
	
	private double deterCoeff;
	
	public ExFunction(double a, double b, double c, double d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

	}

	@Deprecated
	public ExFunction(double a, double b, double c, double d,
			List<PointValue> points) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.points = points;
		
		try {
			avgError = computeAverageError(points);			
		} catch (PowerEByondException pe) {
			avgError = ExConfig.MAX_PECISION;
		}
		
	}


	public ExFunction(double a, double b, double c, double d,
			PointData pointData) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.pointData = pointData;
		this.points = pointData.getPointValues();

		try {
			avgError = computeAverageError(points);			
		} catch (PowerEByondException pe) {
			avgError = ExConfig.MAX_PECISION;
		}
		
		try {
			deterCoeff = computeDeterminationCoefficient(pointData);
		} catch (Exception e) {

		}
		
	}	

	public double getYValue(double x) throws PowerEByondException {
		
		double x1 = b * x;
		double x2 = d * x;
		
		if (x1 > ExConfig.E_POW_MAX || x2 > ExConfig.E_POW_MAX) {
			throw new PowerEByondException(x1,x2);
		}
		
		double value = a * Math.exp(x1) + c * Math.exp(x2);
		
		double treePointValue = ExConfig.treePointDouble(value);
		return treePointValue;
	}

	public String getFunctionStr() {
		return "y=" + a + "*e^(" + b + "*x) + " + c + "*e^(" + d + "*x)" ;
	}

	public double getAvgError() {
		return avgError;
	}

	public double getDeterCoeff() {
		return deterCoeff;
	}

	public boolean checkFitPointValues(List<PointValue> pointValues,
			double precision) throws PowerEByondException {
		if (pointValues == null) {
			logger.error("pointValues is null.");
			return false;
		}
		
		if (pointValues.isEmpty()) {
			logger.error("pointValues is empty.");
			return false;
		}
		
		double absPrecision = Math.abs(precision);
		
		double avgError = computeAverageError(pointValues);
		
		if (1 - avgError >= absPrecision) {
			return true;
		}
		
		return false;
	}

	public double computeAverageError(List<PointValue> pointValues) throws PowerEByondException {
		
		if (pointValues == null || pointValues.isEmpty()) {
			return ExConfig.MAX_PECISION;
		}
		
		double errorSum = 0;
		int pointNum = pointValues.size();
		for(PointValue point : pointValues) {
			double x = point.getX();
			double y = point.getY();
			
			double functionY = getYValue(x);
			
			double error = Math.abs((y - functionY)) / y;
			
			error = ExConfig.formatDouble(error);
			
			errorSum += error;
		}
		
		double averageError = errorSum / pointNum;
		averageError = ExConfig.formatDouble(averageError);
		
		return averageError;
	}
	
	public double computeDeterminationCoefficient(PointData pointData) throws Exception {
		
		if (pointData == null) {
			logger.error("the pointData is null.");
			return ExConfig.MIN_DETERMINATION_COEFFICIENT;
		}
		
		List<PointValue> pointValues = pointData.getPointValues();
		if (pointValues == null || pointValues.isEmpty()) {
			logger.error("the pointValues is null or empty.[{}]",pointValues);
			return ExConfig.MIN_DETERMINATION_COEFFICIENT;
		}
				
		double yAvg = pointData.getyAvg();
		
		double sySquareSum = 0;
		double oySquareSum = 0;
		for(PointValue point : pointValues) {
			double x = point.getX();
			double y = point.getY();
			
			double functionY = getYValue(x);
			
			sySquareSum += Math.pow(functionY - y,2);
			
			oySquareSum += Math.pow(functionY - yAvg,2);
		}
		
		if (oySquareSum == 0) {
			logger.error("the oySquareSum is 0.");
		}
		
		if (Double.isInfinite(sySquareSum) || Double.isNaN(sySquareSum)) {
			throw new NotDoubleValueException(sySquareSum);
		}
		
		if (Double.isInfinite(oySquareSum) || Double.isNaN(oySquareSum)) {
			throw new NotDoubleValueException(oySquareSum);
		}		
		
		double determinationCoefficient = 1 - (sySquareSum / oySquareSum);
		
		return determinationCoefficient;
	}

	@Override
	public String toString() {
		return "ExFunction [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ ", avgError=" + avgError + ", deterCoeff=" + deterCoeff + "]";
	}
	

	

	
}
