package com.fincher.bfim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class SandPEntry {
	
	public static class DateComparator implements Comparator<SandPEntry> {
		public int compare(SandPEntry e1, SandPEntry e2) {			
			return e1.date.compareTo(e2.date) * -1;
		}
	}		
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd-yyyy");
	
	private static final String END_MONTH_TAG = "!!END_MONTH!!";
	private static final String END_DAY_TAG = "!!END_DAY!!";
	private static final String END_YEAR_TAG = "!!END_YEAR!!";

	private static final String BEGIN_MONTH_TAG = "!!BEGIN_MONTH!!";
	private static final String BEGIN_DAY_TAG = "!!BEGIN_DAY!!";
	private static final String BEGIN_YEAR_TAG = "!!BEGIN_YEAR!!";
	
	private static final String URL_STRING = "http://ichart.finance.yahoo.com/table.csv?s=%5EGSPC&a=" 
			+ BEGIN_MONTH_TAG + "&b=" 
			+ BEGIN_DAY_TAG + "&c=" 
			+ BEGIN_YEAR_TAG + "&d=" 
			+ END_MONTH_TAG + "&e=" 
			+ END_DAY_TAG + "&f=" 
			+ END_YEAR_TAG 
			+ "&g=d&ignore=.csv";
	
	private final float price;		

	private final Date date;
	private String fourDays;
	private String eighteenDays;
	private String buySell;
	
//	public SandPEntry(float price, Date date, float fourDays, float eighteenDays, String buySell) {
//		this.price = price;
//		this.date = date;
//		this.fourDays = fourDays;
//		this.eighteenDays = eighteenDays;
//		this.buySell = buySell;
//	}
	
	public SandPEntry(float price, Date date) {
		this.price = price;
		this.date = date;		
	}
	
//	public SandPEntry(String str) throws ParseException {
//		StringTokenizer st = new StringTokenizer(str, ",");
//		date = SDF.parse(st.nextToken());
//		price = new Float(st.nextToken());		
//	}
	
	public String toString() {
		return String.valueOf(SDF.format(date) + "," + price);
	}		
	
	public String getFourDays() {
		return fourDays;
	}

	public void setFourDays(String fourDays) {
		this.fourDays = fourDays;
	}

	public String getEighteenDays() {
		return eighteenDays;
	}

	public void setEighteenDays(String eighteenDays) {
		this.eighteenDays = eighteenDays;
	}

	public String getBuySell() {
		return buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}

	public float getPrice() {
		return price;
	}

	public String getDate() {
		return SDF.format(date);
	}
	
	public static List<SandPEntry> getFromWeb(Date beginDate, Date endDate) 
	throws IOException {
		System.setProperty("http.proxyHost","proxy.mdnt.com");
		System.setProperty("http.proxyPort", "9119");
//		System.setProperty("http.proxyUser", "s149450");
//		System.setProperty("http.proxyPassword", "B@i4emmB@i4emm");
		
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(beginDate);
		
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(endDate);
		
		String tempUrl = URL_STRING.replace("%END_MONTH%", String.valueOf(endCal.get(GregorianCalendar.MONTH)));
		tempUrl = tempUrl.replace(END_MONTH_TAG, String.valueOf(endCal.get(GregorianCalendar.MONTH)));
		tempUrl = tempUrl.replace(END_DAY_TAG, String.valueOf(endCal.get(GregorianCalendar.DATE)));
		tempUrl = tempUrl.replace(END_YEAR_TAG, String.valueOf(endCal.get(GregorianCalendar.YEAR)));
	
		tempUrl = tempUrl.replace(BEGIN_MONTH_TAG, String.valueOf(beginCal.get(GregorianCalendar.MONTH)));
		tempUrl = tempUrl.replace(BEGIN_DAY_TAG, String.valueOf(beginCal.get(GregorianCalendar.DATE)));
		tempUrl = tempUrl.replace(BEGIN_YEAR_TAG, String.valueOf(beginCal.get(GregorianCalendar.YEAR)));
		System.out.println("URL = " + tempUrl);
	
		URL url = new URL(tempUrl);
	
		BufferedReader urlInput = new BufferedReader(new InputStreamReader(url.openStream()));
		urlInput.readLine(); // skip header line

		ArrayList<SandPEntry>entries = new ArrayList<SandPEntry>();
		
		final SimpleDateFormat webSdf = new SimpleDateFormat("yyyy-MM-dd");
	
		String csv;
		while ((csv = urlInput.readLine()) != null) {
			try {
				StringTokenizer tokenizer = new StringTokenizer(csv, ",\"");
				Date date = webSdf.parse(tokenizer.nextToken());
				for (int i = 0; i < 5; i++) {
					tokenizer.nextToken();
				}
			
				SandPEntry newEntry = new SandPEntry(new Float(tokenizer.nextToken()), date);
				System.out.println("Adding entry " +  newEntry);
				entries.add(newEntry);
			} catch (ParseException pe) {
				throw new IOException(pe);
			}
		}
		
		if (!entries.isEmpty()) {
			Collections.sort(entries, new SandPEntry.DateComparator());
		}
		
		return entries;
	}
}
