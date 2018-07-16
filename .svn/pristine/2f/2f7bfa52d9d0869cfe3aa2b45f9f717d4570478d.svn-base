package com.itecheasy.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtils {

	/**
	 * 获取产品售价，保留2位小数
	 * 
	 * @param costPrice
	 *            基准成本价
	 * @param unitQuantity
	 *            批量数量
	 * @param profitCoefficient
	 *            利润系数
	 * @param exchangeRate
	 *            汇率
	 * @return 产品售价
	 */
	public static BigDecimal getProductSalePrice2(BigDecimal costPrice,
			Integer unitQuantity, BigDecimal profitCoefficient,
			BigDecimal exchangeRate) {
		
		return getProductSalePrice4(costPrice, unitQuantity, profitCoefficient, exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取产品售价,保留4位小数
	 * 
	 * @param costPrice
	 *            基准成本价
	 * @param unitQuantity
	 *            批量数量
	 * @param profitCoefficient
	 *            利润系数
	 * @param exchangeRate
	 *            汇率
	 * @return 产品售价
	 */
	public static BigDecimal getProductSalePrice4(BigDecimal costPrice,
			Integer unitQuantity, BigDecimal profitCoefficient,
			BigDecimal exchangeRate) {
		if(profitCoefficient == null || BigDecimal.ZERO.compareTo(profitCoefficient) == 0){
			return BigDecimal.ZERO;
		}
		if(BigDecimal.ZERO.compareTo(exchangeRate) == 0){
			return BigDecimal.ZERO;
		}
		
		// 批量售价=(基准成本价×批量数量) ÷ 汇率 × 利润系数
		BigDecimal salePrice = costPrice.multiply(new BigDecimal(unitQuantity))
				.divide(exchangeRate, 4, BigDecimal.ROUND_HALF_UP).multiply(
						profitCoefficient);
		return salePrice.setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获取利润系数
	 * 
	 * @param salePrice
	 *            售价
	 * @param costPrice
	 *            基准成本价
	 * @param unitQuantity
	 *            批量数量
	 * @param exchangeRate
	 *            汇率
	 * @return 返回利润系数
	 */
	public static BigDecimal getProfitCoefficient(BigDecimal salePrice,
			BigDecimal costPrice, Integer unitQuantity, BigDecimal exchangeRate) {
		if(exchangeRate==null || BigDecimal.ZERO.compareTo(exchangeRate) == 0){
			return BigDecimal.ZERO;
		}
		if(costPrice==null || BigDecimal.ZERO.compareTo(costPrice) == 0){
			return BigDecimal.ZERO;
		}
		if(salePrice==null || BigDecimal.ZERO.compareTo(salePrice) == 0){
			return BigDecimal.ZERO;
		}
		if(unitQuantity==null || unitQuantity == 0){
			return BigDecimal.ZERO;
		}
		BigDecimal temp = costPrice.multiply(new BigDecimal(unitQuantity))
				.divide(exchangeRate, 6, BigDecimal.ROUND_HALF_UP);
		return salePrice.divide(temp, 4, BigDecimal.ROUND_HALF_UP);
	}
	
	public  static String convertBigDecimal(BigDecimal value, String format ){
		DecimalFormat nf = new DecimalFormat(format);   
		return nf.format(value);  
	}

	public static void main(String[] args) {
		BigDecimal costPrice = new BigDecimal("10.25");
		Integer unitQuantity = 10;
		BigDecimal profitCoefficient = new BigDecimal("1.2525");
		BigDecimal exchangeRate = new BigDecimal("8.2");
		BigDecimal salePrice= getProductSalePrice4(costPrice, unitQuantity, profitCoefficient, exchangeRate);
		
		System.out.println(salePrice);
		
		System.out.println(getProfitCoefficient(salePrice, costPrice, unitQuantity, exchangeRate));
	}
	
}
