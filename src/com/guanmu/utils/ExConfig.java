package com.guanmu.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.guanmu.model.PointValue;

public class ExConfig {

	public static final boolean DEBUG = true;

	public static final double E_POW_MAX = Math.log(Double.MAX_VALUE);

	public static final double MAX_PECISION = 1;

	public static final double MIN_DETERMINATION_COEFFICIENT = 0;

	/** 包括 */
	public static final double MIN_A = 0;
	/** 不包括 */
	public static final double MAX_A = 100;
	public static final double NEAR_STEP_A = 0.1;
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
	public static final DecimalFormat THREE_POINT_DF = new DecimalFormat(
			"#.000");

	/**
	 * 保留小数点后五位小数
	 * 
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

		return tmp.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 保留小数点后三位小数
	 * 
	 * @param d
	 * @return
	 */
	public static double treePointDouble(double d) {
		BigDecimal tmp = null;
		try {
			tmp = new BigDecimal(d);
		} catch (Exception e) {
			e.printStackTrace();
			return d;
		}

		return tmp.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	public static List<PointValue> getInitTableValues() {

		List<PointValue> initValues = new ArrayList<PointValue>();

		if (!DEBUG) {
			return initValues;
		}

		initValues = debugData1();

		return initValues;
	}

	private static List<PointValue> debugData1() {
		List<PointValue> initValues = new ArrayList<PointValue>();
		initValues.add(new PointValue(11.54, 31.5));
		initValues.add(new PointValue(28.86, 68.5));
		initValues.add(new PointValue(46.2, 139.2));
		initValues.add(new PointValue(57.7, 206));
		initValues.add(new PointValue(69.3, 280));
		initValues.add(new PointValue(86.6, 450));
		initValues.add(new PointValue(103.9, 700));
		initValues.add(new PointValue(109.7, 813));
		initValues.add(new PointValue(115.5, 935));
		initValues.add(new PointValue(121.2, 1069));
		initValues.add(new PointValue(131.6, 1590));
		initValues.add(new PointValue(138.5, 2220));
		initValues.add(new PointValue(144.3, 4150));
		initValues.add(new PointValue(150, 7020));

		return initValues;
	}

	private static List<PointValue> debugData2() {
		List<PointValue> initValues = new ArrayList<PointValue>();
		initValues.add(new PointValue(11.54, 32.5));
		initValues.add(new PointValue(28.86, 65.5));
		initValues.add(new PointValue(46.2, 121));
		initValues.add(new PointValue(57.7, 162));
		initValues.add(new PointValue(69.3, 203));
		initValues.add(new PointValue(86.6, 274));
		initValues.add(new PointValue(103.9, 350));
		initValues.add(new PointValue(109.7, 420));
		initValues.add(new PointValue(115.5, 495));
		initValues.add(new PointValue(121.2, 602));
		initValues.add(new PointValue(131.6, 830));
		initValues.add(new PointValue(138.5, 1370));
		initValues.add(new PointValue(144.3, 2780));
		initValues.add(new PointValue(150, 6350));

		return initValues;
	}
	

	private static List<PointValue> debugData3() {
		List<PointValue> initValues = new ArrayList<PointValue>();
		initValues.add(new PointValue(11.54, 129.5));
		initValues.add(new PointValue(28.86, 258.4));
		initValues.add(new PointValue(46.2, 441));
		initValues.add(new PointValue(57.7, 656));
		initValues.add(new PointValue(69.3, 1042));
		initValues.add(new PointValue(86.6, 2400));
		initValues.add(new PointValue(103.9, 5150));
		initValues.add(new PointValue(109.7, 6500));
		initValues.add(new PointValue(115.5, 8100));

		return initValues;
	}	
}
