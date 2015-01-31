<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="edu.asupoly.cst425.lab4.model.*" %>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit a new phone entry</title>
</head>
<body>
<CENTER>
<p>
<b>Edit Phonebook entry:</b>
</p>
<html:form action="/edit" method="POST"> 
<%@ include file="/WEB-INF/userform.jsp" %>
</html:form>

</CENTER>
</body>
</html>