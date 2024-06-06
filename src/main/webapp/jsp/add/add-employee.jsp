<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    Object error = request.getAttribute("error");
    
    %>
<form action="admin/add-employee" method="post" id="add-employee" onsubmit="submitForm(event)">
    <center><h2>ADD EMPLOYEE</h2></center>
    <input type="text" name="userName" placeholder="User Name" required>
    <input type="text" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <input type="text" name="role" placeholder="Role" required>
	<input type="text" name="aadhar" placeholder="Aadhar ID" required>
    <input type="text" name="branchId" placeholder="Branch Id" required>
    <input type="submit" value="ADD EMPLOYEE">
    <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #add-employee {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #add-employee input {
        padding: 2%;
        border: none;
    }
    #add-employee input[type="submit"]{
        background-color: greenyellow;
        cursor:pointer;
    }
</style>