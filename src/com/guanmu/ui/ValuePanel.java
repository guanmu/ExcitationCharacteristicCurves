/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.slf4j.Logger;

import com.guanmu.model.DoubleDocument;
import com.guanmu.model.PointTableModel;
import com.guanmu.model.PointValue;
import com.guanmu.thread.ComputeController;
import com.guanmu.utils.ExConfig;
import com.guanmu.utils.OptionPaneUtils;
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
	private JButton computeBtn;
	
	
	private PointTableModel tableModel;
	
	private JFrame parentFrame;
	
	private CurvesProgressMonitor monitor;
	
	
	/**
	 * 
	 */
	public ValuePanel(JFrame frame) {
		this.setLayout(new BorderLayout());
		
		parentFrame = frame;
		
		createParamPanel();
		
		createTablePanel();

		createButtonPanel();
		
		addListeners();
		
		if (ExConfig.DEBUG) {
//			debugData1();
//			debugData2();
			debugData3();
				
		}
		
	}

	/**
	 * 
	 */
	private void addListeners() {		
		
		addValueBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String xValueStr = xText.getText();
				if (xValueStr.isEmpty()) {
					OptionPaneUtils.openMessageDialog(parentFrame,"x不能为空。");
					return;
				}

				String yValueStr = yText.getText();
				if (yValueStr.isEmpty()) {
					OptionPaneUtils.openMessageDialog(parentFrame,"y不能为空。");
					return;
				}				
				
				double x = getXValue();
				if (x <= 0) {
					OptionPaneUtils.openMessageDialog(parentFrame,"x不能为负数。");
					return;
				}
				
				double y = getYValue();
				if (y <= 0) {
					OptionPaneUtils.openMessageDialog(parentFrame,"y不能为负数。");
					return;
				}
				
				if (tableModel.containsX(x)) {
					OptionPaneUtils.openMessageDialog(parentFrame,"表格中已存在该x对应的y值。");
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
					OptionPaneUtils.openMessageDialog(parentFrame,"请选择需要删除的数据。");
					return;
				}
				
				tableModel.deleteValue(rowIndex);
			}
		});
		
		computeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				final List<PointValue> pointValues = tableModel.getRowValues();
				if (pointValues.isEmpty()) {
					OptionPaneUtils.openMessageDialog(parentFrame,"请输入数据。");
					return;
				}
				
				double precision = 0;
				String precisionValueStr = precisionText.getText();
				
				if (!precisionValueStr.isEmpty()) {
					try {
						precision = Double.parseDouble(precisionValueStr);
					} catch (Exception e) {
						OptionPaneUtils.openMessageDialog(parentFrame,"精度输入不正确。");
						return;
					}					
				}

				monitor = new CurvesProgressMonitor(parentFrame, "进度","正在计算……", 0, 150);
				monitor.setProgress(0);
				monitor.setNote("test");
				
				final double finalPrecision = precision;
				new Thread() {
					
					@Override
					public void run() {
						try {
							new ComputeController(monitor,pointValues,finalPrecision).start();
						} catch (Exception e) {
							logger.error("ComputeController start exception.",e);
						}
					};
					
				}.start();
				

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
		
		computeBtn = new JButton("求解");
		buttonPanel.add(computeBtn);
		
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
		
		precisionText.setDocument(new DoubleDocument());
		
		rowPanel1.add(precisionText);
		
		JPanel rowPanel2 = new JPanel();
		rowPanel2.setLayout(new BoxLayout(rowPanel2, BoxLayout.X_AXIS));	
		
		JLabel xLabel = new JLabel("x:");
		rowPanel2.add(xLabel);
		xText = new JTextField();
		xText.setColumns(INPUT_TEXT_LENGTH);
		xText.setDocument(new DoubleDocument());
		rowPanel2.add(xText);
		
		JPanel rowPanel3 = new JPanel();
		rowPanel3.setLayout(new BoxLayout(rowPanel3, BoxLayout.X_AXIS));	
		
		JLabel yLabel = new JLabel("y:");
		rowPanel3.add(yLabel);
		yText = new JTextField();
		yText.setColumns(INPUT_TEXT_LENGTH);
		yText.setDocument(new DoubleDocument());
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
	
	private void debugData1() {
		tableModel.addNewValue(11.54,31.5);
		tableModel.addNewValue(28.86,68.5);
		tableModel.addNewValue(46.2,139.2);
		tableModel.addNewValue(57.7,206);
		tableModel.addNewValue(69.3,280);
		tableModel.addNewValue(86.6,450);
		tableModel.addNewValue(103.9,700);
		tableModel.addNewValue(109.7,813);
		tableModel.addNewValue(115.5,935);
		tableModel.addNewValue(121.2,1069);
		tableModel.addNewValue(131.6,1590);
		tableModel.addNewValue(138.5,2220);
		tableModel.addNewValue(144.3,4150);
		tableModel.addNewValue(150,7020);
	}
	
	private void debugData2() {
		tableModel.addNewValue(11.54,32.5);
		tableModel.addNewValue(28.86,65.5);
		tableModel.addNewValue(46.2,121);
		tableModel.addNewValue(57.7,162);
		tableModel.addNewValue(69.3,203);
		tableModel.addNewValue(86.6,274);
		tableModel.addNewValue(103.9,350);
		tableModel.addNewValue(109.7,420);
		tableModel.addNewValue(115.5,495);
		tableModel.addNewValue(121.2,602);
		tableModel.addNewValue(131.6,830);
		tableModel.addNewValue(138.5,1370);
		tableModel.addNewValue(144.3,2780);
		tableModel.addNewValue(150,6350);
	}
	
	private void debugData3() {
		tableModel.addNewValue(11.54, 129.5);
		tableModel.addNewValue(28.86, 258.4);
		tableModel.addNewValue(46.2, 441);
		tableModel.addNewValue(57.7, 656);
		tableModel.addNewValue(69.3, 1042);
		tableModel.addNewValue(86.6, 2400);
		tableModel.addNewValue(103.9, 5150);
		tableModel.addNewValue(109.7, 6500);
		tableModel.addNewValue(115.5, 8100);
	}	
	
	
}
