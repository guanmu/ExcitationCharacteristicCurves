package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.guanmu.exception.PowerEByondException;
import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExConfig;

public class FitCallbleThread implements Callable<List<PointValue>> {
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double precision;

	public FitCallbleThread(CurvesProgressMonitor monitor, PointData pointData,
			double precision) {
		super();
		this.monitor = monitor;
		this.pointData = pointData;
		this.precision = precision;
	}

	@Override
	public List<PointValue> call() throws Exception {
		Thread.currentThread().setName("FitCallbleThread");
		
		Thread.sleep(500);
		
		monitor.addProgress(10);
		
		List<PointValue> fitResult = computeFunctionByFit();
		
		
		monitor.addProgress(20);
		
		return fitResult;
	}

	private List<PointValue> computeFunctionByFit() {
		
		List<PointValue> points = pointData.getPointValues();
		List<PointValue> results = new ArrayList<>();
		
		double end = ExConfig.computePointsXAxis(points);
		
		double step = (end - ExConfig.X_START) / (ExConfig.POINT_NUMBER - 1);
		for (int i = 0; i < ExConfig.POINT_NUMBER ; i++) {
			double x = ExConfig.X_START + step * i;
			
			double y = ExConfig.lagrange(points, x);
			
			results.add(new PointValue(x,y));
		}
		
		
		return results;
	}

}
