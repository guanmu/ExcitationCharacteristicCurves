/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class KeyTextTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("Key Text Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				KeyTextComponent keyTextComponent = new KeyTextComponent();
				final JTextField textField = new JTextField();
				
				ActionListener actionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						String keyText = actionEvent.getActionCommand();
						textField.setText(keyText);
					}
				};
				
				keyTextComponent.addActionListener(actionListener);
				
				frame.add(keyTextComponent,BorderLayout.CENTER);
				frame.add(textField,BorderLayout.SOUTH);
				frame.setSize(300, 200);
				frame.setVisible(true);
			}
			
		};
		
		EventQueue.invokeLater(runner);
	}


}
