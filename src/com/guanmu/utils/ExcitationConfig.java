package com.guanmu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ExcitationConfig {
	
	public static final boolean DEBUG = true;
	
	public static final double E_POW_MAX = Math.log(Double.MAX_VALUE);
	
	public static final double MAX_PECISION = 1;
	
	/** 包括*/
	public static final double MIN_A = 0;
	/** 不包括*/
	public static final double MAX_A = 100;
	public static final double NEAR_STEP_A = 1;
	public static final double STEP_A = 0.01;	
	
	public static final double MIN_B = 0;
	public static final double MAX_B = 0.1;
	public static final double NEAR_STEP_B = 0.01;
	public static final double STEP_B = 0.00001;	
	
	public static final double MIN_C = 0;
	public static final double MAX_C = 0.00001;
	public static final double NEAR_STEP_C = 0.000001;
	public static final double STEP_C = 0.0000000001;	
	
	public static final double MIN_D = 0;
	public static final double MAX_D = 1;	
	public static final double NEAR_STEP_D = 0.1;
	public static final double STEP_D = 0.0001;	

	public static final DecimalFormat TWO_POINT_DF = new DecimalFormat("#.00");
	public static final DecimalFormat THREE_POINT_DF = new DecimalFormat("#.000");
	
	/**
	 * 保留小数点后三位小数
	 * @param d
	 * @return
	 */
	public static double formatDouble(double d) {
		BigDecimal tmp = null;
		try {
			tmp = new BigDecimal(d);
		} catch (Exception e) {
			e.printStackTrace();
			return d;
		}
		
		return tmp.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();  
		
	}
	
}
