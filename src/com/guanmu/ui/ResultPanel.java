package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;

public class ResultPanel extends JPanel {

	private static final long serialVersionUID = -1959771411616075955L;
	
	private JLabel relateLabel;
	private JTextField relateValue;
	
	private JLabel changeYPointLabel;
	private JTextField changeYPointValue;
	private JButton changeBtn;
	
	private JLabel changeXPointLabel;
	private JTextField changeXPointValue;	
	
	public ResultPanel() {
		
		this.setLayout(new BorderLayout());
		
		createRelatePanel();
		
		createChangePointPanel();
		
		initStatus();

	}

	private void initStatus() {
		relateValue.setEditable(false);
		
		changeYPointValue.setEditable(true);
		changeXPointValue.setEditable(false);
	}

	private void createChangePointPanel() {
		JPanel changePointPanel = new JPanel();
		changePointPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		changeYPointLabel = new JLabel("拐点 y'值 ：");
		changePointPanel.add(changeYPointLabel);
		
		changeYPointValue = new JTextField();
		changeYPointValue.setColumns(8);
		changeYPointValue.setText("");
		changePointPanel.add(changeYPointValue);
		
		changeBtn = new JButton();
		changeBtn.setText("计算拐点");
		changePointPanel.add(changeBtn);
		
		changeXPointLabel = new JLabel("拐点 x'值 ：");
		changePointPanel.add(changeXPointLabel);
		
		changeXPointValue = new JTextField();
		changeXPointValue.setColumns(8);
		changeXPointValue.setText("");		
		changePointPanel.add(changeXPointValue);
		
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

	/**
	 * @param pointData
	 * @param function
	 */
	public void addInfo(PointData pointData, ExFunction function) {
		relateValue.setText("" + function.getDeterCoeff());
		
	}
	
	
}
