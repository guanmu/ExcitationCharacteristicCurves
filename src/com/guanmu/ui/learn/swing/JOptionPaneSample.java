/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.learn.swing;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import com.guanmu.utils.DiamondIcon;
import com.guanmu.utils.OptionPaneUtils;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class JOptionPaneSample {
	
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
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("JOptionPane Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				frame.setSize(300, 100);
				frame.setVisible(true);
				
//				JOptionPane.showMessageDialog(frame, "sssssssssssss", "title", 1);
				
				String msg = "this is a really long message ................................................................ this is end";
				JOptionPane optionPane = OptionPaneUtils.getNarrowOptionPane(30);
				optionPane.setMessage(msg);
				optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				
//				JDialog dialog = optionPane.createDialog(frame, "Width 72");
//				dialog.setVisible(true);
				
				JOptionPane sliderOptionPane = new JOptionPane();
				JSlider slider = OptionPaneUtils.getSlider(sliderOptionPane);
				sliderOptionPane.setMessage(new Object[] {"Select a value:", slider});
				sliderOptionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				sliderOptionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
//				JDialog dialog = sliderOptionPane.createDialog(frame,"My Slider");
//				dialog.setVisible(true);
//				System.out.println("Input:" + sliderOptionPane.getInputValue());
				
				JOptionPane buttonPane = new JOptionPane();
				buttonPane.setMessage("I got an icon and a text label");
				buttonPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
				Icon icon = new DiamondIcon(Color.BLUE);
				JButton jButton = OptionPaneUtils.getButton(buttonPane, "Test", icon);
				buttonPane.setOptions(new Object[] {jButton});
				JDialog dialog = buttonPane.createDialog(frame,"Icon/Text Button");
				dialog.setVisible(true);
				System.out.println("Button value:" + buttonPane.getValue());
				
				
			}
		};
		
		EventQueue.invokeLater(runner);
		
	}
}
