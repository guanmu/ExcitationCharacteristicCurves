/* Copyright MacroSAN Technologies Co., Ltd. All rights reserved. */
package learn.com.guanmu.math;

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
	}
}
