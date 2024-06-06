<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="app.models.Employee"%>
<%
    Employee employee = (Employee)request.getAttribute("employee");
%><%
	Object error = request.getAttribute("error");
%>

<table class="employee-table">
        <tr>
            <th>User ID</th>
            <td><span name="userId"><%= employee.getUserId() %></span></td>
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
            <th>Role</th>
            <td><span name="role"><%= employee.getRole() %></span></td>
        </tr>
                <tr>
            <th>Aadhar</th>
            <td><span name="aadhar"><%= employee.getAadhar() %></span></td>
        </tr>
        <tr>
            <th>Branch ID</th>
            <td><span name="branchId"><%= employee.getBranchId()%></span></td>
        </tr>
    </table>	
	 <button id="editButton" onclick="toggleEdit('employee-table')">Edit</button>
    <button id="saveButton" style="display: none;" onclick="saveForm('employee-table','employee/update-self')">Save</button>
    <br>
    <h3 style="color:red">
	<%=error==null ? "":error.toString().toUpperCase()%>
	</h3>
	<br>
<br>
<form id="password-change" action="employee/change-password" method="post" onsubmit="submitForm(event)">
	<h2>CHANGE PASSWORD</h2>
	<input type="password" placeholder="Old Password" name="oldPassword"
		required> <input type="password" placeholder="New Password"
		pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.*\s).{8,}"
		name="newPassword" required> <input type="password"
		placeholder="Confirm New Password"
		pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.*\s).{8,}"
		name="confirmPassword" required> <input type="submit"
		value="Change password">
	<%=request.getAttribute("passwordChangeSuccess")==null ? "" : request.getAttribute("passwordChangeSuccess").toString()%>
	<%=request.getAttribute("passwordChangeError")==null ? "" : request.getAttribute("passwordChangeError").toString()%>
</form>

<style>
#password-change {
	padding-top:10px;
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
}
#password-change input , #editButton , #saveButton{
	padding:0.5rem;
	border: none;
}
#password-change input[type="submit"] , #editButton , #saveButton{
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
