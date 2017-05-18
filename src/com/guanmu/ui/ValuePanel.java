/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.slf4j.Logger;

import com.guanmu.model.PointTableModel;
import com.guanmu.utils.RootLogger;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu.ui
 * @author wangquan 2017-5-15
 * 
 */
public class ValuePanel extends JPanel {

	private static final long serialVersionUID = -6995856246269794959L;
	
	private static Logger logger = RootLogger.getLog(ValuePanel.class.getName());
	
	public static int INPUT_TEXT_LENGTH = 40;
	
	private JPanel paramPanel;
	
	private JTextField precisionText;
	private JTextField xText;
	private JTextField yText;
	
	private JTable dataTable;
	
	private JPanel buttonPanel;
	private JLabel valueNumberLabel;
	private JLabel valueNumberInfo;
	private JButton addValueBtn;
	private JButton deleteValueBtn;
	
	private PointTableModel tableModel;
	
	/**
	 * 
	 */
	public ValuePanel() {
		this.setLayout(new BorderLayout());
		
		createParamPanel();
		
		createTablePanel();

		createButtonPanel();
		
		addListeners();
		
	}

	/**
	 * 
	 */
	private void addListeners() {
		addValueBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double x = getXValue();
				if (x <= 0) {
					// TODO 
					return;
				}
				
				double y = getYValue();
				if (y <= 0) {
					// TODO 
					
					System.out.println("y < 0");
					return;
				}
				
				if (tableModel.containsX(x)) {
					// TODO
					
					return;
				}
				
				tableModel.addNewValue(x,y);
				
				xText.setText("");
				yText.setText("");
			}
		});
		
		tableModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int pointNumber = tableModel.getRowCount();
				valueNumberInfo.setText("" + pointNumber);
			}
		});
		
		deleteValueBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = dataTable.getSelectedRow();
				if (rowIndex < 0) {
					// TODO
					
					return;
				}
				
				tableModel.deleteValue(rowIndex);
			}
		});
		
		
	}

	/**
	 * 
	 */
	private void createButtonPanel() {
		buttonPanel = new JPanel();
		
		valueNumberLabel = new JLabel("当前数据个数：");
		buttonPanel.add(valueNumberLabel);
		
		valueNumberInfo = new JLabel("0");
		buttonPanel.add(valueNumberInfo);
		
		addValueBtn = new JButton("添加数据");
		buttonPanel.add(addValueBtn);
		
		deleteValueBtn = new JButton("删除数据");
		buttonPanel.add(deleteValueBtn);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
	}

	/**
	 * 
	 */
	private void createTablePanel() {

		tableModel = new PointTableModel();
		dataTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		
		this.add(scrollPane,BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	private void createParamPanel() {
		
		paramPanel = new JPanel();
		
		BoxLayout yaxisLayout = new BoxLayout(paramPanel, BoxLayout.Y_AXIS);
		paramPanel.setLayout(yaxisLayout);
		
		JPanel rowPanel1 = new JPanel();
		rowPanel1.setLayout(new BoxLayout(rowPanel1, BoxLayout.X_AXIS));
		
		JLabel precisionLabel = new JLabel("精度：");
		rowPanel1.add(precisionLabel);
		precisionText = new JTextField();
		precisionText.setColumns(INPUT_TEXT_LENGTH);
		rowPanel1.add(precisionText);
		
		JPanel rowPanel2 = new JPanel();
		rowPanel2.setLayout(new BoxLayout(rowPanel2, BoxLayout.X_AXIS));	
		
		JLabel xLabel = new JLabel("x:");
		rowPanel2.add(xLabel);
		xText = new JTextField();
		xText.setColumns(INPUT_TEXT_LENGTH);
		rowPanel2.add(xText);
		
		JPanel rowPanel3 = new JPanel();
		rowPanel3.setLayout(new BoxLayout(rowPanel3, BoxLayout.X_AXIS));	
		
		JLabel yLabel = new JLabel("y:");
		rowPanel3.add(yLabel);
		yText = new JTextField();
		yText.setColumns(INPUT_TEXT_LENGTH);
		rowPanel3.add(yText);
		
		paramPanel.add(rowPanel1);
		paramPanel.add(rowPanel2);
		paramPanel.add(rowPanel3);
		
		this.add(paramPanel,BorderLayout.NORTH);
	}
	
	public double getXValue() {
		String valueStr = xText.getText();
		
		try {
			double value = Double.parseDouble(valueStr);	
			return value;
		} catch(Exception e) {
			logger.error("parseDouble exception.",e);
			return -1;
		}

	}
	
	public double getYValue() {
		String valueStr = yText.getText();
		
		try {
			double value = Double.parseDouble(valueStr);	
			return value;
		} catch(Exception e) {
			logger.error("parseDouble exception.",e);
			return -1;
		}

	}	
}
