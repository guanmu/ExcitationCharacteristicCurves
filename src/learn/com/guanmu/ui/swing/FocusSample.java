/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class FocusSample {
	

	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			class ActionFocusMover implements ActionListener{
				

				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					KeyboardFocusManager manager = 
							KeyboardFocusManager.getCurrentKeyboardFocusManager();
					manager.focusNextComponent();
					
				}
			}	
			
			class MouseEnterFocusMover extends MouseAdapter{

				@Override
				public void mouseEntered(MouseEvent e) {
					Component component = e.getComponent();
					if (!component.hasFocus()) {
						component.requestFocusInWindow();
					}
					
				}
			}
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Focus Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				ActionListener actionListener = new ActionFocusMover();
				MouseListener mouseListener = new MouseEnterFocusMover();
				
				frame.setLayout(new GridLayout(3,3));
				for(int i = 1;i < 10;i++) {
					JButton button = new JButton(Integer.toString(i));
					button.addActionListener(actionListener);
					button.addMouseListener(mouseListener);
					
					if ((i%2) != 0) {
						button.setFocusable(false);
					}
					
					frame.add(button);
				}
				
				frame.setSize(300, 200);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);
		
	}
}
