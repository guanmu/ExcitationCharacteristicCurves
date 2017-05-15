/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

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
		
		paramPanel = new JPanel();
		
		precisionText = new JTextField();
		precisionText.setText("精度：");
		paramPanel.add(precisionText);
		
		xText = new JTextField("x:");
		paramPanel.add(xText);
		
		yText = new JTextField("y:");
		paramPanel.add(yText);
		
		this.add(paramPanel,BorderLayout.NORTH);
		
		dataTable = new JTable();
		this.add(dataTable,BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		
		addValueBtn = new JButton("添加数据");
		buttonPanel.add(addValueBtn);
		
		deleteValueBtn = new JButton("删除数据");
		buttonPanel.add(deleteValueBtn);
		
	}
	
	
	
}
