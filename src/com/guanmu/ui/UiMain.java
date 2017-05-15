/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

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
		this.setSize(900, 780);
		this.setTitle("电压互感器空载特性分析");
		this.getContentPane().setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setVisible(true);
		this.add(mainPanel);
		
		createValuePanel();
		
		createFitCurvesPanel();
		
		createTryCurvesPanel();
		
	}


	private void createTryCurvesPanel() {
		tryCurvesPanel = new TryCurvesPanel();
		tryCurvesPanel.setVisible(true);
		
		mainPanel.add(tryCurvesPanel, BorderLayout.EAST);
	}


	private void createFitCurvesPanel() {
		fitCurvesPanel = new FitCurvesPanel();
		fitCurvesPanel.setVisible(true);
		
		mainPanel.add(fitCurvesPanel,BorderLayout.CENTER);
		
	}
	
	private void createValuePanel() {
		valuePanel = new ValuePanel();
		valuePanel.setBorder(new CompoundBorder());
		valuePanel.setVisible(true);
		
		mainPanel.add(valuePanel,BorderLayout.WEST);
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
