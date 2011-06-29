package com.yoyar.gtd.internal;

public enum Priority {
	 
	AA_TOP(1, "Top", "FF0000"),
	BB_HIGH(2, "High", "F88017"),
	CC_MEDIUM(4, "Medium", "800080"),
	DD_LOW(8, "Low", "0000FF");
	
	private int intValue;
	private String label;
	private String hexColorCode;
	
	Priority(int intValue, String label, String hexColorCode) {
		this.intValue = intValue;
		this.label = label;
		this.hexColorCode = hexColorCode;
	}
	
	public int asInt() {
		return intValue;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getHexColorCode() {
		return hexColorCode;
	}
}
