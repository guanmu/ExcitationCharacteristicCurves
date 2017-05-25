/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import com.guanmu.model.CurvesXYDataset;
import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
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
	 * @param pointData
	 * @param function
	 */
	public void drawCurves(PointData pointData, ExFunction function) {
		xyDataset.changeValue(pointData,function);
	
		aValue.setText("" + function.getA());
		bValue.setText("" + function.getB());
		cValue.setText("" + function.getC());
		dValue.setText("" + function.getD());
		
		
	}

	
	
}
