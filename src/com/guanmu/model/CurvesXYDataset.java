package com.guanmu.model;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;

import com.guanmu.exception.PowerEByondException;
import com.guanmu.utils.RootLogger;

public class CurvesXYDataset extends XYSeriesCollection {

	private static final long serialVersionUID = -2154940116654217208L;
	
	public static final int X_START = 0;
	
	public static final int DEFAULT_X_END = 100;
	
	public static final int DEFAULT_SAMPLES = 200;
	
	private static final Logger logger = RootLogger.getLog(CurvesXYDataset.class.getName());
	
	private ExFunction exFunction;
	
	private List<PointValue> rowValues = new ArrayList<PointValue>();
	
	private double end = 100;
	private int samples  = DEFAULT_SAMPLES;

	public CurvesXYDataset() {
		super();
	}

	public CurvesXYDataset(ExFunction exFunction,
			List<PointValue> rowValues) {
		this.exFunction = exFunction;
		this.rowValues = rowValues;
		
		autoAddSeries();
	}
	
	private void autoAddSeries() {
		if (rowValues != null) {
			XYSeries pointSeries = new XYSeries("points");
			for(int i = 0;i < rowValues.size();i++) {
				PointValue point = rowValues.get(i);
				
				pointSeries.add(point.getX(),point.getY());
			}		
			
			 this.addSeries(pointSeries);
		}
		

		if (exFunction != null) {
			XYSeries functionSeries = new XYSeries(exFunction.getFunctionStr());
			double step = (end - X_START) / (samples - 1);
			for (int i = 0; i < samples; i++) {
				double x = X_START + step * i;
				try {
					functionSeries.add(x, exFunction.getYValue(x));
				} catch (PowerEByondException pe) {
					logger.error("getYValue exception",pe);
				}
			}

			this.addSeries(functionSeries);
		}

	}

	public void autoComputeParams() {
		
		double tmpMaxX = 0;
		for(PointValue pv : rowValues) {
			
			if (pv.getX() > tmpMaxX) {
				tmpMaxX = pv.getX();
			}
				
		}
		
		tmpMaxX = tmpMaxX + 0.1*tmpMaxX;
		
		end = (int) tmpMaxX;
		
	}
	
	/**
	 * @param pointData
	 * @param function
	 * @param i
	 * @param j
	 */
	public void changeValue(PointData pointData, ExFunction function) {
		this.removeAllSeries();
		
		rowValues = pointData.getPointValues();
		exFunction = function;
		
		autoAddSeries();
		
		fireDatasetChanged();
	}
	
}