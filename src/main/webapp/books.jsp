<%-- 
    Document   : books
    Created on : Jan 21, 2020, 7:44:23 AM
    Author     : Chris.Cusack

    The view to show all books
--%>

<%@page import="java.util.List, edu.nbcc.model.Book"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="WEB-INF/jspf/header.jspf"%>
<title>Books</title>
</head>
<body class="p-4">
	<%@include file="WEB-INF/jspf/navigation.jspf"%>
	<h2>Books</h2>
	<%
		if (request.getAttribute("vm") != null) {
			List<Book> books = (List<Book>)request.getAttribute("vm");
	%>
	
	<table class="table table-striped">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Price</th>
			<th>Term</th>
		</tr>
	
		<% for (Book book : books) { %>
		<tr>
			<td><a href="book/<%=book.getId()%>"><%=book.getId()%></a></td>
			<td><%=book.getName() %></td>
			<td><%=book.getPrice() %></td>
			<td><%=book.getTerm() %></td>
		</tr>
		<% } %> 

	</table>
   <% } else { %> 	
       
	<h4>No Books</h4>
	
	<% } %> 


</body>
</html>
