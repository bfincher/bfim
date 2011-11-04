package com.fincher.bfim.jsp;

import java.util.Iterator;
import java.util.List;

import com.fincher.bfim.SandPEntry;

public class Entries {
	
	private final Iterator<SandPEntry> entries;
	
	public Entries(List<SandPEntry> list) {
		entries = list.iterator();
	}
	
	public boolean hasNext() {
		return entries.hasNext();
	}
	
	public SandPEntry next() {
		return entries.next();
	}

}
