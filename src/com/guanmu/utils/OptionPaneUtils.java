/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.utils
 * @author wangquan 2017-5-15
 * 
 */
public class OptionPaneUtils {
	
	public static JOptionPane getNarrowOptionPane(int maxCharactersPerLineCount) {
		
		class NarrowOptionPane extends JOptionPane {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6347820662407873300L;
			
			int maxCharactersPerLineCount;

			/**
			 * @param maxCharctersPerLineCount
			 */
			public NarrowOptionPane(int maxCharactersPerLineCount) {
				this.maxCharactersPerLineCount = maxCharactersPerLineCount;
			}

			/**
			 * @return the maxCharactersPerLineCount
			 */
			public int getMaxCharactersPerLineCount() {
				return maxCharactersPerLineCount;
			}
			
		}
		
		return new NarrowOptionPane(maxCharactersPerLineCount);
	}
	
	
	public static JSlider getSlider(final JOptionPane optionPane) {
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		ChangeListener changeListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider theSlieder = (JSlider) e.getSource();
				
				if (!theSlieder.getValueIsAdjusting()) {
					optionPane.setInputValue(new Integer(theSlieder.getValue()));
				}
			}
		};
		slider.addChangeListener(changeListener);
		
		return slider;
	}
	
	
	public static JButton getButton(final JOptionPane optionPane, String text, Icon icon) {
		final JButton button = new JButton(text, icon);
		
		ActionListener actionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				optionPane.setValue(button.getText());
			}
		};
		button.addActionListener(actionListener);
		
		return button;
	}
	
	public static JDialog openMessageDialog(Component parent,String message) {
		JOptionPane optionPane = OptionPaneUtils.getNarrowOptionPane(30);
		optionPane.setMessage(message);
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog(parent, "提示");
		dialog.setVisible(true);	
		
		return dialog;
	}
}
