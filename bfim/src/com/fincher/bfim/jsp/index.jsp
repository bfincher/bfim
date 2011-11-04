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