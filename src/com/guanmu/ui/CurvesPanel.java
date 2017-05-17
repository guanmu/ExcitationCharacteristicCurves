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
public class CurvesPanel extends JPanel {

	private static final long serialVersionUID = 6408235105966866867L;
	
	protected JLabel nameLabel;
	
	protected JPanel dataPanel;
	
	protected JPanel resultPanel;
	
	/**
	 * 
	 */
	public CurvesPanel() {
		this.setLayout(new BorderLayout());
		
		createPanels();
				
		this.add(nameLabel,BorderLayout.NORTH);
		this.add(dataPanel,BorderLayout.CENTER);
		this.add(resultPanel,BorderLayout.SOUTH);
	}

	/**
	 * 
	 */
	protected void createPanels() {
		nameLabel = new JLabel("逼近法");
		
		dataPanel = new JPanel();
		
		resultPanel = new JPanel();
	}
	
	
	
}
