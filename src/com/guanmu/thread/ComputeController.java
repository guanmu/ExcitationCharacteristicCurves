package com.guanmu.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.ui.CurvesProgressMonitor;
import com.guanmu.ui.UiMain;
import com.guanmu.utils.OptionPaneUtils;
import com.guanmu.utils.RootLogger;

public class ComputeController {
	
	private static final Logger logger = RootLogger.getLog(ComputeController.class.getName());
	
	private CurvesProgressMonitor monitor;
	
	private PointData pointData;
	
	private double precision;
	
	public ComputeController(CurvesProgressMonitor monitor, PointData pointData, double precision) {
		this.monitor = monitor;
		this.pointData = pointData;
		this.precision = precision;
	}

	public void start() throws Exception {
		
		Thread.currentThread().setName("ComputeController Thread");
		
		logger.info("###start compute function");
		
		ExecutorService exec = ExThreadPool.getInstance().getControllerExec();
		
		logger.info("FitCallbleThread submit before");
		Future<List<PointValue>> fitResult = exec.submit(new FitCallbleThread(monitor,pointData,precision));
		logger.info("FitCallbleThread submit after");

		Future<ExFunction> tryResult = exec.submit(new TryTotalCallbleThread(monitor, pointData, precision));

		
		exec.shutdown();
		
		if (exec.awaitTermination(30, TimeUnit.MINUTES)) {
			
			List<PointValue> fitResults = fitResult.get();
			ExFunction tryFunction = tryResult.get();
			
			if (fitResults == null) {
				OptionPaneUtils.openErrorDialog(UiMain.instance,"插值法处理失败。");
				return;
			}
			
			if (tryFunction == null) {
				OptionPaneUtils.openErrorDialog(UiMain.instance,"逼近法处理失败。");
				return;
			}
			
			logger.info("###fit result:" + fitResults);
			logger.info("###function:" + tryFunction);
			
			UiMain.instance.drawResults(pointData,fitResults,tryFunction);
			
			
		} else {
			logger.error("compute time out.");
			
			OptionPaneUtils.openErrorDialog(UiMain.instance,"精度过高，运算超时。");
		}
		
	}

}
