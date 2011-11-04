package com.fincher.bfim_applet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class UpdateSandPPrice {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String URL_STRING = "http://ichart.finance.yahoo.com/table.csv?s=%5EGSPC&d=%%END_MONTH%&e=%END_DAY%&f=%END_YEAR%&g=d&a=%BEGIN_MONTH%&b=%BEGIN_DAY%&c=%BEGIN_YEAR%&ignore=.csv";
	
	public static void main (String[] args) {
		try {						
			
//			System.setProperty("http.proxyHost","proxy.mdnt.com");
//			System.setProperty("http.proxyPort", "9119");
//			System.setProperty("http.proxyUser", "s149450");
//			System.setProperty("http.proxyPassword", "pw"); 
			
			File dataFile = new File(args[0] + "/" + SandPEntry.DATA_FILE_NAME);
			
			if (args.length > 1) {
				File outFile = new File(args[1]);
				PrintStream out = new PrintStream(outFile);
				System.setOut(out);
				System.setErr(out);
			}
			
			while (true) {
				List<SandPEntry> entries = SandPEntry.loadFromFile(dataFile);									
			
				GregorianCalendar endCal = new GregorianCalendar();
				endCal.set(GregorianCalendar.HOUR_OF_DAY, 0);
				endCal.set(GregorianCalendar.MINUTE, 0);
				endCal.set(GregorianCalendar.SECOND, 0);
				endCal.set(GregorianCalendar.MILLISECOND, 0);
				
//				System.out.println(endCal.getTime());
			
				// have the begin date be at least 5 days ago so that we will
				// cover weekends and holidays
				GregorianCalendar fiveDaysAgo = new GregorianCalendar();
				fiveDaysAgo.setTime(endCal.getTime());
				fiveDaysAgo.add(GregorianCalendar.DATE, -5);
			
				Date beginDate;
				Date lastEntry = entries.get(entries.size() - 1).date;
				if (lastEntry.before(fiveDaysAgo.getTime())) {
					beginDate = lastEntry;
				} else {
					beginDate = fiveDaysAgo.getTime();
				}
			
				GregorianCalendar beginCal = new GregorianCalendar();
				beginCal.setTime(beginDate);
			
				String tempUrl = URL_STRING.replaceAll("%END_MONTH%", String.valueOf(endCal.get(GregorianCalendar.MONTH)));
				tempUrl = tempUrl.replaceAll("%END_MONTH%", String.valueOf(endCal.get(GregorianCalendar.MONTH)));
				tempUrl = tempUrl.replaceAll("%END_DAY%", String.valueOf(endCal.get(GregorianCalendar.DATE)));
				tempUrl = tempUrl.replaceAll("%END_YEAR%", String.valueOf(endCal.get(GregorianCalendar.YEAR)));
			
				tempUrl = tempUrl.replaceAll("%BEGIN_MONTH%", String.valueOf(beginCal.get(GregorianCalendar.MONTH)));
				tempUrl = tempUrl.replaceAll("%BEGIN_DAY%", String.valueOf(beginCal.get(GregorianCalendar.DATE)));
				tempUrl = tempUrl.replaceAll("%BEGIN_YEAR%", String.valueOf(beginCal.get(GregorianCalendar.YEAR)));
				System.out.println("URL = " + tempUrl);
			
				URL url = new URL(tempUrl);
			
				BufferedReader urlInput = new BufferedReader(new InputStreamReader(url.openStream()));
				urlInput.readLine(); // skip header line
		
				ArrayList<SandPEntry> tempList = new ArrayList<SandPEntry>();
			
				String csv;
				while ((csv = urlInput.readLine()) != null) {
					StringTokenizer tokenizer = new StringTokenizer(csv, ",\"");
					Date date = sdf.parse(tokenizer.nextToken());
					if (date.compareTo(lastEntry) > 0) {
						for (int i = 0; i < 5; i++) {
							tokenizer.nextToken();
						}
					
						SandPEntry newEntry = new SandPEntry(new Float(tokenizer.nextToken()), date);
						System.out.println("Adding entry " +  newEntry);
						tempList.add(newEntry);
					} else {
						System.out.println("Not adding entry for " + date);
					}
				}
			
				if (!tempList.isEmpty()) {
					Collections.sort(tempList, new SandPEntry.DateComparator());
					entries.addAll(tempList);
					SandPEntry.saveToFile(entries, dataFile);
				}
				
				GregorianCalendar tomorrow6pm = new GregorianCalendar();
				tomorrow6pm.add(GregorianCalendar.DATE, 1);
				tomorrow6pm.set(GregorianCalendar.HOUR_OF_DAY, 18);
				tomorrow6pm.set(GregorianCalendar.MINUTE, 0);
				tomorrow6pm.set(GregorianCalendar.SECOND, 0);
				tomorrow6pm.set(GregorianCalendar.MILLISECOND, 0);
				
				Thread.sleep(tomorrow6pm.getTimeInMillis() - System.currentTimeMillis());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
