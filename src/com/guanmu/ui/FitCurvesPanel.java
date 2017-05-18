/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class FitCurvesPanel extends CurvesPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2141822560252516326L;

	/**
	 * 
	 */
	public FitCurvesPanel() {
		title = "拟合法";
		
		createPanels();
		
		addPanels();
	}
	
	@Override
	protected void createPanels() {
		
		createDataPanel();
		
		createResultPanle();
	}

	/**
	 * 
	 */
	private void createResultPanle() {
		resultPanel = new JPanel();
		
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
