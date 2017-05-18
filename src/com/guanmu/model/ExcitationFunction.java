package com.guanmu.model;

public class ExcitationFunction {
	
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
	
}
