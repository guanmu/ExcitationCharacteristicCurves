package com.guanmu.model;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;

import com.guanmu.exception.PowerEByondException;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.RootLogger;

public class CurvesXYDataset extends XYSeriesCollection {

	private static final long serialVersionUID = -2154940116654217208L;
	

	
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
		
		addSeriesByFunction(exFunction);
	}
	
	private void addSeriesByFunction(ExFunction function) {

		if (function != null) {
			XYSeries functionSeries = new XYSeries(function.getFunctionStr());
			double step = (end - ExConfig.X_START) / (samples - 1);
			for (int i = 0; i < samples; i++) {
				double x = ExConfig.X_START + step * i;
				try {
					functionSeries.add(x, function.getYValue(x));
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
		
		autoComputeParams();
		
		addPoints();
		
		addSeriesByFunction(function);
		
		fireDatasetChanged();
	}

	/**
	 * @param pointData
	 * @param fitResults
	 */
	public void changeValue(PointData pointData, List<PointValue> fitResults) {
		this.removeAllSeries();
		
		rowValues = pointData.getPointValues();
		
		addPoints();
		
		addFitSeries(fitResults);
		
		fireDatasetChanged();
	}

	/**
	 * 
	 */
	private void addPoints() {
		if (rowValues != null) {
			XYSeries pointSeries = new XYSeries("points");
			for(int i = 0;i < rowValues.size();i++) {
				PointValue point = rowValues.get(i);
				
				pointSeries.add(point.getX(),point.getY());
			}		
			
			 this.addSeries(pointSeries);
		}
	}

	/**
	 * @param fitResults
	 */
	private void addFitSeries(List<PointValue> fitResults) {
		
		if (fitResults != null) {
			XYSeries pointSeries = new XYSeries("插值法",true,false);
			for(int i = 0;i < fitResults.size();i++) {
				PointValue point = fitResults.get(i);
				
				pointSeries.add(point.getX(),point.getY());
			}		
			
			 this.addSeries(pointSeries);
		}	
		
	}

	public void changeValue(PointData pointData, List<PointValue> fitResults,
			ExFunction function) {
		rowValues = pointData.getPointValues();
		exFunction = function;
		
		autoComputeParams();
		
		addPoints();
		
		addFitSeries(fitResults);
		
		addSeriesByFunction(function);
		
		fireDatasetChanged();
	}
	
	@Override
	public List<XYSeries> getSeries() {
		List list = super.getSeries();
		List<XYSeries> result = new ArrayList<>();
		
		for (Object obj : list) {
			
			if (obj instanceof XYSeries) {
				XYSeries series = (XYSeries)obj;
				
				result.add(series);
			}
		}
		
		return result;
	}

	public void drawInfexionPoint(double infexionX, double infexionY) {
		
		String infexionSeriesName = "拐点";
		XYSeries infexionSeries = null;
		for(XYSeries series : this.getSeries()) {
			if (infexionSeriesName.equals(series.getKey())) {
				infexionSeries = series;
				break;
			}
		}
		
		if (infexionSeries == null) {
			infexionSeries = new XYSeries(infexionSeriesName);
			this.addSeries(infexionSeries);				
		} else {
			int count = infexionSeries.getItemCount();
			infexionSeries.delete(0, count - 1);
		}

		infexionSeries.add(infexionX, infexionY);
		
		fireDatasetChanged();
	}
	
	@Override
	public void removeAllSeries() {
		super.removeAllSeries();
		fireDatasetChanged();
	}
}