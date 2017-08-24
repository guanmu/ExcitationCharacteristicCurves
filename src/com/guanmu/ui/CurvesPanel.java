/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import com.guanmu.model.CurvesXYDataset;
import com.guanmu.utils.ExConfig;
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
	
	protected JFreeChart localJFreeChart;
	protected CurvesXYDataset xyDataset;
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
	
	protected void initStatus() {
		
		aValue.setEditable(false);
		bValue.setEditable(false);
		cValue.setEditable(false);
		dValue.setEditable(false);
	}
	
	/**
	 * 
	 */
	protected void createFunctionResultPanle() {
		functionResultPanel = new JPanel();
		functionResultPanel.setLayout(new GridBagLayout());
		
		functionLabel = new JLabel();
		functionLabel.setText("I=a*e^(b*U) + c*e^(d*U)");
		
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
		
		initStatus();
	}
	
	protected JFreeChart createChart(XYDataset paramXYDataset) {
		localJFreeChart = ChartFactory.createXYLineChart(title, "U(V)", "I(mA)", paramXYDataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		localXYPlot.setDomainZeroBaselineVisible(true);
		localXYPlot.setRangeZeroBaselineVisible(true);
		localXYPlot.getDomainAxis().setLowerMargin(0D);
		localXYPlot.getDomainAxis().setUpperMargin(1D);
		
		localXYPlot.getDomainAxis().setLowerBound(0);
		
		
		localXYPlot.getRangeAxis().setLowerBound(0);
		localXYPlot.setDomainPannable(true);
		localXYPlot.setRangePannable(true);		
		
		// 设置point中的提示图形：小矩形
		XYLineAndShapeRenderer pointLineAndShapeRenderer = new XYLineAndShapeRenderer();
		pointLineAndShapeRenderer.setLegendLine(new Rectangle2D.Double(-1.0D, -1.0D, 2.0D, 2.0D));
		pointLineAndShapeRenderer.setSeriesLinesVisible(0, false);
		pointLineAndShapeRenderer.setSeriesPaint(0, new Color(255, 0, 0));
		
		pointLineAndShapeRenderer.setSeriesShapesVisible(1, false);
		pointLineAndShapeRenderer.setSeriesPaint(1, new Color(0, 0, 255));
		
		pointLineAndShapeRenderer.setSeriesShapesVisible(2, false);
		pointLineAndShapeRenderer.setSeriesPaint(2, new Color(0, 0, 0));
		
		pointLineAndShapeRenderer.setSeriesLinesVisible(3, false);
		pointLineAndShapeRenderer.setSeriesPaint(3, new Color(0, 255, 255));		
		
	    pointLineAndShapeRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
	    localXYPlot.setRenderer(0,pointLineAndShapeRenderer);
	    
		XYLineAndShapeRenderer fitLineAndShapeRenderer = new XYLineAndShapeRenderer();
	    localXYPlot.setRenderer(1,fitLineAndShapeRenderer);	    
	    
		XYLineAndShapeRenderer tryLineAndShapeRenderer = new XYLineAndShapeRenderer();
	    localXYPlot.setRenderer(2,tryLineAndShapeRenderer);	
	    
		XYLineAndShapeRenderer pointLineAndShapeRenderer2 = new XYLineAndShapeRenderer();
		pointLineAndShapeRenderer2.setLegendLine(new Rectangle2D.Double(-2D, -2D, 4.0D, 4.0D));
	    localXYPlot.setRenderer(3,pointLineAndShapeRenderer2);	    
	    
	    NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
	    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());	
	    
	    // y轴
	    localNumberAxis.setRange(0, ExConfig.Y_DEFUALT_MAX);
	    // x轴
	    localXYPlot.getDomainAxis().setRange(ExConfig.X_START, ExConfig.X_DEFUALT_MAX);
	    
	    
		return localJFreeChart;
	}	
	

	/**
	 * 
	 */
	protected void createDataPanel() {				
		xyDataset = new CurvesXYDataset();
	    JFreeChart localJFreeChart = createChart(xyDataset);
	    ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
	    localChartPanel.setMouseWheelEnabled(true);
		
	    
	    xyDataset.getSeries();
	    
	    dataPanel = localChartPanel;
	}
	
	public void autoUpdateXYAixs() {
		XYPlot localXYPlot = (XYPlot) localJFreeChart.getPlot();
		NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
		
		CurvesXYDataset dataSet = (CurvesXYDataset)localXYPlot.getDataset();
		
		List<XYSeries> serieses = dataSet.getSeries();
		
		double max = 0;
		for(int i = 0;i < serieses.size();i++) {
			XYSeries series = serieses.get(i);
			double maxValue = series.getMaxY();
			
			if (maxValue > max) {
				max = maxValue;
			}
		}
		
		max = max * 1.1;
		
		localNumberAxis.setRange(0, max);
	}	
}
