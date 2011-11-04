package com.fincher.bfim;

import java.util.GregorianCalendar;

public class DateUtil {
	
	private final String today;
	private final String lastYear;
	
	public DateUtil() {
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar lastYear = new GregorianCalendar();
		lastYear.add(GregorianCalendar.YEAR, -1);
		
		this.today = SandPEntry.SDF.format(today.getTime());
		this.lastYear = SandPEntry.SDF.format(lastYear.getTime());
	}	
	
	public String getToday() {
		return today;
	}
	
	public String getLastYear() {
		return lastYear;
	}

}
