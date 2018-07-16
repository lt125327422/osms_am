package com.itecheasy.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String toMD5(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			return byte2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e); // never happens
		}
	}

	/**
	 * 把字节数组转换成可读形式的16进制表达式字符串, 字节之间以字符串:隔开,
	 * 转换后的字符串类似 "5B:D6:0E:D5:83:92:A2:D5"
	 */
	public static String byte2Hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			if (i != b.length -1)
				sb.append(":");
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * convert from string in format "5B:D6:0E:D5:83:92:A2:D5" to byte array
	 */
	public static byte[] hex2byte(String s) {
		int count = 0; // the count of bytes in string
		int p = 0;
		while (1 > 0) {
			int pos = s.indexOf(":", p);
			if (pos == -1) {
				count++;
				break; // nothing left
			} else {
				count++;
				p = pos + 1; // the next search goes from the current pos + 1
			}
		}

		byte[] b = new byte[count]; // construct the array according to the
									// number of bytes
		int i = 0;
		while (s.length() > 0) {
			int pos = s.indexOf(":");
			if (pos == -1) {
				String stmp = s;
				b[i] = (byte) Integer.parseInt(stmp, 16);
				s = "";
			} else {
				String stmp = s.substring(0, pos);
				b[i] = (byte) Integer.parseInt(stmp, 16);
				i++;
				s = s.substring(pos + 1);
			}
		}
		return b;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.toMD5("123456"));
	}
}
