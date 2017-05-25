/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.model
 * @author wangquan 2017-5-16
 * 
 */
public class PointTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4896623903869082985L;
	
	TableModel model;
	

	public final static String[] TABLE_COLUMNS = {"x(V)","y(mA)"}; 
	
	
	private List<PointValue> rowValues = new ArrayList<PointValue>();	
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return rowValues.size();
	}

	@Override
	public int getColumnCount() {
		return TABLE_COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rowValues.get(rowIndex).getColumnValue(columnIndex);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return TABLE_COLUMNS[column];
	}

	/**
	 * @param x
	 * @param y
	 */
	public void addNewValue(double x, double y) {
		rowValues.add(new PointValue(x, y));
		fireTableDataChanged();
	}

	/**
	 * @param x
	 * @return
	 */
	public boolean containsX(double x) {
		
		for(PointValue pv : rowValues) {
			if (pv.getX() == x) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @param rowIndex
	 */
	public void deleteValue(int rowIndex) {
		if (rowIndex >= rowValues.size()) {
			return;
		}
		
		rowValues.remove(rowIndex);
		fireTableDataChanged();
	}

	
	public List<PointValue> getRowValues() {
		return new ArrayList<PointValue>(rowValues);
	}
}
