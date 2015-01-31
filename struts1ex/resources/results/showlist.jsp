<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="edu.asupoly.cst425.lab4.model.PhoneEntry" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %> 
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Lab 4 List page</title>
</head>
<body>
<p>
<bean:write name="message" property="message" ignore="true"/>
<% session.removeAttribute("message"); %>
</p>

<!--  Let's put the Find dialog and add link at the top of the page -->
<%
	String filter = (String)session.getAttribute("findFilter");
	if (filter == null) {
		filter = "";
	}
%>

<FORM action="<html:rewrite page='/list.do'/>" method="POST">
  <INPUT TYPE="text" NAME="lastname" VALUE="<%= filter %>"> 
  <INPUT TYPE="submit" VALUE="Find"/>
</FORM>

</hr/>
<!--  Not using useBean here since I don't know how to say a String array! -->
<% 
   	PhoneEntry[] phoneNumbers = (PhoneEntry[])request.getAttribute("phoneNumbers");
	if (phoneNumbers == null || phoneNumbers.length == 0) {
		if (filter == null || filter.length() == 0) {
%>
The phonebook has no entries
<a href="add.jsp">add a new phonebook entry</a>
<br/>
<%
		} else {
%>
There are no entries found in the phonebook for lastname filter <%= filter %>
<a href="add.jsp">add a new phonebook entry</a>
<%
		}
    } else {
%>
<p>
Phonebook entries
<% if (filter != null && filter.length() > 0) { %>
for lastname filter <% out.println(filter); } %>
<a href="add.jsp">add a new phonebook entry</a>
</p>
<OL>
<%
for (int i = 0; i < phoneNumbers.length; i++) {
%>
<LI> <%= phoneNumbers[i] %> 
     <a href="remove.jsp?entry=<%= phoneNumbers[i].getPhone() %>">remove</a>
	 <a href="<html:rewrite page='/editsetup.do?entry='/><%= phoneNumbers[i].getPhone() %>">edit</a>     
</LI>
<%		
}
%>
</OL>
<%
}
%>
</body>
</html>