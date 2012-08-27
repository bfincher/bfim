<html>

<head>
	<title> Brian Fincher Investment Method </title>
</head>

<body bgcolor="white">

<link href="http://www.fincherhome.com/CalendarControl.css"
      rel="stylesheet" type="text/css">
<script src="http://www.fincherhome.com/CalendarControl.js"
        language="javascript"></script>

<%@ page language="java" import="com.fincher.bfim.jsp.*" %>
<jsp:useBean id="date" scope="page" class="com.fincher.bfim.DateUtil" />

<center>
	<font size=8>
	Brian Fincher Investment Method
	<br>
	</font>
	<p>
	The Brian Fincher Investment Method (BFIM) is a strategy on when to sell
	<br>
	and buy S&P 500 index funds.  This strategy attempts to minimize losses
	<br>
	by selling on down swings in the market and buying back on up swings.
	<br>
	This method was determined by analyzing S&P 500 closing prices since 1950.
	<p>
	The strategy says that you should sell if there is an 18 day loss of 7.5%
	<br>
	and you should buy if there is a 14 day gain of 3.5%.
	<p>
	
	<form method=GET action=bfim.jsp>
		<font size=5>
		    Begin Date <input type=text name="beginDate" value=<%out.print(date.getLastYear());%> size=20 onfocus="showCalendarControl(this);">
		</font>
		<br>
		<font size=5> End Date <input type=text name="endDate" value=<%out.print(date.getToday());%> size=20 onfocus="showCalendarControl(this);">
		</font>
		<br>
		<input type=submit name=action value="Submit">
	</form>
</center>
</body>
</html>