/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.utils
 * @author wangquan 2017-5-15
 * 
 */
public class GridBagLayoutUtils {
	
	private static final Insets insets = new Insets(0,0,0,0);
	
	public static void addComponent(Container container,Component component,
			int gridx, int gridy, int gridWidth, int gridHeight, 
			double weightx, double weigthy,
			int anchor,	int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridWidth, gridHeight, weightx, weightx, anchor, fill,
				insets, 0, 0);
		container.add(component,gbc);
	}
}
