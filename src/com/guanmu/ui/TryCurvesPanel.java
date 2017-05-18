/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.guanmu.ui.learn.jfreechart.SampleXYDataset;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui
 * @author wangquan 2017-5-15
 * 
 */
public class TryCurvesPanel extends CurvesPanel {

	private static final long serialVersionUID = 6408235105966866867L;

	
	/**
	 * 
	 */
	public TryCurvesPanel() {
		title = "逼近法";
		
		createPanels();
		
		addPanels();		
	}

	@Override
	protected void createPanels() {
		
		createDataPanel();
		
		createFunctionResultPanle();
	}

	/**
	 * 
	 */
	private void createDataPanel() {		
	    JFreeChart localJFreeChart = createChart(new SampleXYDataset());
	    ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
	    localChartPanel.setMouseWheelEnabled(true);
		
	    dataPanel = localChartPanel;
	}

	
	
}
