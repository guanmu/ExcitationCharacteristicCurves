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
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.OptionPaneUtils;
import com.guanmu.utils.RootLogger;

public class InfexionController {
	
	private static final Logger logger = RootLogger.getLog(InfexionController.class.getName());
	
	private CurvesProgressMonitor monitor;

	private ExFunction function;
		
	public InfexionController(CurvesProgressMonitor monitor, ExFunction function) {
		this.monitor = monitor;
		this.function = function;
	}

	public void start() throws Exception {
		
		Thread.currentThread().setName("InfexionController Thread");
		
		logger.info("###start infexion caculate");
		monitor.addProgress(5, 90);
		
		double infexionX = 0;
		double infexionY = 0;
		
		
		for(int i = 100;i <= 200;i++) {
			
			double initY = function.getYValue(i);
			
			double tmpY = function.getYValue(i* 1.1);
			
			double tmpChange = tmpY/initY;
			if (tmpChange > ExConfig.MIN_INFEXION_CHANGE) {
				infexionX = i;
				infexionY = initY;
				break;
			}
			
			monitor.addProgress(1, 90);
		}
		
		logger.info("###end infexion caculate.[{}]",infexionX);
		
		UiMain.instance.drawInfexionPoint(infexionX,infexionY);
		
		monitor.close();
	}

}
