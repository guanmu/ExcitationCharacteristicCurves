package com.guanmu.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
			logger.error("computeAverageError exception.",pe);
			avgError = ExConfig.MAX_PECISION;
		}
		
		try {
			deterCoeff = computeDeterminationCoefficient(pointData);
		} catch (Exception e) {
			logger.error("computeDeterminationCoefficient exception.",e);
		}
		
	}	

	public double getYValue(double x) throws PowerEByondException {
		
		double x1 = b * x;
		double x2 = d * x;
		
		if (x1 > ExConfig.E_POW_MAX || x2 > ExConfig.E_POW_MAX) {
			throw new PowerEByondException(x1,x2);
		}
		
		double value = a * Math.exp(x1) + c * Math.exp(x2);
		
//		double treePointValue = ExConfig.treePointDouble(value);
		return value;
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
		BigDecimal bigAvg = new BigDecimal(yAvg);
		
		BigDecimal sySquareSum = new BigDecimal(0);
		BigDecimal oySquareSum = new BigDecimal(0);
		for(PointValue point : pointValues) {
			double x = point.getX();
			double y = point.getY();
			BigDecimal bigY = new BigDecimal(y);
			
			double functionY = getYValue(x);
			BigDecimal bigFunctionY = new BigDecimal(functionY);
			
			BigDecimal pointSySquare = bigFunctionY.subtract(bigY).pow(2);
			sySquareSum = sySquareSum.add(pointSySquare);
			
			
			BigDecimal pointOySquare = bigFunctionY.subtract(bigAvg).pow(2);
			oySquareSum = oySquareSum.add(pointOySquare);
		}
		
		double sySum = sySquareSum.doubleValue();
		double oySum = oySquareSum.doubleValue();
		if (oySum == Double.MIN_VALUE) {
			logger.error("the oySquareSum is min value.");
		}

		if (Double.isInfinite(sySum) || Double.isNaN(sySum)) {
	        throw new NotDoubleValueException(sySum);
		}

		if (Double.isInfinite(oySum) || Double.isNaN(oySum)) {
			throw new NotDoubleValueException(oySum);
		}	
		
		if (sySum >= oySum) {
			return ExConfig.MIN_DETERMINATION_COEFFICIENT;
		}
		
		MathContext mc = new MathContext(10, RoundingMode.HALF_DOWN);
		BigDecimal divResult = sySquareSum.divide(oySquareSum,mc);
		double determinationCoefficient = new BigDecimal(1).subtract(divResult).doubleValue();
		
		return determinationCoefficient;
	}

	@Override
	public String toString() {
		return "ExFunction [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ ", avgError=" + avgError + ", deterCoeff=" + deterCoeff + "]";
	}
	

	public static void main(String[] args) {
		List<PointValue> pointValues = ExConfig.getInitTableValues();
		PointData pd = new PointData(pointValues);
		ExFunction test = new ExFunction(22.0, 0.032319999999999224, 0, 0,pd);
		
		System.out.println(test);
	}

	
}
