<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="app.models.*,java.util.List"%>
<form class="withdraw" action="employee/withdraw" method="post" onsubmit="submitForm(event)">
<h2>WITHDRAW</h2>
<input type="number" placeholder="Account Number" name="sourceAccountNumber" required>
<input type="number" placeholder="Rs.00,00,000" name="amount" required
		min="1" , max="1,00,000"> <input type="submit" value="WITHDRAW">
	<%=request.getAttribute("error")==null? "" :request.getAttribute("error")%>
	<%=request.getAttribute("tid")==null?"":request.getAttribute("tid")%>
	<%

%>
</form>
<style>
.withdraw {
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

.withdraw input[type="submit"] {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}

.withdraw input, .withdraw select {
	padding: 0.5rem;
	border: none;
}

</style>