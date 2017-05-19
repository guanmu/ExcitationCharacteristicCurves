/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

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
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-10
 * 
 */
public class KeyTextComponent2 extends JComponent {

	private EventListenerList actionListenerList = new EventListenerList();
	
	/**
	 * 
	 */
	public KeyTextComponent2() {
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
					
					fireActionPerformed(actionEvent);
				}
			}
		};		
		
		
		MouseListener internalMouseListener = new MouseAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
			}
		};
		
		addKeyListener(internalKeyListener);
		addMouseListener(internalMouseListener);
	}
	
	
	public void addActionListener(ActionListener actionListener) {
		actionListenerList.add(ActionListener.class, actionListener);
	}
	
	public void removeActionListener(ActionListener actionListener) {
		actionListenerList.remove(ActionListener.class, actionListener);
	}	
	
	
	/**
	 * @param actionEvent
	 */
	private void fireActionPerformed(ActionEvent actionEvent) {
		EventListener listenerList[] = actionListenerList.getListeners(ActionListener.class);
		
		for(int i = 0, n = listenerList.length;i < n;i++) {
			((ActionListener)listenerList[i]).actionPerformed(actionEvent);
		}
	}
	
	public boolean isFocusable() {
		return true;
	}
	
	
	
}
