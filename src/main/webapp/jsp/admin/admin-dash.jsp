<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Bank"%>
<%@ page import="java.text.NumberFormat,java.util.Locale"%>
<%
    Bank bank = (Bank)request.getAttribute("bank");
    Object parent = request.getAttribute("parent");
	Object error = request.getAttribute("error");
    if(error!=null){
        bank = new Bank();
    }
%>
<table class="Bank-table">
    <tr>
        <th>Bank Worth</th>
        <td><span name="BankWorth"><%= NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(bank.getBankWorth()) %></span></td>
    </tr>
    <tr>
        <th>Customer Count</th>
        <td><span name="customerCount"><%= bank.getCustomerCount() %></span></td>
    </tr>
    <tr>
        <th>Account Count</th>
        <td><span name="accountCount"><%= bank.getAccountCount() %></span></td>
    </tr>
    <tr>
        <th>Employee Count</th>
        <td><span name="employeeCount"><%= bank.getEmployeeCount() %></span></td>
    </tr>
    <tr>
        <th>Branch Count</th>
        <td><span name="branchCount"><%= bank.getBranchCount() %></span></td>
    </tr>
</table>	
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<style>
.Bank-table {
	border-collapse: collapse;
	width: 100%;
}

.Bank-table th, .Bank-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.Bank-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.Bank-table th, .Bank-table td {
	border-radius: 5px;
}

.Bank-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>
