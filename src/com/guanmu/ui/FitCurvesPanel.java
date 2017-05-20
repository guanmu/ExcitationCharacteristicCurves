/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import com.guanmu.model.CurvesXYDataset;
import com.guanmu.model.ExcitationFunction;
import com.guanmu.model.PointValue;

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
		title = "插值法";
		
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
		ExcitationFunction exFunction = new ExcitationFunction(0.1, 0.1, 0.1, 0.5);
		List<PointValue> rows = new ArrayList<>();
		rows.add(new PointValue(1, 10d));
		rows.add(new PointValue(2, 30d));
		rows.add(new PointValue(10, 10));
		
		XYDataset xyDataset = new CurvesXYDataset();
		
	    JFreeChart localJFreeChart = createChart(xyDataset);
	    ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
	    localChartPanel.setMouseWheelEnabled(true);
		
	    dataPanel = localChartPanel;
	}

	
	
}
