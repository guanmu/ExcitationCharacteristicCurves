/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

import com.guanmu.utils.GridBagLayoutUtils;

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
	
	protected JPanel functionResultPanel;
	
	protected JLabel functionLabel;
	
	protected JLabel aLabel;
	protected JTextField aValue;
	
	protected JLabel bLabel;
	protected JTextField bValue;
	protected JLabel cLabel;
	protected JTextField cValue;
	protected JLabel dLabel;
	protected JTextField dValue;
	
	/**
	 * 
	 */
	public CurvesPanel() {
		this.setLayout(new BorderLayout());
		
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
	}

	/**
	 * 
	 */
	protected void createPanels() {
		
		dataPanel = new JPanel();

	}
	
	/**
	 * 
	 */
	protected void createFunctionResultPanle() {
		functionResultPanel = new JPanel();
		functionResultPanel.setLayout(new GridBagLayout());
		
		functionLabel = new JLabel();
		functionLabel.setText("y1=a*e^(b*x) + c*e^(d*x)");
		
		GridBagLayoutUtils.addComponent(functionResultPanel,functionLabel,0,0,4,1,
				0,0,
				GridBagConstraints.CENTER,GridBagConstraints.NONE);		
		
		aLabel = new JLabel("a=");
		GridBagLayoutUtils.addComponent(functionResultPanel,aLabel,0,1,1,1,
				0,0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		aValue = new JTextField();
		GridBagLayoutUtils.addComponent(functionResultPanel,aValue,1,1,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		bLabel = new JLabel("b=");
		GridBagLayoutUtils.addComponent(functionResultPanel,bLabel,2,1,1,1,
				0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		bValue = new JTextField();
		GridBagLayoutUtils.addComponent(functionResultPanel,bValue,3,1,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		cLabel = new JLabel("c=");
		GridBagLayoutUtils.addComponent(functionResultPanel,cLabel,0,2,1,1,
				0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		cValue = new JTextField();
		GridBagLayoutUtils.addComponent(functionResultPanel,cValue,1,2,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		dLabel = new JLabel("d=");
		GridBagLayoutUtils.addComponent(functionResultPanel,dLabel,2,2,1,1,
				0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		dValue = new JTextField();
		GridBagLayoutUtils.addComponent(functionResultPanel,dValue,3,2,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);	
		
		functionResultPanel.setVisible(true);
	}	
	
	protected void addPanels() {
		this.add(dataPanel,BorderLayout.CENTER);
		this.add(functionResultPanel,BorderLayout.SOUTH);
	}
	
	protected JFreeChart createChart(XYDataset paramXYDataset) {
		JFreeChart localJFreeChart = ChartFactory.createXYLineChart(title, "X(mA)", "Y(V)", paramXYDataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		localXYPlot.setDomainZeroBaselineVisible(true);
		localXYPlot.setRangeZeroBaselineVisible(true);
		localXYPlot.getDomainAxis().setLowerMargin(0D);
		localXYPlot.getDomainAxis().setUpperMargin(0D);
		
		localXYPlot.getDomainAxis().setLowerBound(0);
		
		localXYPlot.getRangeAxis().setLowerBound(0);
		localXYPlot.setDomainPannable(true);
		localXYPlot.setRangePannable(true);		
		
		// 设置legen中的曲线提示图形：小矩形
		XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer) localXYPlot.getRenderer();
		localXYLineAndShapeRenderer.setLegendLine(new Rectangle2D.Double(-4.0D, -3.0D, 8.0D, 6.0D));
	
	    
		return localJFreeChart;
	}	
	
}
