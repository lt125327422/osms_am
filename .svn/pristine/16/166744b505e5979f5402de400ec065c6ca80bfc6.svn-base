package com.itecheasy.common.util;

public class SqlUtils {
	public enum MatchPattern {
		EQ("="), GT(">"), LT("<"), GE(">="), LE("<="), NE("<>");
		private String match;

		private MatchPattern(String match) {
			this.match = match;
		}

		public String getValue() {
			return match;
		}
	}
	
	public static String getMatchPattern(String key)
	{
		String match = "=";
		if(MatchPattern.EQ.toString().equalsIgnoreCase(key)){
			match = MatchPattern.EQ.getValue();
		}else if(MatchPattern.GT.toString().equalsIgnoreCase(key)){
			match = MatchPattern.GT.getValue();
		}else if(MatchPattern.LT.toString().equalsIgnoreCase(key)){
			match = MatchPattern.LT.getValue();
		}else if(MatchPattern.GE.toString().equalsIgnoreCase(key)){
			match = MatchPattern.GE.getValue();
		}else if(MatchPattern.LE.toString().equalsIgnoreCase(key)){
			match = MatchPattern.LE.getValue();
		}else if(MatchPattern.NE.toString().equalsIgnoreCase(key)){
			match = MatchPattern.NE.getValue();
		}
		return match;
	}
}
