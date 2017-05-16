/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class TimerSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				ActionListener actionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Hello World Timer");
					}
				};
				
				Timer timer = new Timer(500, actionListener);
				timer.start();
			};
		};
		
		
		EventQueue.invokeLater(runner);
	}

}
