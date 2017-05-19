/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
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
public class CreateColorSamplePopup {
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
						
						final JColorChooser colorChooser = 
								new JColorChooser(initiaBackground);
						
						final JLabel previewLabel = new JLabel("I Love Swing", JLabel.CENTER);
						previewLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
						previewLabel.setSize(previewLabel.getPreferredSize());
						previewLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
						colorChooser.setPreviewPanel(previewLabel);
						
						ActionListener okActionListener = new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								Color newColor = colorChooser.getColor();
								
								if (newColor.equals(button.getForeground())) {
									System.out.println("Color change rejected");
								} else {
									button.setBackground(colorChooser.getColor());
								}
							}
						};
						
						ActionListener cancelActionListener = new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								button.setBackground(Color.RED);
							}
						};
						
						final JDialog dialog = JColorChooser.createDialog(
								null, "Change Button Background", true, colorChooser,
								okActionListener, cancelActionListener);
						
						Runnable showDialog = new Runnable() {
							
							@Override
							public void run() {
								dialog.setVisible(true);
							}
						};
						EventQueue.invokeLater(showDialog);	
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
