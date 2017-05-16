/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
		paramPanel = new JPanel();
		
		paramPanel.setLayout(new GridBagLayout());
		
		JLabel precisionLabel = new JLabel("精度：");
		paramPanel.add(precisionLabel);
		GridBagLayoutUtils.addComponent(paramPanel, precisionLabel, 0, 0, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE);
		precisionText = new JTextField();
		GridBagLayoutUtils.addComponent(paramPanel, precisionText, 1, 0, 3, 1, GridBagConstraints.WEST,GridBagConstraints.BOTH);
		
		JLabel xLabel = new JLabel("x:");
		GridBagLayoutUtils.addComponent(paramPanel, xLabel, 0, 1, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE);
		xText = new JTextField();
		GridBagLayoutUtils.addComponent(paramPanel, xText, 1, 1, 3, 1, GridBagConstraints.WEST,GridBagConstraints.BOTH);
		
		JLabel yLabel = new JLabel("y:");
		GridBagLayoutUtils.addComponent(paramPanel, yLabel, 0, 2, 1, 1, GridBagConstraints.WEST,GridBagConstraints.NONE);
		yText = new JTextField();
		GridBagLayoutUtils.addComponent(paramPanel, yText, 1, 2, 3, 1, GridBagConstraints.WEST,GridBagConstraints.BOTH);
		
		this.add(paramPanel,BorderLayout.NORTH);
		
		dataTable = new JTable();
		this.add(dataTable,BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		
		addValueBtn = new JButton("添加数据");
		buttonPanel.add(addValueBtn);
		
		deleteValueBtn = new JButton("删除数据");
		buttonPanel.add(deleteValueBtn);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
		
	}
	
	
	
}
