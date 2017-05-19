/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.ui.swing;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
public class PointTableModelMap extends AbstractTableModel implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4896623903869082985L;
	
	TableModel model;
	

	public final static String[] TABLE_COLUMNS = {"x(mA)","y(V)"}; 
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	}
	
	public TableModel getModel() {
		return model;
	}
	
	public void setModel(TableModel model) {
		if (this.model != null) {
			this.model.removeTableModelListener(this);
		}
		
		this.model = model;
		
		if (this.model != null) {
			this.model.addTableModelListener(this);
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return model.getColumnClass(columnIndex);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (model == null) {
			return 0;
		}
		
		return model.getRowCount();
	}

	@Override
	public int getColumnCount() {
		if (model == null) {
			return 0;
		}
		
		return model.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return model.getValueAt(rowIndex, columnIndex);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return model.getColumnName(column);
	}	
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		model.setValueAt(aValue, rowIndex, columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return model.isCellEditable(rowIndex, columnIndex);
	}
	
}
