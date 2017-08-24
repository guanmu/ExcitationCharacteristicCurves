package com.guanmu.thread;

import java.util.Date;
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

public class InfexionController {
	
	private static final Logger logger = RootLogger.getLog(InfexionController.class.getName());
	
	private CurvesProgressMonitor monitor;

	private ExFunction function;
	
	private double infexionY;
	
	public InfexionController(CurvesProgressMonitor monitor, ExFunction function,double infexionY) {
		this.monitor = monitor;
		this.function = function;
		this.infexionY = infexionY;
	}

	public void start() throws Exception {
		
		Thread.currentThread().setName("InfexionController Thread");
		
		logger.info("###start infexion caculate");
		monitor.addProgress(5, 90);
		
		double infexionX = 0;
		double error = Double.MAX_VALUE;
		for(int i = 50;i <= 160;i++) {
			
			double tmpY = function.caculateInfexionY(i);
			
			double tmpError = Math.abs(infexionY - tmpY);
			if (tmpError < error) {
				infexionX = i;
				error = tmpError;
			}
			
			monitor.addProgress(1, 90);
		}
		
		logger.info("###end infexion caculate.[{}]",infexionX);
		
		UiMain.instance.drawInfexionX(infexionX);
		
		monitor.close();
	}

}
