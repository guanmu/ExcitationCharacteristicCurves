package com.guanmu.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

public class PointData {
	
	private static final Logger logger = RootLogger.getLog(PointData.class.getName());	
	
	private List<PointValue> pointValues = new ArrayList<PointValue>();
	
	private double yAvg = 0;
	
	public PointData(List<PointValue> pointValues) {
		super();
		this.pointValues = pointValues;
		
		yAvg = computeYAvg();
	}

	private double computeYAvg() {
		
		if (pointValues == null ||pointValues.isEmpty()) {
			logger.error("the points is null or empty.[{}]",pointValues);
			return 0;
		}
		
		double ySum = 0;
		int pointNum = pointValues.size();
		for(PointValue point : pointValues) {
			ySum += point.getY();
		}
		
		
		double yAvgTmp = ySum / pointNum;
		
		double threePointValue = ExConfig.treePointDouble(yAvgTmp);
		return threePointValue;
	}

	public List<PointValue> getPointValues() {
		return pointValues;
	}

	public double getyAvg() {
		return yAvg;
	}

	
	
}
