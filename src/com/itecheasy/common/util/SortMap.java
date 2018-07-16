package com.itecheasy.common.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SortMap<T1, T2> {
	@SuppressWarnings("unchecked")
	public List<T1> sortMapByValue(Map<T1, T2> map, final Comparator<T2> c) {
		Set<Map.Entry<T1, T2>> set = map.entrySet();
		Map.Entry<T1, T2>[] entries = (Map.Entry<T1, T2>[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator<Map.Entry<T1, T2>>() {
			public int compare(Map.Entry<T1, T2> e1, Map.Entry<T1, T2> e2) {
				T2 value1 = (T2)e1.getValue();
				T2 value2 = (T2)e2.getValue();
				return c.compare(value1, value2);
			}
		});
		List<T1> ret = new LinkedList<T1>();
		for(Map.Entry<T1, T2> e : entries) {
			ret.add(e.getKey());
		}
		return ret;
	}
}
