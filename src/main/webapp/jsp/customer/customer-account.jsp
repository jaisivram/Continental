<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import='app.models.*'%>
<%
Account account = (Account) request.getAttribute("account");
Object error = request.getAttribute("error");
Long primaryAccountNumber = (Long) request.getAttribute("primaryAccountNumber");
%>

<div class="account-container">
    <% if(error == null) { %>
    <div class="account-details">
        <label for="account-number">Account Number:</label>
        <span id="account-number"><%= account.getAccountNumber() %><%=account.getAccountNumber().equals(primaryAccountNumber) ? "  : Primary" : ""%></span>
    </div>
    <div class="account-details">
        <label for="balance">Balance:</label>
        <span id="balance">Rs.<%= account.getBalance() %></span>
    </div>
    <%if(!request.getSession().getAttribute("userType").toString().equals("customer")) { %>
    <div class="account-details">
        <label for="customer-id">Customer ID:</label>
        <span id="customer-id"><%= account.getCustomerId() %></span>
    </div>
    <% } %>
    <div class="account-details">
        <label for="status">Status:</label>
        <span id="status"><%= account.getStatus().toUpperCase() %></span>
    </div>
    <% if(!account.getAccountNumber().equals(primaryAccountNumber)&&account.getStatus().equals("active")) { %>
    <div class='account-details'>
        <form action="customer/set-primary" method="post" onsubmit="submitForm(event)">
        <input type="hidden" value=<%=account.getAccountNumber()%> name="accountNumber">
        <input type="submit" value="Set Primary">
        </form>
    </div>
    <% } %>
    <% } else { %>
    <%= error %>
    <% } %>
</div>
<style>
.account-container {
	border: 4px solid rgb(156, 185, 202); /* Thickened border */
	border-radius: 8px;
	padding: 20px;
	width: auto;
	margin-bottom: 2%;
	background-color: rgb(156, 185, 202); /* Background color */
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.account-details {
	margin-bottom: 10px;
}

.account-details label {
	font-weight: bold;
	font-size: 120%;
	font-style: italic;
}

.account-details span {
	margin-left: 10px;
	font-weight: bolder;
	font-size: 120%;
	color: #f7edb9; /* White font color */
}

.account-container input[type="submit"] {
	border: 2px transparent;
	background-color: rgb(62, 212, 107);
	border-radius: 3px;
	width: 10vw;
	padding: 5px 10px;
	font-size: 16px;
	outline: none;
	cursor: pointer;
}
</style>
