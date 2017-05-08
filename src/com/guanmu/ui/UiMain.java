/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

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
	private JTextField precisionText;
	private JTextField xText;
	private JTextField yText;
	private JButton addValueBtn;
	
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
		
		createMainPanel();

		createValuePanel();
		
		createFitCurvesPanel();
		
		createTryCurvesPanel();
		
		this.add(mainPanel, null);
		
		
	}


	private void createTryCurvesPanel() {
		// TODO Auto-generated method stub
		
	}


	private void createFitCurvesPanel() {
		// TODO Auto-generated method stub
		
	}


	private void createValuePanel() {
		// TODO Auto-generated method stub
		valuePanel = new JPanel();
		valuePanel.setBorder(new CompoundBorder());
		
		mainPanel.add(valuePanel);
	}


	private void createMainPanel() {
		mainPanel = new JPanel();
		
		// TODO 
	}


	public static void main(String[] args) {
		UiMain uiMain = new UiMain();
		uiMain.setVisible(true);
		

	}

}
