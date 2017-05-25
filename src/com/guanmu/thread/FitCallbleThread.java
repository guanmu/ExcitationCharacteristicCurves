package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.Callable;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;

public class FitCallbleThread implements Callable<ExFunction> {
	
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
	public ExFunction call() throws Exception {
		Thread.currentThread().setName("FitCallbleThread");
		
		Thread.sleep(500);
		
		monitor.addProgress(10);
		
		Thread.sleep(5*1000);
		
		
		ExFunction fitResult = computeFunctionByFit();
		
		
		monitor.addProgress(20);
		
		return fitResult;
	}

	private ExFunction computeFunctionByFit() {
		
		// TODO
		
		return new ExFunction(1, 0.1, 0.01, 0.02);
	}

}
