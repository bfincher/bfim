package com.fincher.bfim_applet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

class BfimJPanel extends JPanel {	
	private Table table;
	private JScrollPane scrollPane;
	
	private GridBagConstraints gbcRemainder;
	
	public BfimJPanel(Criteria criteria, List<SandPEntry> entries) {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		
		gbcRemainder = (GridBagConstraints)gbc.clone();
		gbcRemainder.gridwidth = GridBagConstraints.REMAINDER;				
		
		table = Table.getInstance(criteria, entries);
		
		scrollPane = new JScrollPane(table);
		add(scrollPane, gbcRemainder);
	}		

}
