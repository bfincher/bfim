package com.fincher.bfim_applet;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

class Table extends JTable {
	
	private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();
	static {
		PERCENT_FORMAT.setMaximumFractionDigits(2);
	}
	
	public static Table getInstance(Criteria criteria, List<SandPEntry> entries) {
		String[] columnNames = {"Date", 
				"Price", 				
				String.valueOf(criteria.buyDays) + " Days", 
				String.valueOf(criteria.sellDays) + " Days",
				"Trigger"
		};
		
		return new Table(columnNames, criteria, entries);
	}
	
	public Table(String[] columnNames, 
			Criteria criteria, 
			List<SandPEntry> entries) {
		super(new DefaultTableModel(columnNames, entries.size()));
		
		TableModel model = getModel();
		
		int tableIdx = 0;
		int listIdx = entries.size() - 1;
		while (tableIdx < entries.size()) {
			SandPEntry entry = entries.get(listIdx);
			int colIdx = 0;
			model.setValueAt(SandPEntry.sdf.format(entry.date), tableIdx, colIdx++);
			model.setValueAt(CURRENCY_FORMAT.format(entry.price), tableIdx, colIdx++);			
			
			float buyPercent = getCompValue(criteria.buyDays, listIdx, entries);
			float sellPercent = getCompValue(criteria.sellDays, listIdx, entries);			
			
			model.setValueAt(formatPercentString(buyPercent), tableIdx, colIdx++);
			model.setValueAt(formatPercentString(sellPercent), tableIdx, colIdx++);
			
			if (sellPercent <= criteria.sellPercent) {
				model.setValueAt("Sell", tableIdx, colIdx++);
			} else if (buyPercent >= criteria.buyPercent) {
				model.setValueAt("Buy", tableIdx, colIdx++);
			}
			
			tableIdx++;
			listIdx--;
		}
		
	}
	
	private static String formatPercentString(float percent) {
		if (percent == Float.MAX_VALUE) {
			return "N/A";
		} else {
			return PERCENT_FORMAT.format(percent);
		}
	}
	
	private static float getCompValue(int numDays, int idx, List<SandPEntry> entries) {
		int compIdx = idx - numDays;
		if (compIdx >= 0) {
			float thisPrice = entries.get(idx).price;
			float compPrice = entries.get(compIdx).price;
			float delta = (thisPrice - compPrice) / compPrice;
			return delta;
		} else {
			return Float.MAX_VALUE;
		}
	}

}
