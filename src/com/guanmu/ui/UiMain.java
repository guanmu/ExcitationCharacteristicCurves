/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * <p>
 * ¿‡√Ë ˆ:
 * <p>
 * 
 * À˘ Ù≤Âº˛:com.guanmu
 * 
 * @author wangquan 2017-4-28
 * 
 */
public class UiMain extends JFrame {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 755498983129736454L;
	
	private JLabel jLabel;
	 private JTextField jTextField;
	 private JButton jButton;

	/**
	 * @throws HeadlessException
	 */
	public UiMain() throws HeadlessException {
		super();
		this.setSize(300, 200);
		this.getContentPane().setLayout(null);
		this.add(getJLabel(), null);
		this.add(getJTextField(), null);
		this.add(getJButton(), null);
		this.setTitle("HelloWorld");

	}

	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setBounds(34, 49, 53, 18);
			jLabel.setText("Name:");
		}
		return jLabel;
	}

	private javax.swing.JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setBounds(96, 49, 160, 20);
		}
		return jTextField;
	}

	private javax.swing.JButton getJButton() {
		if (jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setBounds(103, 110, 71, 27);
			jButton.setText("OK");
		}
		return jButton;
	}

	public static void main(String[] args) {
		UiMain uiMain = new UiMain();
		uiMain.setVisible(true);
	}

}
