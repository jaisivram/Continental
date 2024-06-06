<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Account"%>
<%@page import="java.util.List" %>
<%
    List<Account> accounts = (List<Account>)request.getAttribute("accounts");
	Object error = request.getAttribute("error");
    Object isAdmin = request.getAttribute("isAdmin");
	    Object parent = request.getAttribute("parent");
%>
<button onclick="document.getElementById('<%=parent==null ? new String() : parent.toString()%>').click()"  style="width:30px;color:white;background-color:red">X</button>

<% for(Account account : accounts) {%>
<table class=<%="account-table account-table-"+account.getAccountNumber()%>>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
    <tr>
        <th>Account Number</th>
        <td><span name="accountNumber"><%= account.getAccountNumber() %></span></td>
    </tr>
    <tr>
        <th>Balance</th>
        <td><span name="balance"><%= account.getBalance() %></span></td>
    </tr>
        <th>Branch ID</th>
        <td><span name="branchId" class="<%=(Boolean) isAdmin != null ? "changable" : "" %>"><%= account.getBranchId() %></span></td>
    </tr>
    <tr>
        <th>Status</th>
        <td><span name="status" class="changable"><%= account.getStatus() %></span></td>
    </tr>
</table>	
<button onclick='postAccountView(<%= account.getAccountNumber()%>)'>VIEW</button>
<br>
<br>
<%} %>
<style>
button {
	padding:0.5rem;
	border: none;
}
button {
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