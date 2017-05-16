/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class ButtonSmaple23 {
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Button Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				final JButton button1 = new JButton("Select Me");
				final JButton button2 = new JButton("No Select Me");
				final Random random = new Random();
				
				ActionListener actionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						JButton button = (JButton) actionEvent.getSource();
						
						int red = random.nextInt(255);
						int green = random.nextInt(255);
						int blue = random.nextInt(255);
						
						button.setBackground(new Color(red,green,blue));
					}
				};
				
				PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
						String property = propertyChangeEvent.getPropertyName();
						if ("background".equals(property)) {
							button2.setBackground((Color)propertyChangeEvent.getNewValue());
						}
					}
				};
				
								
				button1.addActionListener(actionListener);
				button1.addPropertyChangeListener(propertyChangeListener);
				
				button2.addActionListener(actionListener);
				
				frame.add(button1,BorderLayout.NORTH);
				frame.add(button2,BorderLayout.SOUTH);
				
				frame.setSize(300,100);
				frame.setVisible(true);
			}
		};
		
		EventQueue.invokeLater(runner);
	}
}
