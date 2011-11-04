package com.fincher.bfim_applet;

class Criteria {
	
	public final int buyDays;
	public final float buyPercent;
	public final int sellDays;
	public final float sellPercent;

	public Criteria(int buyDays, float buyPercent, int sellDays, float sellPercent) {
		this.buyDays = buyDays;
		this.buyPercent = buyPercent;
		this.sellDays = sellDays;
		this.sellPercent = sellPercent;
	}

}
