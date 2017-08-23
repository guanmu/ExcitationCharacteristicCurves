package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.OptionPaneUtils;
import com.guanmu.utils.RootLogger;

public class ResultPanel extends JPanel {

	private static final long serialVersionUID = -1959771411616075955L;
	
	private static final Logger logger = RootLogger.getLog(ResultPanel.class.getName());	
	
	private JLabel relateLabel;
	private JTextField relateValue;
	
	private JLabel changeYPointLabel;
	private JTextField changeYPointValue;
	private JButton changeBtn;
	
	private JLabel changeXPointLabel;
	private JTextField changeXPointValue;	
	
	private ExFunction exFunction;
	
	private JFrame parentFrame;
	
	public ResultPanel(JFrame parentFrame) {
		
		this.setLayout(new BorderLayout());
		
		this.parentFrame = parentFrame;
		
		createRelatePanel();
		
		createChangePointPanel();
		
		initStatus();

	}

	private void initStatus() {
		relateValue.setEditable(false);
		
		changeYPointValue.setEditable(true);
		changeXPointValue.setEditable(false);
		
		changeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (exFunction == null) {
					logger.error("exFunction is null.");
					return;
				}
				
				String infexionYStr = changeYPointValue.getText();
				if (infexionYStr.isEmpty()) {
					OptionPaneUtils.openMessageDialog(parentFrame,"请输入拐点y'的值。");
					return;
				}
				
				double infexionY = 0;
				try {
					infexionY = Double.parseDouble(infexionYStr);
				} catch (Exception ex) {
					OptionPaneUtils.openMessageDialog(parentFrame,"精度输入不正确。");
					return;
				}					
				
				double infexionX = exFunction.caculateInfexionX(infexionY);
				
				infexionX = ExConfig.formatDouble(infexionX);
				changeXPointValue.setText("" + infexionX);
			}
		});
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
		changeBtn.setEnabled(false);
		
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
		exFunction = function;
		changeBtn.setEnabled(true);
	}

	public void clearInfo() {
		relateValue.setText("N/A");
		changeBtn.setEnabled(false);
		changeXPointValue.setText("");
		exFunction = null;
	}
	
	
}
