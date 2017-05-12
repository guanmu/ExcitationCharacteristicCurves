/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.swing.learn;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-12
 * 
 */
public class VerifierSample {
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Verifier Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JTextField textField1 = new JTextField();
				JTextField textField2 = new JTextField();
				JTextField textField3 = new JTextField();
				
				InputVerifier verifier = new InputVerifier() {
					
					@Override
					public boolean verify(JComponent input) {
						boolean returnValue = false;
						
						JTextField textField = (JTextField) input;
						try {
							Integer.parseInt(textField.getText());
							return true;
						} catch (NumberFormatException e) {
							returnValue = false;
						}
						
						return returnValue;
					}
				};
				
				textField1.setInputVerifier(verifier);
				textField3.setInputVerifier(verifier);
				
				frame.add(textField1,BorderLayout.NORTH);
				frame.add(textField2,BorderLayout.CENTER);
				frame.add(textField3,BorderLayout.SOUTH);
				
				frame.setSize(300, 100);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);
		
	}
	
}
