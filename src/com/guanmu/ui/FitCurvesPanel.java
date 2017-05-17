/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

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
		
	}
	
	@Override
	protected void createPanels() {
		createTitle();
		
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

	private static JFreeChart createChart(XYDataset paramXYDataset) {
		JFreeChart localJFreeChart = ChartFactory.createXYLineChart("Line Chart Demo 4", "X", "Y", paramXYDataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		localXYPlot.setDomainZeroBaselineVisible(true);
		localXYPlot.setRangeZeroBaselineVisible(true);
		localXYPlot.getDomainAxis().setLowerMargin(0.0D);
		localXYPlot.getDomainAxis().setUpperMargin(0.0D);
		localXYPlot.setDomainPannable(true);
		localXYPlot.setRangePannable(true);
		XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer) localXYPlot.getRenderer();
		localXYLineAndShapeRenderer.setLegendLine(new Rectangle2D.Double(-4.0D, -3.0D, 8.0D, 6.0D));
		return localJFreeChart;
	}
	
	/**
	 * 
	 */
	private void createTitle() {
		nameLabel = new JLabel("拟合法");
		nameLabel.setBackground(Color.GREEN);
	}	
	
	
}
