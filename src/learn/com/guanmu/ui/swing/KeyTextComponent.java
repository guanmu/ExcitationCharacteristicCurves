/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import com.sun.java.accessibility.util.AWTEventMonitor;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class KeyTextComponent extends JComponent {
	
	private ActionListener actionListenerList = null;
	
	public KeyTextComponent() {
		setBackground(Color.CYAN);
		
		KeyListener internalKeyListener = new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (actionListenerList != null) {
					int keyCode = keyEvent.getKeyCode();
					String keyText = KeyEvent.getKeyText(keyCode);
					
					ActionEvent actionEvent = new ActionEvent(
							this,
							ActionEvent.ACTION_PERFORMED,
							keyText);
					
					actionListenerList.actionPerformed(actionEvent);
				}
			}
		};
		
		MouseListener internalMouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				requestFocusInWindow();
			};
		};
		
		
		addKeyListener(internalKeyListener);
		addMouseListener(internalMouseListener);
	}
	
	public void addActionListener(ActionListener actionListener) {
		actionListenerList = AWTEventMulticaster.add(actionListenerList, actionListener);
	}
	
	public void removeActionListener(ActionListener actionListener) {
		actionListenerList = AWTEventMulticaster.remove(actionListenerList, actionListener);
	}
	
	public boolean isFocusable() {
		return true;
	}
	
}
