<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="admin/view-employee" method="post" id="view-employee" onsubmit="submitForm(event)">
    <center><h2>FIND EMPLOYEE</h2></center>
    <input type="number" name="employeeId" placeholder="Employee ID">
        <input type="submit" value="VIEW EMPLOYEE">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #view-employee {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #view-employee input {
        padding: 2%;
        border: none;
    }
    #view-employee input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>