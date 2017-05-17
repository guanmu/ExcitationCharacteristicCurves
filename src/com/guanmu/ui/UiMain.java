/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import com.guanmu.utils.GridBagLayoutUtils;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu
 * 
 * @author wangquan 2017-4-28
 * 
 */
public class UiMain extends JFrame {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 755498983129736454L;
	
	/** 主界面*/
	private JPanel mainPanel;
	
	/** 数据面板*/
	private JPanel valuePanel;

	
	/** 拟合法曲线面板*/
	private JPanel fitCurvesPanel;
	
	/** 逼近法曲线面板*/
	private JPanel tryCurvesPanel;


	/**
	 * @throws HeadlessException
	 */
	public UiMain() throws HeadlessException {
		super();
		this.setTitle("电压互感器空载特性分析");
		this.setBounds(200, 200, 1400, 680);
		
		mainPanel = new JPanel();
		this.add(mainPanel);
		this.setVisible(true);
		mainPanel.setLayout(new GridBagLayout());
		
		createValuePanel();
		
		createFitCurvesPanel();
		
		createTryCurvesPanel();
		
	}


	private void createTryCurvesPanel() {
		tryCurvesPanel = new TryCurvesPanel();
		tryCurvesPanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,tryCurvesPanel,3,0,0,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);
	}


	private void createFitCurvesPanel() {
		fitCurvesPanel = new FitCurvesPanel();
		fitCurvesPanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,fitCurvesPanel,1,0,2,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);
		
	}
	
	private void createValuePanel() {
		valuePanel = new ValuePanel();
		valuePanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,valuePanel,0,0,1,1,
				0,1.0,
				GridBagConstraints.WEST,GridBagConstraints.BOTH);
	}


	public static void main(String[] args) {
		
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				UiMain uiMain = new UiMain();
				uiMain.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);

	}

}
