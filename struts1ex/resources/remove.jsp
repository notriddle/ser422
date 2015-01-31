<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm Remove</title>
</head>
<body>

<CENTER>
<em>Do you really want to remove the phonebook entry for phone number <%= request.getParameter("entry") %>?</em>
<br/>
<form action="<html:rewrite page='/remove.do'/>" METHOD="POST">
	<INPUT TYPE="hidden" name="phone" value="<%= request.getParameter("entry") %>"/>
	<INPUT TYPE="submit" value="Remove"/>
</form>
</CENTER>
</body>
</html>