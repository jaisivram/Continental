<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Account"%>
<%
    Account account = (Account)request.getAttribute("account");
	Object error = request.getAttribute("error");
    Object isAdmin = request.getAttribute("isAdmin");
    Object parent = request.getAttribute("parent");
%>
<button onclick="document.getElementById('<%=parent==null ? new String() : parent.toString()%>').click()"  style="width:30px;color:white;background-color:red">X</button>
<table class="account-table">
    <tr>
        <th>Account Number</th>
        <td><span name="accountNumber" class="identity"><%= account.getAccountNumber() %></span></td>
    </tr>
    <tr>
        <th>Balance</th>
        <td><span name="balance"><%= account.getBalance() %></span></td>
    </tr>
    <tr>
        <th>IFSC Code</th>
        <td><span name="ifsc" class="<%=(Boolean) isAdmin != null ? "changable" : "" %>"><%= account.getIfsc() %></span></td>
    </tr>
    <tr>
        <th>Branch ID</th>
        <td><span name="branchId" class="<%=(Boolean) isAdmin != null ? "changable" : "" %>"><%= account.getBranchId() %></span></td>
    </tr>
    <tr>
        <th>Customer ID</th>
        <td><span name="customerId"><%= account.getCustomerId() %></span></td>
    </tr>
    <tr>
        <th>Status</th>
        <td><span name="status" class="changable"><%= account.getStatus() %></span></td>
    </tr>
    <tr>
        <th>Creation Time</th>
        <td><span name="creationTime"><%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date(account.getCreationTime()))  %></span></td>
    </tr>
</table>	
<button id="editButton" onclick="toggleEdit('account-table')">Edit</button>
<button id="saveButton" style="display: none;" onclick="saveForm('account-table','employee/update-account')">Save</button>
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<style>
#editButton , #saveButton, #toggleStatusButton {
	padding:0.5rem;
	border: none;
}
#editButton , #saveButton, #toggleStatusButton {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}
.account-table {
	border-collapse: collapse;
	width: 100%;
}

.account-table th, .account-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.account-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.account-table th, .account-table td {
	border-radius: 5px;
}

.account-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>
