<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.*"%>
<%@page import="java.util.List" %>
<%
    Customer customer = (Customer)request.getAttribute("customer");
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
	Object error = request.getAttribute("error");
    Object parent = request.getAttribute("parent");
%>
<button onclick="document.getElementById('<%=parent==null ? new String() : parent.toString()%>').click()"  style="width:30px;color:white;background-color:red">X</button>
<table class="customer-table">
    <tr>
        <th>User ID</th>
        <td><span name="customerId" class="identity"><%= customer.getUserId() %></span></td>
    </tr>
    <tr>
        <th>User Name</th>
        <td><span name="userName"><%= customer.getUserName() %></span></td>
    </tr>
    <tr>
        <th>Email ID</th>
        <td><span name="email" class="changable"><%= customer.getEmail() %></span></td>
    </tr>
    <tr>
        <th>Aadhar</th>
        <td><span name="aadhar" class="changable"><%= customer.getAadhar() %></span></td>
    </tr>
    <tr>
        <th>PAN</th>
        <td><span name="pan" class="changable"><%= customer.getPan() %></span></td>
    </tr>
        <tr>
        <th>STATUS</th>
        <td><span name="status" class="changable"><%= customer.getStatus() %></span></td>
    </tr>
    <tr>
        <th>Primary Account</th>
        <td><span name="primaryAccountNumber" class="changable"><%= customer.getPrimaryAccountNumber() %></span></td>
    </tr>
</table>
<button id="editButton" onclick="toggleEdit('customer-table')">Edit</button>
<button id="saveButton" style="display: none;" onclick="saveForm('customer-table','employee/update-customer')">Save</button>
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<%
if (accounts != null && !accounts.isEmpty()) {
%>
<form id="accountForm" action="employee/view-account" method="post" onsubmit="submitForm(event);">
    <input type="hidden" name="accountNumber" id="accountNumberInput">
    <table class="account-list-table">
        <tr>
            <th>Account Number</th>
            <th>Balance</th>
            <th>IFSC Code</th>
            <th>Status</th>
            <th>View</th>
        </tr>
        <% for (Account account : accounts) { %>
        <tr>
            <td><%= account.getAccountNumber() %></td>
            <td><%= account.getBalance() %></td>
            <td><%= account.getIfsc() %></td>
            <td><%= account.getStatus() %></td>
            <td><button type="button" onclick="selectAccount('<%= account.getAccountNumber() %>')">View</button></td>
        </tr>
        <% } %>
    </table>
</form>
<%
}
%>

<style>
#editButton , #saveButton{
	padding: 0.5rem;
	border: none;
}
#editButton , #saveButton {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}
.customer-table, .account-list-table {
	border-collapse: collapse;
	width: 100%;
}

.customer-table th, .customer-table td, .account-list-table th, .account-list-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.customer-table th, .account-list-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.customer-table th, .customer-table td, .account-list-table th, .account-list-table td {
	border-radius: 5px;
}

.customer-table, .account-list-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>

<script>
function selectAccount(accountNumber) {
    document.getElementById('accountNumberInput').value = accountNumber;
    document.getElementById('accountForm').submit();
}
</script>