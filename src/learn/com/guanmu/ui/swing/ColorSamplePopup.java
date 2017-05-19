/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class ColorSamplePopup {
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("JColorChooser Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				final JButton button = new JButton("Pick to change background");
				
				ActionListener actionListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Color initiaBackground = button.getBackground();
						Color background = JColorChooser.showDialog(
								null, "Change buttong background", initiaBackground);
						
						if (background != null) {
							button.setBackground(background);
						}
					}
					
				};
				button.addActionListener(actionListener);
				
				frame.add(button, BorderLayout.CENTER);
				frame.setSize(300,100);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);			
	}
}
