/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui
 * @author wangquan 2017-5-15
 * 
 */
public class CurvesPanel extends JPanel {

	private static final long serialVersionUID = -1401071675475716518L;

	protected String title = "";
	
	protected JPanel dataPanel;
	
	protected JPanel resultPanel;
	
	/**
	 * 
	 */
	public CurvesPanel() {
		this.setLayout(new BorderLayout());
	}

	/**
	 * 
	 */
	protected void createPanels() {
		
		dataPanel = new JPanel();
		
		resultPanel = new JPanel();
	}
	
	protected void addPanels() {
		this.add(dataPanel,BorderLayout.CENTER);
		this.add(resultPanel,BorderLayout.SOUTH);
	}
	
	protected JFreeChart createChart(XYDataset paramXYDataset) {
		JFreeChart localJFreeChart = ChartFactory.createXYLineChart(title, "X", "Y", paramXYDataset,
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
	
}
