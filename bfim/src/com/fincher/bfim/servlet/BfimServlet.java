package com.fincher.bfim.servlet;
/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/* $Id: RequestParamExample.java 982412 2010-08-04 21:55:19Z markt $
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fincher.bfim.SandPEntry;


public class BfimServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private final String TITLE = "Brian Fincher Investment Method";
    
    private static final String DATE_TAG = "!!DATE!!";
    private static final String PRICE_TAG = "!!PRICE!!";
    private static final String FOUR_DAYS_TAG = "!!FOUR_DAYS!!";
    private static final String EIGHTEEN_DAYS_TAG = "!!EIGHTEEN_DAYS!!";
    private static final String BUY_SELL_TAG = "!!BUY_SELL!!";    
    
    private static final int BUY_DAYS = 4;
    private static final int SELL_DAYS = 18;
    private static final float BUY_PERCENT = 0.035f;
    private static final float SELL_PERCENT = -0.075f;;
    
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();        
    
    private static final String TABLE_ROW = 
    	"<tr style='mso-yfti-irow:0'>\n"
  + "<td width=128 valign=top style='width:95.75pt;border:solid #8064A2 1.0pt;\n"
  + "mso-border-themecolor:accent4;border-right:none;padding:0in 5.4pt 0in 5.4pt'>\n"
  + "<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n"
  + "normal;mso-yfti-cnfc:68'><b> " + DATE_TAG + "<o:p></o:p></b></p>\n"
  + "</td>\n"
  + "<td width=128 valign=top style='width:95.75pt;border-top:solid #8064A2 1.0pt;\n"
  + "mso-border-top-themecolor:accent4;border-left:none;border-bottom:solid #8064A2 1.0pt;\n"
  + "mso-border-bottom-themecolor:accent4;border-right:none;padding:0in 5.4pt 0in 5.4pt'>\n"
  + "<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n"
  + "normal;mso-yfti-cnfc:64'>" + PRICE_TAG + "</p>\n"
  + "</td>\n"
  + "<td width=128 valign=top style='width:95.75pt;border-top:solid #8064A2 1.0pt;\n"
  + "mso-border-top-themecolor:accent4;border-left:none;border-bottom:solid #8064A2 1.0pt;\n"
  + "mso-border-bottom-themecolor:accent4;border-right:none;padding:0in 5.4pt 0in 5.4pt'>\n"
  + "<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n"
  + "normal;mso-yfti-cnfc:64'>" + FOUR_DAYS_TAG + "</p>\n"
  + "</td>\n"
  + "<td width=128 valign=top style='width:95.75pt;border-top:solid #8064A2 1.0pt;\n"
  + "mso-border-top-themecolor:accent4;border-left:none;border-bottom:solid #8064A2 1.0pt;\n"
  + "mso-border-bottom-themecolor:accent4;border-right:none;padding:0in 5.4pt 0in 5.4pt'>\n"
  + "<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n"
  + "normal;mso-yfti-cnfc:64'>" + EIGHTEEN_DAYS_TAG + "</p>\n"
  + "</td>\n"
  + "<td width=128 valign=top style='width:95.8pt;border:solid #8064A2 1.0pt;\n"
  + "mso-border-themecolor:accent4;border-left:none;padding:0in 5.4pt 0in 5.4pt'>\n"
  + "<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:\n"
  + "normal;mso-yfti-cnfc:64'>" + BUY_SELL_TAG + "</p>\n"
  + "</td>\n"
  + "</tr>\n";
    
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<link rel=File-List href=\"table_files/filelist.xml\">");
        out.println("<link rel=themeData href=\"table_files/themedata.thmx\">");
        out.println("<link rel=colorSchemeMapping href=\"table_files/colorschememapping.xml\">");
        
        out.println("<title>" + TITLE + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        
        out.println("<link href=\"http://www.fincherhome.com/CalendarControl.css\" "
        		+ "rel=\"stylesheet\" type=\"text/css\">");
        
        out.println("<script src=\"http://www.fincherhome.com/CalendarControl.js\" "
        	    + "language=\"javascript\"></script>");
        
        out.println("<center>");
        out.println("<h3>" + TITLE + "</h3>");
        String beginDateStr = request.getParameter("beginDate");
        String endDateStr = request.getParameter("endDate");
        if (beginDateStr != null || endDateStr != null) {
//            out.println("Begin Date (MM/DD/YYYY)");
//            out.println(" = " + HTMLFilter.filter(beginDateStr) + "<br>");
//            out.println("End Date   (MM/DD/YYYY)");
//            out.println(" = " + HTMLFilter.filter(endDateStr));
        } else {
        	GregorianCalendar today = new GregorianCalendar();
        	GregorianCalendar lastYear = new GregorianCalendar();
        	lastYear.add(GregorianCalendar.YEAR, -1);
        	
        	beginDateStr = SandPEntry.SDF.format(lastYear.getTime());
        	endDateStr = SandPEntry.SDF.format(today.getTime());
//            out.println("No Params");
        }
        out.println("<font size=8>");
        out.println("Brian Fincher Investment Method");
        out.println("<br>");
        out.println("</font>");
        out.println("<p>");
        out.println("The Brian Fincher Investment Method (BFIM) is a strategy on when to sell");
        out.println("<br>");
        out.println("and buy S&P 500 index funds.  This strategy attempts to minimize losses");
        out.println("<br>");
        out.println("by selling on down swings in the market and buying back on up swings.");
        out.println("<br>");
        out.println("This method was determined by analyzing S&P 500 closing prices since 1950.");
        out.println("<p>");
        out.println("The strategy says that you should sell if there is an 18 day loss of 7.5%");
        out.println("<br>");
        out.println("and you should buy if there is a 14 day gain of 3.5%.");
        out.println("<p>");
        out.println("<P>");
        out.print("<form action=\"");
        out.print("BfimServlet\" ");
        out.println("method=POST>");
        out.println("Begin Date");
        out.println("<input type=text size=20 name=beginDate value=\"" + beginDateStr + "\" onfocus=\"showCalendarControl(this);\">");
        out.println("<br>");
        out.println("End Date");
        out.println("<input type=text size=20 name=endDate  value=\"" + endDateStr + "\" onfocus=\"showCalendarControl(this);\">");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
        
        if (beginDateStr != null && endDateStr != null) {
        	buildTable(out, beginDateStr, endDateStr);
        }
                        
//        dumpFile(out, "webapps/bfim_servlet/tableHeader.txt");

        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
    }        
    

    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }
    
    private static void buildTable(PrintWriter out, String beginDateStr, String endDateStr) throws IOException {
        // table header and column headerss
        out.println("<table class=MsoTableLightListAccent4 border=1 cellspacing=0 cellpadding=0");
        out.println("style='border-collapse:collapse;border:none;mso-border-alt:solid #8064A2 1.0pt;");
        out.println("mso-border-themecolor:accent4;mso-yfti-tbllook:1184;mso-padding-alt:0in 5.4pt 0in 5.4pt'>");
        out.println("<tr style='mso-yfti-irow:-1;mso-yfti-firstrow:yes'>");
        out.println("<td width=128 valign=top style='width:95.75pt;border-top:solid #8064A2 1.0pt;");
        out.println("mso-border-top-themecolor:accent4;border-left:solid #8064A2 1.0pt;mso-border-left-themecolor:");
        out.println("accent4;border-bottom:none;border-right:none;background:#8064A2;mso-background-themecolor:");
        out.println("accent4;padding:0in 5.4pt 0in 5.4pt'>");
        out.println("<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:");
        out.println("normal;mso-yfti-cnfc:5'><b><span style='color:white;mso-themecolor:background1'>Date<o:p></o:p></span></b></p>");
        out.println("</td>");
        out.println("<td width=128 valign=top style='width:95.75pt;border:none;border-top:solid #8064A2 1.0pt;");
        out.println("mso-border-top-themecolor:accent4;background:#8064A2;mso-background-themecolor:");
        out.println("accent4;padding:0in 5.4pt 0in 5.4pt'>");
        out.println("<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:");
        out.println("normal;mso-yfti-cnfc:1'><b><span style='color:white;mso-themecolor:background1'>Price<o:p></o:p></span></b></p>");
        out.println("</td>");
        out.println("<td width=128 valign=top style='width:95.75pt;border:none;border-top:solid #8064A2 1.0pt;");
        out.println("mso-border-top-themecolor:accent4;background:#8064A2;mso-background-themecolor:");
        out.println("accent4;padding:0in 5.4pt 0in 5.4pt'>");
        out.println("<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:");
        out.println("normal;mso-yfti-cnfc:1'><b><span style='color:white;mso-themecolor:background1'>4");
        out.println("Days<o:p></o:p></span></b></p>");
        out.println("</td>");
        out.println("<td width=128 valign=top style='width:95.75pt;border:none;border-top:solid #8064A2 1.0pt;");
        out.println("mso-border-top-themecolor:accent4;background:#8064A2;mso-background-themecolor:");
        out.println("accent4;padding:0in 5.4pt 0in 5.4pt'>");
        out.println("<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:");
        out.println("normal;mso-yfti-cnfc:1'><b><span style='color:white;mso-themecolor:background1'>18");
        out.println("Days<o:p></o:p></span></b></p>");
        out.println("</td>");
        out.println("<td width=128 valign=top style='width:95.8pt;border-top:solid #8064A2 1.0pt;");
        out.println("vmso-border-top-themecolor:accent4;border-left:none;border-bottom:none;");
        out.println("border-right:solid #8064A2 1.0pt;mso-border-right-themecolor:accent4;");
        out.println("background:#8064A2;mso-background-themecolor:accent4;padding:0in 5.4pt 0in 5.4pt'>");
        out.println("<p class=MsoNormal style='margin-bottom:0in;margin-bottom:.0001pt;line-height:");
        out.println("normal;mso-yfti-cnfc:1'><b><span style='color:white;mso-themecolor:background1'>Trigger<o:p></o:p></span></b></p>");
        out.println("</td>");
        out.println("</tr>");
        
        Date beginDate = null;
        Date endDate = null;
        try {
        	beginDate = SandPEntry.SDF.parse(beginDateStr);
        	endDate = SandPEntry.SDF.parse(endDateStr);
        } catch (ParseException pe) {
        	out.println("Invalid date format");
        }
        
        if (beginDate != null && endDate != null) {
        	getData(out, beginDate, endDate);
        }
        
        out.println("</table>");
    }
    
//    private void dumpFile(PrintWriter out, String fileName) throws IOException {
//    	BufferedReader input = null;
//    	try {
//    		input = new BufferedReader(new FileReader(fileName));
//    		String str;
//    		
//    		while ((str = input.readLine()) != null) {
//    			out.println(str);
//    		}
//    	} finally {
//    		if (input != null) {
//    			input.close();
//    		}
//    	}
//    }
    
    private static void getData(PrintWriter out, Date beginDate, Date endDate) throws IOException {
		
    	List<SandPEntry> entries = SandPEntry.getFromWeb(beginDate, endDate);
    	
		for (int listIdx = 0; listIdx < entries.size(); listIdx++) {
			SandPEntry entry = entries.get(listIdx);
			String tableRow = TABLE_ROW.replace(DATE_TAG, entry.getDate());
			tableRow = tableRow.replace(PRICE_TAG, CURRENCY_FORMAT.format(entry.getPrice()));				
			
			float buyPercent = getCompValue(BUY_DAYS, listIdx, entries);
			float sellPercent = getCompValue(SELL_DAYS, listIdx, entries);
			
			tableRow = tableRow.replace(FOUR_DAYS_TAG, formatPercentString(buyPercent));
			tableRow = tableRow.replace(EIGHTEEN_DAYS_TAG, formatPercentString(sellPercent));
			
			if (sellPercent <= SELL_PERCENT) {
				tableRow = tableRow.replace(BUY_SELL_TAG, "Sell");
			} else if (buyPercent >= BUY_PERCENT) {
				tableRow = tableRow.replace(BUY_SELL_TAG, "Buy");
			} else {
				tableRow = tableRow.replace(BUY_SELL_TAG, "");
			}
			
			out.println(tableRow);			
		}
    }
    
    private static float getCompValue(int numDays, int idx, List<SandPEntry> entries) {
		int compIdx = idx + numDays;
		if (compIdx < entries.size()) {
			float thisPrice = entries.get(idx).getPrice();
			float compPrice = entries.get(compIdx).getPrice();
			float delta = (thisPrice - compPrice) / compPrice;
			return delta;
		} else {
			return Float.MAX_VALUE;
		}
	}
    
    private static String formatPercentString(float percent) {
		if (percent == Float.MAX_VALUE) {
			return "N/A";
		} else {
			return PERCENT_FORMAT.format(percent);
		}
	}

}

