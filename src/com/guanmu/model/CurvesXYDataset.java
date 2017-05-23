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
	
	private static final Logger logger = RootLogger.getLog(CurvesXYDataset.class.getName());
	
	private ExFunction exFunction;
	
	private List<PointValue> rowValues = new ArrayList<PointValue>();
	
	private double start;
	private double end;
	private int samples;

	public CurvesXYDataset() {
		super();
	}

	public CurvesXYDataset(ExFunction exFunction,
			List<PointValue> rowValues,double start,double end,int samples) {
		this.exFunction = exFunction;
		this.rowValues = rowValues;
		this.start = start;
		this.end = end;
		this.samples = samples;
		
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
			double step = (end - start) / (samples - 1);
			for (int i = 0; i < samples; i++) {
				double x = start + step * i;
				try {
					functionSeries.add(x, exFunction.getYValue(x));
				} catch (PowerEByondException pe) {
					logger.error("getYValue exception",pe);
				}
			}

			this.addSeries(functionSeries);
		}

	}
	
}