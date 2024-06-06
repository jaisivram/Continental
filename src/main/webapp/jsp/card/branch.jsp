<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Branch"%>
<%
    Branch branch = (Branch)request.getAttribute("branch");
    Object parent = request.getAttribute("parent");
	Object error = request.getAttribute("error");
%>
<button onclick="document.getElementById('<%=parent==null ? new String() : parent.toString()%>').click()"  style="width:30px;color:white;background-color:red">X</button>
<table class="branch-table">
    <tr>
        <th>Branch ID</th>
        <td><span name="branchId" class="identity"><%= branch.getBranchId() %></span></td>
    </tr>
    <tr>
        <th>IFSC Code</th>
        <td><span name="ifsc"><%= branch.getIfsc() %></span></td>
    </tr>
    <tr>
        <th>Address</th>
        <td><span name="address" class="changable"><%= branch.getAddress() %></span></td>
    </tr>
    <tr>
        <th>Branch Name</th>
        <td><span name="branchName"><%= branch.getBranchName() %></span></td>
    </tr>
    <tr>
        <th>Account Count</th>
        <td><span name="accountCount"><%= branch.getAccountCount() %></span></td>
    </tr>
    <tr>
        <th>Customer Count</th>
        <td><span name="customerCount"><%= branch.getCustomerCount() %></span></td>
    </tr>
    <tr>
        <th>Branch Worth</th>
        <td><span name="branchWorth"><%= branch.getBranchWorth() %></span></td>
    </tr>
</table>	
<button id="editButton" onclick="toggleEdit('branch-table')">Edit</button>
<button id="saveButton" style="display: none;" onclick="saveForm('branch-table','admin/update-branch')">Save</button>
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<style>
#editButton , #saveButton {
	padding:0.5rem;
	border: none;
}
#editButton , #saveButton {
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}
.branch-table {
	border-collapse: collapse;
	width: 100%;
}

.branch-table th, .branch-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.branch-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.branch-table th, .branch-table td {
	border-radius: 5px;
}

.branch-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>
