/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class SampleProgress {
	
	static ProgressMonitor monitor;
	static int progress;
	static Timer timer;
	
	static class ProgressMonitorHandler implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (monitor == null) {
				return;
			}
			
			if (monitor.isCanceled()) {
				System.out.println("M canceled");
				timer.stop();
			} else {
				progress += 3;
				monitor.setProgress(progress);
				monitor.setNote("Loaded " + progress + " files");
			}
		}
		
	}
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("ProgressMonitor Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new GridLayout(0,1));
				
				JButton startButton = new JButton("Start");
				ActionListener startActionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Component parent = (Component) e.getSource();
						monitor = new ProgressMonitor(parent, "Loading Progress", 
								"Getting started..", 0, 200);
						progress = 0;
						
						progress += 50;
						monitor.setProgress(progress);
						monitor.setNote("Loaded " + progress + " files");
					}
				};
				startButton.addActionListener(startActionListener);
				
				JButton increaseButton = new JButton("Manual Increase");
				ActionListener increaseActionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (monitor == null) {
							return;
						} 
						
						if (monitor.isCanceled()) {
							System.out.println("Monitor canceled");
						} else {
							progress += 5;
							monitor.setProgress(progress);
							monitor.setNote("Loaded " + progress + " files");
						}
					}
				};
				increaseButton.addActionListener(increaseActionListener);
				
				JButton autoIncreaseButton = new JButton("Automatic Increase");
				ActionListener autoIncreaseActionListener = new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (monitor != null) {
							if (timer == null) {
								timer = new Timer(250, new ProgressMonitorHandler());
							}
							timer.start();
						}
					}
				};
				autoIncreaseButton.addActionListener(autoIncreaseActionListener);
				
				frame.add(startButton);
				frame.add(increaseButton);
				frame.add(autoIncreaseButton);
				
				
				frame.setSize(300, 200);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);		
	}
}
