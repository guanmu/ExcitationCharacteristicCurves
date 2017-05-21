package com.guanmu.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.guanmu.model.ExcitationFunction;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.utils.ExcitationConfig;

public class ComputeController {
	
	private CurvesProgressMonitor monitor;
	
	private List<PointValue> pointValues;
	
	private double precision;
	
	public ComputeController(CurvesProgressMonitor monitor, List<PointValue> pointValues, double precision) {
		this.monitor = monitor;
		this.pointValues = pointValues;
		this.precision = precision;
	}

	public void start() {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Future<ExcitationFunction> fitResult = exec.submit(new FitCallbleThread(monitor,pointValues,precision));
		
		
		List<Future<ExcitationFunction>> tryResults = new ArrayList<>();
		double min = 0;
		for (double max = 10;max <= ExcitationConfig.MAX_A;max = max + 10) {
			
			Future<ExcitationFunction> tryResult = exec.submit(new TryCallbleThread(monitor,pointValues,precision,min,max));
			tryResults.add(tryResult);
			
			min = max;
		}
		
		
		exec.shutdown();
	}

}
