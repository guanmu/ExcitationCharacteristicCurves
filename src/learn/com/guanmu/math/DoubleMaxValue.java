/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.math;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类描述:
 * <p>
 * 
 * 所属插件:learn.com.guanmu.math
 * @author wangquan 2017-5-23
 * 
 */
public class DoubleMaxValue {
	
	public static void main(String[] args) {
		
		double maxValue = Math.log(Double.MAX_VALUE);
		System.out.println("maxValue:" + maxValue);
		
		double rightValue = Math.exp(709);
		System.out.println("rightValue:" + rightValue);
		
		double infValue = Math.exp(709.783);
		System.out.println("infValue:" + infValue);
		
		List<Integer> test = new ArrayList<Integer>();
		test.add(1);
		test.add(12);
		test.add(3);
		test.add(16);
		test.add(10);
		test.add(31);
		test.add(161);
		test.add(101);		
		
		int m1 = 0;
		int m2 = 0;
		int m3 = 0;
		
		for(int i = 0;i < test.size();i++) {
			
			int value = test.get(i);
			
			if (m1 == 0) {
				m1 = value;
			} else {
				if (value > m1) {
					m2 = m1;
					
					m3 = m2;
					m1 = value;
					continue;
				}
			}
			
			if (m2 == 0) {
				m2 = value;
			} else {
				if (value > m2) {
					m3 = m2;
					
					m2 = value;
					continue;
				}
			}
			
			if (m3 == 0) {
				m3 = value;
			} else {
				if (value > m3) {
					m3 = value;
				}
			}
		}
		
		
		System.out.println("m1:" + m1);
		System.out.println("m2:" + m2);
		System.out.println("m3:" + m3);
	}
}
