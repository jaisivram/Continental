<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="employee/view-customer" method="post" id="view-customer" onsubmit="submitForm(event)">
    <center><h2>FIND CUSTOMER</h2></center>
    <input type="number" name="customerId" placeholder="CUSTOMER ID">
        <input type="submit" value="VIEW CUSOMER">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #view-customer {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #view-customer input {
        padding: 2%;
        border: none;
    }
    #view-customer input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>