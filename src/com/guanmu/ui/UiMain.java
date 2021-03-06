/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package com.guanmu.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.slf4j.Logger;

import com.guanmu.model.ExFunction;
import com.guanmu.model.PointData;
import com.guanmu.model.PointValue;
import com.guanmu.thread.InfexionController;
import com.guanmu.utils.GridBagLayoutUtils;
import com.guanmu.utils.RootLogger;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:com.guanmu
 * 
 * @author wangquan 2017-4-28
 * 
 */
public class UiMain extends JFrame {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 755498983129736454L;
	private static final Logger logger = RootLogger.getLog(UiMain.class.getName());
	
	public static UiMain instance;
	
	/** 主界面*/
	private JPanel mainPanel;
	
	/** 数据面板*/
	private JPanel valuePanel;
	
	/** 逼近法曲线面板*/
	private TryCurvesPanel tryCurvesPanel;
	
	/** 结果面板*/
	private ResultPanel resultPanel;

	/**
	 * @throws HeadlessException
	 */
	public UiMain() throws HeadlessException {
		super();
		this.setTitle("电压互感器空载特性分析");
		this.setBounds(200, 200, 1130, 600);
		
		mainPanel = new JPanel();
		this.add(mainPanel);
		this.setVisible(true);
		mainPanel.setLayout(new GridBagLayout());
		
		createValuePanel();
		
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		GridBagLayoutUtils.addComponent(mainPanel,separator,1,0,1,1,
				0.05,0,
				GridBagConstraints.CENTER,GridBagConstraints.NONE);		
		
		createTryCurvesPanel();
		
		createResultPanel();
		
		addListener();
	}

	/**
	 * 
	 */
	private void addListener() {

	}



	private void createResultPanel() {
		resultPanel = new ResultPanel(this);
		resultPanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,resultPanel,1,1,3,1,
				0,0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);		
	}


	private void createTryCurvesPanel() {
		tryCurvesPanel = new TryCurvesPanel();
		tryCurvesPanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,tryCurvesPanel,2,0,1,1,
				1.0,1.0,
				GridBagConstraints.CENTER,GridBagConstraints.BOTH);
	}
	
	private void createValuePanel() {
		valuePanel = new ValuePanel(this);
		valuePanel.setVisible(true);
		
		GridBagLayoutUtils.addComponent(mainPanel,valuePanel,0,0,1,2,
				0,1.0,
				GridBagConstraints.WEST,GridBagConstraints.BOTH);
	}

	/**
	 * @param pointData 
	 * @param fitResults 
	 * @param function
	 */
	public void drawResults(PointData pointData, List<PointValue> fitResults,final ExFunction function) {
		
		tryCurvesPanel.drawCurves(pointData,fitResults,function);
		
		tryCurvesPanel.autoUpdateXYAixs();
		
		resultPanel.addInfo(pointData,function);
		
		try {
			new InfexionController(function).start();
		} catch (Exception e) {
			logger.error("ComputeController start exception.",e);
		}		

	}	
	
	public void initCaculate() {
		tryCurvesPanel.clearInfo();
		resultPanel.clearInfo();
	}
	
	public void drawInfexionPoint(double infexionX,double infexionY) {
		
		resultPanel.drawInfexionPoint(infexionX,infexionY);
		
		tryCurvesPanel.drawInfexionPoint(infexionX,infexionY);
	}	
	
	public static void main(String[] args) {
		
		initConfigs();
		
		Runnable runner = new Runnable() {
			
			@Override
			public void run() {
				UiMain uiMain = new UiMain();
				uiMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				uiMain.setVisible(true);
				
				instance = uiMain;
				
			}
		};
		
		EventQueue.invokeLater(runner);

	}


	private static void initConfigs() {
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
	}




}
