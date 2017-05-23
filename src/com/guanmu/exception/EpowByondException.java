/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.exception;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.exception
 * @author wangquan 2017-5-23
 * 
 */
public class EpowByondException extends Exception{
	
	private static final long serialVersionUID = -3275996084948152130L;
	
	private double x1;
	private double x2;
	
	/**
	 * @param x1
	 * @param x2
	 */
	public EpowByondException(double x1, double x2) {
		this.x1 = x1;
		this.x2 = x2;
	}	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + "\n" + "[x1=" + x1 + ",x2=" + x2 + "]";
	}
}
