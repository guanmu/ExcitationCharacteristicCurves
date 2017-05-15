/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui
 * @author wangquan 2017-5-15
 * 
 */
public class TryCurvesPanel extends JPanel {

	private static final long serialVersionUID = 6408235105966866867L;
	
	private JLabel nameLabel;
	
	private JPanel dataPanel;
	
	private JPanel resultPanel;
	
	/**
	 * 
	 */
	public TryCurvesPanel() {
		this.setLayout(new BorderLayout());

		nameLabel = new JLabel("拟合法");
		
		dataPanel = new JPanel();
		
		resultPanel = new JPanel();
		
		
		this.add(nameLabel,BorderLayout.NORTH);
		this.add(dataPanel,BorderLayout.CENTER);
		this.add(resultPanel,BorderLayout.SOUTH);
	}
	
	
	
}
