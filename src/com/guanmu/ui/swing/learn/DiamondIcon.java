/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui.swing.learn;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui.swing.learn
 * @author wangquan 2017-5-12
 * 
 */
public class DiamondIcon implements Icon{
	private Color color;
	
	private boolean selected;
	private int width;
	private int height;
	private Polygon poly;
	private static final int DEFAULT_WIDTH = 10;
	private static final int DEFAULT_HEIGHT = 10;
	
	public DiamondIcon(Color color) {
		this(color,true,DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}

	/**
	 * @param color
	 * @param selected
	 * @param width
	 * @param height
	 */
	public DiamondIcon(Color color, boolean selected, int width, int height) {
		this.color = color;
		this.selected = selected;
		this.width = width;
		this.height = height;
		initPolygon();
	}

	/**
	 * 
	 */
	private void initPolygon() {
		poly = new Polygon();
		int halfWidth = width / 2;
		int halfHeight = height / 2;
		
		poly.addPoint(0, halfHeight);
		poly.addPoint(halfWidth, 0);
		poly.addPoint(width, halfHeight);
		poly.addPoint(halfWidth, height);
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(color);
		g.translate(x, y);
		
		if (selected) {
			g.fillPolygon(poly);
		} else {
			g.drawPolygon(poly);
		}
		
		g.translate(-x, -y);
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconWidth()
	 */
	@Override
	public int getIconWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see javax.swing.Icon#getIconHeight()
	 */
	@Override
	public int getIconHeight() {
		return height;
	}


	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new JFrame("Icon Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Icon icon = new DiamondIcon(Color.RED, true, 25, 25);
				JLabel label = new JLabel(icon);
				
				frame.add(label);
				
				frame.setSize(300, 100);
				frame.setVisible(true);
				
			}
		};
		
		EventQueue.invokeLater(runner);
		
	}	
}
