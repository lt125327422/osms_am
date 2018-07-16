package com.itecheasy.common.util;

import java.util.Arrays;
import java.util.Comparator;

public class NumberComparator implements Comparator<String> {
    private boolean ignoreCase = true;

    public NumberComparator() {
    }

    public NumberComparator(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public int compare(String o1, String o2) {
        if (ignoreCase) {
            o1 = o1.toLowerCase();
            o2 = o2.toLowerCase();
        }
        for (int i = 0; i < o1.length(); i++) {
            if (i == o1.length() && i < o2.length()) {
                return -1;
            } else if (i == o2.length() && i < o1.length()) {
                return 1;
            }
            char ch1 = o1.charAt(i);
            char ch2 = o2.charAt(i);
            if (ch1 >= '0' && ch2 <= '9') {
            	int i1 = getNumber(o1.substring(i));
            	int i2 = getNumber(o2.substring(i));
                if (i1 == i2) {
                    continue;
                } else {
                    return i1 - i2;
                }
            } else if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }
        return 0;
    }

    private int getNumber(String str) {
    	int num = Integer.MAX_VALUE;
        int bits = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                bits++;
            } else {
                break;
            }
        }
        if (bits > 0) {
        	try {
        		num = Integer.parseInt(str.substring(0, bits));
			} catch (NumberFormatException e) {
				num = Integer.parseInt(str.substring(0, 9));
			}
        }
        return num;
    }
    
    public static void main(String[] args) {
    	String [] ages= new String[]{"10" , "12", "1"};
		Arrays.sort(ages, new NumberComparator(true)); 
		for (int i = 0; i < ages.length; i++) {
			System.out.println(ages[i]+" ");
		}
	}
}
