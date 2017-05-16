/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.sun.corba.se.spi.orbutil.fsm.Input;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class ButtonSmaple {
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Button Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JButton button = new JButton("Select Me");
				
				ActionListener actionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("I was selected.");
					}
				};
				
				MouseListener mouserListener = new MouseAdapter() {
					/* (non-Javadoc)
					 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
					 */
					@Override
					public void mousePressed(MouseEvent mouserEvent) {
						int modifiers = mouserEvent.getModifiers();
						
						if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
							System.out.println("Left button pressed.");
						}
						
						if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
							System.out.println("Middle button pressed.");
						}
						
						if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
							System.out.println("Right button pressed.");
						}
						
					}
					
					/* (non-Javadoc)
					 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
					 */
					@Override
					public void mouseReleased(MouseEvent mouserEvent) {
						if (SwingUtilities.isLeftMouseButton(mouserEvent)) {
							System.out.println("Left button released.");
						}
						
						if (SwingUtilities.isMiddleMouseButton(mouserEvent)) {
							System.out.println("Middle button released.");
						}
						
						if (SwingUtilities.isRightMouseButton(mouserEvent)) {
							System.out.println("Right button released.");
						}
					}
				};
								
				button.addActionListener(actionListener);
				button.addMouseListener(mouserListener);
				
				frame.add(button,BorderLayout.SOUTH);
				frame.setSize(300,100);
				frame.setVisible(true);
			}
		};
		
		EventQueue.invokeLater(runner);
	}
}
