/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

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
public class ValuePanel extends JPanel {

	private static final long serialVersionUID = -6995856246269794959L;

	private JPanel paramPanel;
	
	private JTextField precisionText;
	private JTextField xText;
	private JTextField yText;
	
	private JTable dataTable;
	
	private JPanel buttonPanel;
	private JButton addValueBtn;
	private JButton deleteValueBtn;
	
	/**
	 * 
	 */
	public ValuePanel() {
		this.setLayout(new BorderLayout());
		
		createParamPanel();

		dataTable = new JTable();
		this.add(dataTable,BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		
		addValueBtn = new JButton("添加数据");
		buttonPanel.add(addValueBtn);
		
		deleteValueBtn = new JButton("删除数据");
		buttonPanel.add(deleteValueBtn);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
		
	}

	/**
	 * 
	 */
	private void createParamPanel() {
		
		paramPanel = new JPanel();
		
		paramPanel.setLayout(new GridLayout(0,1));
		
		JPanel boxPanel1 = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(boxPanel1, BoxLayout.X_AXIS);
		boxPanel1.setLayout(boxLayout1);
		
		JLabel precisionLabel = new JLabel("精度：");
		boxPanel1.add(precisionLabel);
		precisionText = new JTextField();
		boxPanel1.add(precisionText);
		
		JPanel boxPanel2 = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(boxPanel2, BoxLayout.X_AXIS);
		boxPanel2.setLayout(boxLayout2);	
		
		JLabel xLabel = new JLabel("x:");
		boxPanel2.add(xLabel);
		xText = new JTextField();
		boxPanel2.add(xText);
		
		JPanel boxPanel3 = new JPanel();
		BoxLayout boxLayout3 = new BoxLayout(boxPanel3, BoxLayout.X_AXIS);
		boxPanel3.setLayout(boxLayout3);
		
		JLabel yLabel = new JLabel("y:");
		boxPanel3.add(yLabel);
		yText = new JTextField();
		boxPanel3.add(yText);
		
		paramPanel.add(boxPanel1);
		paramPanel.add(boxPanel2);
		paramPanel.add(boxPanel3);
		
		this.add(paramPanel,BorderLayout.NORTH);
	}
	
	
	
}
