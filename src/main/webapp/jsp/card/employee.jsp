<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Employee"%>
<%
    Employee employee = (Employee)request.getAttribute("employee");
	Object error = request.getAttribute("error");
    Object parent = request.getAttribute("parent");
%>
<button onclick="document.getElementById('<%=parent==null ? new String() : parent.toString()%>').click()"  style="width:30px;color:white;background-color:red">X</button>
<table class="employee-table">
    <tr>
        <th>User ID</th>
        <td><span name="employeeId" class="identity"><%= employee.getUserId() %></span></td>
    </tr>
    <tr>
        <th>User Name</th>
        <td><span name="userName"><%= employee.getUserName() %></span></td>
    </tr>
    <tr>
        <th>Email ID</th>
        <td><span name="email" class="changable"><%= employee.getEmail() %></span></td>
    </tr>
    <tr>
        <th>Aadhar</th>
        <td><span name="aadhar" class="changable"><%= employee.getAadhar() %></span></td>
    </tr>
    <tr>
        <th>Role</th>
        <td><span name="role" class="changable"><%= employee.getRole() %></span></td>
    </tr>
        <tr>
        <th>Branch ID</th>
        <td><span name="branchId" class="changable"><%= employee.getBranchId() %></span></td>
    </tr>
        <tr>
        <th>Status</th>
        <td><span name="status" class="changable"><%= employee.getStatus() %></span></td>
    </tr>
</table>
<button id="editButton" onclick="toggleEdit('employee-table')">Edit</button>
<button id="saveButton" style="display: none;" onclick="saveForm('employee-table','admin/update-employee')">Save</button>
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<style>
#editButton , #saveButton{
	padding:0.5rem;
	border: none;
}
#editButton , #saveButton{
	background-color: rgb(62, 212, 107);
	outline: none;
	cursor: pointer;
}
.employee-table {
	border-collapse: collapse;
	width: 100%;
}

.employee-table th, .employee-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.employee-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.employee-table th, .employee-table td {
	border-radius: 5px;
}

.employee-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>