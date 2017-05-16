/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.model;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
public class PointSorterTableModel extends PointTableModelMap implements TableModelListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -740495043901437947L;
	int[] indexes = new int[0];
	Vector<Integer> sortingColumns = new Vector<Integer>();
	boolean ascending = true;
	/**
	 * 
	 */
	public PointSorterTableModel() {
		
	}
	
	public PointSorterTableModel(TableModel model) {
		setModel(model);
	}
	
	@Override
	public void setModel(TableModel model) {
		super.setModel(model);
		reallocateIndexes();
		sortByColumn(0);
		fireTableDataChanged();
	}
	
	public int compareRowsByColumn(int row1,int row2,int column) {
		Class<?> type = model.getColumnClass(column);
		TableModel data = model;
		
		Object o1 = data.getValueAt(row1, column);
		Object o2 = data.getValueAt(row2, column);
		
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) {
			return -1;
		} else if (o2 == null) {
			return 1;
		}
		
		if (type.getSuperclass() == Number.class) {
			Number n1 = (Number) data.getValueAt(row1,column);
			double d1 = n1.doubleValue();
			Number n2 = (Number) data.getValueAt(row2, column);
			double d2 = n2.doubleValue();
			
			if (d1 < d2) {
				return -1;
			} else if (d1 < d2) {
				return 1;
			} else {
				return 0;
			}
		} else if (type == String.class) {
			String s1 = (String) data.getValueAt(row1, column);
			String s2 = (String) data.getValueAt(row2, column);
			int result = s1.compareTo(s2);
			
			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		}
		
		return 0;
	}
	
	/**
	 * 
	 */
	public int compare(int row1,int row2) {
		for (int level = 0,n = sortingColumns.size();level < n;level++) {
			Integer column = (Integer) sortingColumns.elementAt(level);
			int result = compareRowsByColumn(row1, row2, column);
			if (result != 0) {
				return (ascending ? result : -result);
			}
		}
		
		return 0;
	}

	/**
	 * 
	 */
	private void reallocateIndexes() {
		int rowCount = model.getRowCount();
		indexes = new int[rowCount];
		for(int row = 0;row < rowCount;row++) {
			indexes[row] = row;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.guanmu.model.PointTableModelMap#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		reallocateIndexes();
		sortByColumn(0);
		fireTableStructureChanged();
	}
	
	public void checkModel() {
		if (indexes.length != model.getRowCount()) {
			System.out.println("Sorter not informed of a change in model.");
		}
	}
	
	public void sort() {
		checkModel();
		shuttlesort((int[])indexes.clone(),indexes,0,indexes.length);
		fireTableDataChanged();
	}
	
	public void shuttlesort(int from[],int to[],int low,int high) {
		if (high - low < 2) {
			return;
		}
		int middle = (low + high) / 2;
		shuttlesort(to, from, low, middle);
		shuttlesort(to, from, middle, high);
		
		int p = low;
		int q = middle;
		
		for(int i = low;i < high;i++) {
			if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
		
	}
	
	protected void swap(int first,int second) {
		int tmp = indexes[first];
		indexes[first] = indexes[second];
		indexes[second] = tmp;
	}
	
	/* (non-Javadoc)
	 * @see com.guanmu.model.PointTableModelMap#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		checkModel();
		return model.getValueAt(rowIndex, columnIndex);
	}
	
	/* (non-Javadoc)
	 * @see com.guanmu.model.PointTableModelMap#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		checkModel();
		
		model.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	public void sortByColumn(int column) {
		sortByColumn(column,true);
	}

	/**
	 * @param column
	 * @param b
	 */
	public void sortByColumn(int column, boolean ascending) {
		this.ascending = ascending;
		sortingColumns.removeAllElements();
		sortingColumns.add(new Integer(column));
		sort();
		super.tableChanged(new TableModelEvent(this));
	}
	
	
}
