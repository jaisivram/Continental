<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="app.models.Customer"%>
<%
    Customer customer = (Customer)request.getAttribute("customer");
	Object error = request.getAttribute("error");
%>

<table class="customer-table">
        <tr>
            <th>User ID</th>
            <td><span name="userId"><%= customer.getUserId() %></span></td>
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
            <td><span name="aadhar"><%= customer.getAadhar() %></span></td>
        </tr>
        <tr>
            <th>PAN</th>
            <td><span name="pan"><%= customer.getPan() %></span></td>
        </tr>
        <tr>
            <th>Primary Account</th>
            <td><span name="primaryAccount"><%= customer.getPrimaryAccountNumber() %></span></td>
        </tr>
    </table>	
	 <button id="editButton" onclick="toggleEdit('customer-table')">Edit</button>
    <button id="saveButton" style="display: none;" onclick="saveForm('customer-table','customer/update-self')">Save</button>
    <br>
    <h3 style="color:red">
	<%=error==null ? "":error.toString().toUpperCase()%>
	</h3>
	<br>
<br>
<form id="password-change" action="customer/change-password" method="post" onsubmit="submitForm(event)">
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
.customer-table {
	border-collapse: collapse;
	width: 100%;
}

.customer-table th, .customer-table td {
	border: 1px solid #205a9c;
	padding: 1rem;
	background-color: bisque;
}

.customer-table th {
	background-color: rgb(156, 185, 202);
	font-weight: bold;
	font-style: italic;
}

.customer-table th, .customer-table td {
	border-radius: 5px;
}

.customer-table  {
	border: 2px transparent;
	background-color: rgb(224, 210, 147);
	border-radius: 3px;
	font-size: 16px;
	padding: 1.5%;
	outline: none;
	cursor: pointer;
}
</style>
