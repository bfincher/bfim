<HTML>
<HEAD><TITLE> 
    Brian Fincher Investment Method
</TITLE></HEAD>


<BODY BGCOLOR="white">

<%@ page language="java" import="com.fincher.bfim.jsp.*" %>
<jsp:useBean id="table" scope="page" class="com.fincher.bfim.jsp.BfimBean" />

<%
    table.processRequest(request);
    if (table.getProcessError() == false) {
%>

<CENTER>
<TABLE WIDTH=60% BORDER=1 CELLPADDING=10>
<TR>
<TH> Date </TH>
<TH> Price </TH>
<TH> 4 Days </TH>
<TH> 18 Days </TH>
<TH> Buy/Sell </TH>
</TR>

<%
	com.fincher.bfim.jsp.Entries entries = table.getEntries();
	while (entries.hasNext()) {
		com.fincher.bfim.SandPEntry entry = entries.next();
%>

	<TR>
		<TD><%out.print(entry.getDate());%></TD>
		<TD><%out.print(entry.getPrice());%></TD>
		<TD><%out.print(entry.getFourDays());%></TD>
		<TD><%out.print(entry.getEighteenDays());%></TD>
		
<%
		if (entry.getBuySell().equals("Buy")) {
%>
		<TD BGCOLOR=GREEN><%out.print(entry.getBuySell());%></TD>
<%
		} else if (entry.getBuySell().equals("Sell")) {
%>			
		<TD BGCOLOR=RED><%out.print(entry.getBuySell());%></TD>
<%
		} else {
%>
		<TD><%out.print(entry.getBuySell());%></TD>
<%
		}
%>
	</TR>
<%
    }
}
%>

</TABLE>
</CENTER>

</BODY>
</HTML>