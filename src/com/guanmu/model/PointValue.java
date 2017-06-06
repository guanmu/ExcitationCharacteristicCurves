/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.model;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.model
 * @author wangquan 2017-5-16
 * 
 */
public class PointValue {
	
	private double x;
	
	private double y;

	/**
	 * 
	 */
	public PointValue() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x
	 * @param y
	 */
	public PointValue(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @param columnIndex
	 * @return
	 */
	public Object getColumnValue(int columnIndex) {
		if (columnIndex == 0) {
			
			if (x < 0) {
				return "";
			}
			
			return x;
		}
		
		if (y < 0) {
			return "";
		}
		return y;
	}
	
	
}
