package com.fincher.bfim_applet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

class SandPEntry {
	
	static class DateComparator implements Comparator<SandPEntry> {
		public int compare(SandPEntry e1, SandPEntry e2) {			
			return e1.date.compareTo(e2.date);
		}
	}
	
	public static final String DATA_FILE_NAME = "sandpzip.bin";
	
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public final float price;
	public final Date date;
	
	public SandPEntry(float price, Date date) {
		this.price = price;
		this.date = date;
	}
	
	public SandPEntry(String str) throws ParseException {
		StringTokenizer st = new StringTokenizer(str, ",");
		date = sdf.parse(st.nextToken());
		price = new Float(st.nextToken());		
	}
	
	public String toString() {
		return String.valueOf(sdf.format(date) + "," + price);
	}
	
	public static List<SandPEntry> loadFromWeb() throws IOException, ParseException {
		return loadFromStream(new URL("https://www.fincherhome.com/bfim/sandpzip.bin").openStream());
	}
	
	public static List<SandPEntry> loadFromClasspath() throws IOException, ParseException {
		return loadFromStream(ClassLoader.getSystemResourceAsStream("com/fincher/bfim_applet/" + DATA_FILE_NAME));
	}
	
	public static List<SandPEntry> loadFromFile(File file) throws IOException, ParseException {
		return loadFromStream(new FileInputStream(file));		
	}
	
	public static List<SandPEntry> loadFromStream(InputStream stream) 
			throws IOException, ParseException {
		BufferedReader input = new BufferedReader(new InputStreamReader(new GZIPInputStream(stream)));
		StringTokenizer tokenizer = new StringTokenizer(input.readLine(), ",");
		int numEntries = new Integer(tokenizer.nextToken());
		
		List<SandPEntry> entries = new ArrayList<SandPEntry>(numEntries + 10);
		String str;
		
		while ((str = input.readLine()) != null) {
			entries.add(new SandPEntry(str));
		}
		
		input.close();
		return entries;
	}
	
	public static void saveToFile(List<SandPEntry> entries, File file) throws IOException {
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file))));
		output.write(String.valueOf(entries.size()));
		
		for (SandPEntry entry: entries) {
			output.write("\n" + entry.toString());
		}
		
		output.close();
	}
}
