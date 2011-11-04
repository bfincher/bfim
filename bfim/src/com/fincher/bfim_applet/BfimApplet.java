package com.fincher.bfim_applet;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.swing.JApplet;

public class BfimApplet extends JApplet {
						
	private static final int SELL_DAYS = 18;
	private static final int BUY_DAYS = 4;
	private static final float SELL_PERCENT = -0.075f;
	private static final float BUY_PERCENT = 0.035f;
	
	private static final Criteria CRITERIA = new Criteria(BUY_DAYS, BUY_PERCENT, SELL_DAYS, SELL_PERCENT);	
	
	public BfimApplet() throws IOException, ParseException {
		List<SandPEntry> list = SandPEntry.loadFromWeb();
		
//		// get the latest price
//		URL url = new URL("http://quote.yahoo.com/d/quotes.csv?s=^GSPC&f=sl1d1t1c1ohgv&e=.csv");
//		
//		BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
//		String csv = input.readLine();
//		StringTokenizer tokenizer = new StringTokenizer(csv, ",\"");
//		
//		tokenizer.nextToken(); // skip ticker symbol
//		float price = new Float(tokenizer.nextToken());
//		Date date = SandPEntry.sdf.parse(tokenizer.nextToken());
//		if (date.compareTo(list.get(list.size() - 1).date) > 0) {
//			list.add(new SandPEntry(price, date));
//		}		
//		
//		input.close();
		
		getContentPane().add(new BfimJPanel(CRITERIA, list));
	}	

}
