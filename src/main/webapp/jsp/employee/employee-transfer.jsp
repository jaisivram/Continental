<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="app.models.*,java.util.List" %>
<form class="transfer" action="employee/transfer" method="post" onsubmit="submitForm(event)">
<h2>TRANSFER</h2>
<input type="number" placeholder="Account Number" name="sourceAccountNumber" required>
     <input type="number" placeholder="To account number" name="transactionAccountNumber" required>
    <input type="number" placeholder="Rs.00,00,000" name="amount" required min="1", max="1,00,000">
<select id="account-select" onchange="document.getElementById('ifsc-input').classList.toggle('hidden')">
  <option value="internal">INTRA BANK TRANSFER</option>
  <option value="external">INTER BANK TRANSFER</option>
</select>
<input type="text" name="scope" id="ifsc-input" placeholder="IFSC Code (For External)" class="hidden">
    <input type="submit" value="TRANSFER">
    <%=request.getAttribute("error")==null? "" :request.getAttribute("error")%>
    <%=request.getAttribute("tid")==null?"":request.getAttribute("tid")%>
    <%
%>
</form>
<style>
    .transfer {
	padding-top: 10px;
	font-family: sans-serif;
	display: flex;
	flex-direction: column;
	background-color: rgb(156, 185, 202);
	padding: 1.5%;
	gap: 1rem;
	border: 2px transparent;
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	font-style:bold;
    }
    .transfer input[type="submit"] {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
    }
.transfer input, .transfer select {
	padding: 0.5rem;
	border: none;
}
</style>