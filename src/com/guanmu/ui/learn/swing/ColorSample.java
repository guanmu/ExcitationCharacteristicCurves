/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class ColorSample {
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("JColorChooser Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				final JLabel label = new JLabel("I Love Swing",JLabel.CENTER);
				label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
				frame.add(label, BorderLayout.SOUTH);
				
				final JColorChooser colorChooser = 
						new JColorChooser(label.getBackground());
				colorChooser.setBorder(
						BorderFactory.createTitledBorder("Pick Foreground Color"));
				
				frame.add(colorChooser, BorderLayout.CENTER);
				
				frame.pack();
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);			
	}
}
