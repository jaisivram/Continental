<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="app.models.*,java.util.List"%>
<%List<Account> accounts = (List<Account>)request.getAttribute("accounts"); 
Long primaryAccountNumber = (Long)request.getAttribute("primaryAccountNumber");
%>
<form class="deposit" action="customer/deposit" method="post" onsubmit="submitForm(event)">
<h2>DEPOSIT</h2>
	<select name="sourceAccountNumber">
		<%for (Account account : accounts) { %>
		<% if(account.getAccountNumber().equals(primaryAccountNumber)) { %>
		<%="<option value='"+primaryAccountNumber+"' selected>"+primaryAccountNumber+"</option>"%>
		<% } else { %>
		<%="<option value='"+account.getAccountNumber()+"'>"+account.getAccountNumber()+"</option>"%>
		<%}}%>
	</select> <input type="number" placeholder="Rs.00,00,000" name="amount" required
		min="1" , max="1,00,000"> <input type="submit" value="DEPOSIT">
	<%=request.getAttribute("error")==null? "" :request.getAttribute("error")%>
	<%=request.getAttribute("tid")==null?"":request.getAttribute("tid")%>
	<%

%>
</form>
<style>
.deposit {
	padding-top: 10px;
	font-family: sans-serif;
	display: flex;
	flex-direction: column;
	background-color: rgb(156, 185, 202);
	padding: 1.5%;
	gap: 1rem;
	border: 2px transparent;
	border-radius: 3px;
	padding: 1.5%;
	outline: none;
}

.deposit input[type="submit"] {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}

.deposit input, .deposit select {
	padding: 0.5rem;
	border: none;
}

</style>