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
	
	private static final String HISTORICAL_URL_STRING = "http://ichart.finance.yahoo.com/table.csv?s=%5EGSPC&a=" 
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
		System.setProperty("http.proxyHost","centralproxy.northgrum.com");
		System.setProperty("http.proxyPort", "80");
		System.setProperty("http.proxyUser", "s149450");
		System.setProperty("http.proxyPassword", "B@i4emmB@i4emm");
		
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(beginDate);
		
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(endDate);
		
		String tempUrl = HISTORICAL_URL_STRING.replace("%END_MONTH%", String.valueOf(endCal.get(GregorianCalendar.MONTH)));
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
		try {
			while ((csv = urlInput.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(csv, ",\"");
				Date date = webSdf.parse(tokenizer.nextToken());
				for (int i = 0; i < 5; i++) {
					tokenizer.nextToken();
				}
			
				SandPEntry newEntry = new SandPEntry(new Float(tokenizer.nextToken()), date);
				entries.add(newEntry);
			}
		
			if (!entries.isEmpty()) {
				Collections.sort(entries, new SandPEntry.DateComparator());
			}
		
//			getCurrentPrice(entries, endCal);
		
		} catch (ParseException pe) {
			throw new IOException(pe);
		}
		
		return entries;
	}
	
//	private static void getCurrentPrice(List<SandPEntry> entries, GregorianCalendar endCal) throws IOException, ParseException  {
//		endCal.set(GregorianCalendar.HOUR_OF_DAY, 0);
//		endCal.set(GregorianCalendar.MINUTE, 0);
//		endCal.set(GregorianCalendar.SECOND, 0);
//		endCal.set(GregorianCalendar.MILLISECOND, 0);
//		
//		GregorianCalendar today = new GregorianCalendar();
//		today.set(GregorianCalendar.HOUR_OF_DAY, 0);
//		today.set(GregorianCalendar.MINUTE, 0);
//		today.set(GregorianCalendar.SECOND, 0);
//		today.set(GregorianCalendar.MILLISECOND, 0);
//		if (endCal.compareTo(today) >= 0) {					
//			// get the latest price
//			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?s=^GSPC&f=sl1d1t1c1ohgv&e=.csv");
//			
//			BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
//			String csv = input.readLine();
//			System.out.println("CSV = " + csv);
//			StringTokenizer tokenizer = new StringTokenizer(csv, ",\"");
//			
//			tokenizer.nextToken(); // skip ticker symbol
//			float price = new Float(tokenizer.nextToken());
//			
//			final SimpleDateFormat webSdf = new SimpleDateFormat("MM/dd/yyyy");
//			
//			Date date = webSdf.parse(tokenizer.nextToken());
//			
//			SandPEntry entry = new SandPEntry(price, date);
//			if (entries.isEmpty()) {
//				entries.add(entry);
//			} else if (date.compareTo(entries.get(0).date) > 0) {						
//				entries.add(0, new SandPEntry(price, date));
//			}		
//			
//			input.close();
//		}
//	}
}
