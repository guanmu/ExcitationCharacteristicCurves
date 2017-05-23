package com.guanmu.exception;

public class NotDoubleValueException extends Exception {
	
	private static final long serialVersionUID = -4579699333155371090L;
	
	private double vlaue;
	
	public NotDoubleValueException(double vlaue) {
		this.vlaue = vlaue;
	}



}
