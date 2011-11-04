package com.fincher.bfim.jsp;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fincher.bfim.SandPEntry;

public class BfimBean {

	private List<SandPEntry> entries;
	private boolean processError = false;
	private String beginDateStr;
	private String endDateStr;
	private Date beginDate;
	private Date endDate;	       

	private static final int BUY_DAYS = 4;
	private static final int SELL_DAYS = 18;
	private static final float BUY_PERCENT = 0.035f;
	private static final float SELL_PERCENT = -0.075f;;

//	private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();	

	public Entries getEntries() {
		return new Entries(entries);
	}

	public boolean getProcessError() {
		return processError;
	}

	public void setBeginDate(String beginDateStr) throws ParseException {
		this.beginDateStr = beginDateStr;
		beginDate = SandPEntry.SDF.parse(beginDateStr);
	}

	public void setEndDate(String endDateStr) throws ParseException {
		this.endDateStr = endDateStr;
		endDate = SandPEntry.SDF.parse(endDateStr);
	}

	public void processRequest(HttpServletRequest request) throws ParseException, IOException {
		if (beginDateStr == null || beginDateStr.equals("")) {
			setBeginDate(request.getParameter("beginDate"));
		}

		if (endDateStr == null || endDateStr.equals("")) {
			setEndDate(request.getParameter("endDate"));
		}
		
		getDataFromWeb();
	}

	private void getDataFromWeb() throws IOException {
		
		entries = SandPEntry.getFromWeb(beginDate, endDate);				
		
		for (int listIdx = 0; listIdx < entries.size(); listIdx++) {
			SandPEntry entry = entries.get(listIdx);				
			
			float buyPercent = getCompValue(BUY_DAYS, listIdx, entries);
			float sellPercent = getCompValue(SELL_DAYS, listIdx, entries);
			
			entry.setFourDays(formatPercentString(buyPercent));
			entry.setEighteenDays(formatPercentString(sellPercent));			
			
			if (sellPercent <= SELL_PERCENT) {
				entry.setBuySell("Sell");
			} else if (buyPercent >= BUY_PERCENT) {
				entry.setBuySell("Buy");
			} else {
				entry.setBuySell("");
			}						
		}
	}
	
	private static float getCompValue(int numDays, int idx, List<SandPEntry> entries) {
		int compIdx = idx + numDays;
		if (compIdx < entries.size()) {
			float thisPrice = entries.get(idx).getPrice();
			float compPrice = entries.get(compIdx).getPrice();
			float delta = (thisPrice - compPrice) / compPrice;
			return delta;
		} else {
			return Float.MAX_VALUE;
		}
	}
    
    private static String formatPercentString(float percent) {
		if (percent == Float.MAX_VALUE) {
			return "N/A";
		} else {
			return PERCENT_FORMAT.format(percent);
		}
	}

}
