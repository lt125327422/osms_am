package com.itecheasy.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateCode {
	/**
	 * 根据ID,生成一个指定长度的编码
	 * 
	 * @param id
	 * @param 生成的编码长度
	 * @return 返回大写的编码
	 */
	public static String generate(Integer id, Integer length) {
		//int i = id;
		String result = "";
		if (length <= 10) {
			String[] charDir = "1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,M,N,P,Q,R,S,T,W,X,Y,Z".split(",");
			while (id != 0) {
				result = charDir[id % 30] + result;
				id = id / 30;
			}

			if (result.length() > length) {
				result = "";
			} else if (result.length() < length) {
				result = "0000000000".substring(0, length - result.length())
						+ result;
			}
		}
		//System.out.println("UPDATE dbo.product_set SET code='"+result+"' WHERE id="+i);
		// Put your code here
		return result;
	}
	
	/**
	 * 生成编码  ，返回12位长度的编码
	 * @param startChar 以什么开头
	 * @return 返回12位长度的编码
	 */
	public static String generate(String startChar)
	{
		Date dt = new Date();
		String code = startChar;
		SimpleDateFormat sf=new SimpleDateFormat("yyMMdd");
		code += sf.format(dt);
		String ticks = String.valueOf(dt.getTime());
		code += ticks.substring(ticks.length() - 5, ticks.length());
		
		return code;
	}
	
	public static void main(String[] args) {
		generate(132504,6);
		generate(132505,6);
		generate(132506,6);
		generate(132509,6);
		generate(132510,6);
		
	}
}
