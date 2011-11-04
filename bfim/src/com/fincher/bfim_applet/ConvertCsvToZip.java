package com.fincher.bfim_applet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

public class ConvertCsvToZip {
	
	public static void main (String[] args) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(args[0]));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(args[1]))));
			
			String str;
			while ((str = input.readLine()) != null) {
				output.write(str + "\n");
			}
			
			input.close();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
