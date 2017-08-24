/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.text.DecimalFormat;
import java.util.List;

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
		title = "曲线图";
		
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
	 * @param fitResults 
	 * @param function
	 */
	public void drawCurves(PointData pointData, List<PointValue> fitResults, ExFunction function) {
		xyDataset.changeValue(pointData,fitResults,function);
	
		aValue.setText("" + function.getA());
		bValue.setText("" + function.getB());
		
		cValue.setText("" + function.getCStr());
		dValue.setText("" + function.getDStr());
		
		
	}



	
	
}
