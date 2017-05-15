/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.swing.learn;

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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class AncestorSampler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Ancestor Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				AncestorListener ancestorListener = new AncestorListener() {
					
					@Override
					public void ancestorRemoved(AncestorEvent event) {
						System.out.println("remove");
						
					}
					
					@Override
					public void ancestorMoved(AncestorEvent event) {
						System.out.println("move");
					}
					
					@Override
					public void ancestorAdded(AncestorEvent event) {
						System.out.println("add");
					}
				};
				
				frame.getRootPane().addAncestorListener(ancestorListener);
				
				frame.setSize(300,200);
				frame.setVisible(true);
				
				frame.getRootPane().setVisible(false);
				frame.getRootPane().setVisible(true);
			}
		};
		
		EventQueue.invokeLater(runner);
	}

}
