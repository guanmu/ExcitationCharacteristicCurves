package com.guanmu.ui;

import java.awt.Component;

import javax.swing.ProgressMonitor;

public class CurvesProgressMonitor extends ProgressMonitor {
	
	private Object processLock = new Object();
	
	private int process = 0;
	
	public CurvesProgressMonitor(Component parentComponent, Object message,
			String note, int min, int max) {
		super(parentComponent, message, note, min, max);
	}
	
	@Override
	public void setProgress(int nv) {
		synchronized (processLock) {
			super.setProgress(nv);
			process = nv;			
		}
	}
	
	public int getProgress() {
		return process;
	}

	public void addProgress(int addProgress) {
		synchronized (processLock) {
			int now = process;
			
			int value = now + addProgress;
			super.setProgress(value);
			process = value;	
			
		}
		
	}
}
