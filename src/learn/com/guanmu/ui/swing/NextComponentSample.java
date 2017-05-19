/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SortingFocusTraversalPolicy;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-11
 * 
 */
public class NextComponentSample {
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Reverse Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				frame.setLayout(new GridLayout(3,3));
				for (int i = 9;i > 0;i--) {
					JButton button = new JButton("" + i);
					frame.add(button,0);
				}
				
				final Container contentPane = frame.getContentPane();
				Comparator<Component> comp = new Comparator<Component>() {

					@Override
					public int compare(Component c1, Component c2) {
						Component comps[] = contentPane.getComponents();
						
						List<Component> list = Arrays.asList(comps);
						int first = list.indexOf(c1);
						int second = list.indexOf(c2);
						
						return second - first;
					}
					
				};
				
				FocusTraversalPolicy policy = new SortingFocusTraversalPolicy(comp);
				frame.setFocusTraversalPolicy(policy);
				
				frame.setSize(300, 200);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);
		
	}
}
