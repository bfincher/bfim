package com.fincher.bfim_applet;

import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;

public class ZipReader {
	
	public static void main (String[] args) {
		try {
			GZIPInputStream input = new GZIPInputStream(new FileInputStream(args[0]));
			
			byte[] buf = new byte[4096];
			while (true) {
				int bytesRead = input.read(buf, 0, buf.length);
				if (bytesRead == -1) {
					break;
				}
				
				System.out.println(new String(buf, 0, bytesRead));
			}
			
			input.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
