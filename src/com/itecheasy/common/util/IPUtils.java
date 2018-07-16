package com.itecheasy.common.util;

public class IPUtils {
	/**
	 * 根据ip地址计算出long型的数据
	 */
	public static long convertIP2Long(String strIP) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	/**
	 * 将10进制整数形式转换成127.0.0.1形式的IP地址
	 */
	public static String convertLong2IP(long longIP) {
		StringBuffer sb = new StringBuffer("");
		// 直接右移24位
		sb.append(String.valueOf(longIP >>> 24));
		sb.append(".");
		// 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		return sb.toString();
	}
	
	/**
	 * 检查指定IP地址是否符合指定的IP地址段，如检查192.168.0.56，是否符合地址段(192.168.0.1, 192.168.0.200)，应该返回true
	 * @param startIP
	 * @param endIP
	 * @return
	 */
	public static boolean isIPInScope(String ip, String startIP, String endIP) {
		long l = convertIP2Long(ip);
		return  l >= convertIP2Long(startIP) && l <= convertIP2Long(endIP);
	}
}
