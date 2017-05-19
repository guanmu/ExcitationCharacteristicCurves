/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-15
 * 
 */
public class ActionChangedListener implements PropertyChangeListener {

	private JButton button;

	/**
	 * @param button
	 */
	public ActionChangedListener(JButton button) {
		super();
		this.button = button;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String properytName = evt.getPropertyName();
		
		if (Action.NAME.equals(properytName)) {
			String text = (String)evt.getNewValue();
			button.setText(text);
			button.repaint();
		} else if ("enabled".equals(properytName)) {
			Boolean enabledState = (Boolean) evt.getNewValue();
			
			button.setEnabled(enabledState);
			button.repaint();
		} else if (Action.SMALL_ICON.equals(properytName)) {
			Icon icon = (Icon)evt.getNewValue();
			button.setIcon(icon);
			button.invalidate();
			button.repaint();
		}
		
		
	}

}
