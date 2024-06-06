<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="employee/add-customer" method="post" id="add-customer" onsubmit="submitForm(event)">
    <center><h2>ADD CUSTOMER</h2></center>
    <input type="text" name="userName" placeholder="User Name" required>
    <input type="text" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <input type="text" name="pan" placeholder="PAN Number" required>
    <input type="text" name="aadhar" placeholder="Aadhar ID" required>
    <input type="submit" value="Add CUSTOMER">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #add-customer {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #add-customer input {
        padding: 2%;
        border: none;
    }
    #add-customer input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>