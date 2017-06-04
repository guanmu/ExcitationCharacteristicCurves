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
import com.guanmu.model.PointData;
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
	
	private JTable dataTable;
	
	private JPanel buttonPanel;
	private JLabel valueNumberLabel;
	private JLabel valueNumberInfo;
	private JButton resetBtn;
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
		
		initValues();

		
	}

	private void initValues() {
		
		List<PointValue> initValues = ExConfig.getInitTableValues();
		
		for(PointValue point : initValues) {
			tableModel.addNewValue(point.getX(),point.getY());
		}

		precisionText.setText("" + ExConfig.DEFUALT_PRECESION);
	}

	/**
	 * 
	 */
	private void addListeners() {		
		
		tableModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int pointNumber = tableModel.getRowCount();
				valueNumberInfo.setText("" + pointNumber);
			}
		});
		
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				initValues();
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
				
				double precision = 0.999;
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
				monitor.setNote("请等待");
				
				final PointData pointData = new PointData(pointValues);
				final double finalPrecision = precision;
				new Thread() {
					
					@Override
					public void run() {
						try {
							new ComputeController(monitor,pointData,finalPrecision).start();
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
		
		buttonPanel.add(new JLabel("    "));
		
		valueNumberLabel = new JLabel("当前数据个数：");
		buttonPanel.add(valueNumberLabel);
		
		valueNumberInfo = new JLabel("0");
		buttonPanel.add(valueNumberInfo);
		
		resetBtn = new JButton("重置");
		buttonPanel.add(resetBtn);
		
		computeBtn = new JButton("求解");
		buttonPanel.add(computeBtn);
		
		buttonPanel.add(new JLabel("    "));
		
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
		
		
		paramPanel.add(rowPanel1);
		
		this.add(paramPanel,BorderLayout.NORTH);
	}


	
	
}
