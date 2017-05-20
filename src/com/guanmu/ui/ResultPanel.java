package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ResultPanel extends JPanel {

	private static final long serialVersionUID = -1959771411616075955L;
	
	private JLabel relateLabel;
	private JTextField relateValue;
	
	private JLabel changePointLabel;
	private JTextField changePointValue;
	
	public ResultPanel() {
		
		this.setLayout(new BorderLayout());
		
		createRelatePanel();
		
		createChangePointPanel();
		
		initStatus();

	}

	private void initStatus() {
		relateValue.setEditable(false);
		
		changePointValue.setEditable(false);
		
	}

	private void createChangePointPanel() {
		JPanel changePointPanel = new JPanel();
		changePointPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		changePointLabel = new JLabel("拐点  ：");
		changePointPanel.add(changePointLabel);
		
		changePointValue = new JTextField();
		changePointValue.setColumns(33);
		changePointValue.setText("x=10,y=10");
		changePointPanel.add(changePointValue);
		
		
		changePointPanel.setVisible(true);
		
		this.add(changePointPanel,BorderLayout.SOUTH);
	}

	private void createRelatePanel() {
		JPanel relatePanel =  new JPanel();
		relatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		relateLabel = new JLabel("线性相关度：");
		relatePanel.add(relateLabel);
		
		relateValue = new JTextField("N/A");
		relateValue.setColumns(30);
		relatePanel.add(relateValue);
		
		relatePanel.setVisible(true);
		
		this.add(relatePanel,BorderLayout.NORTH);
	}
	
	
}
