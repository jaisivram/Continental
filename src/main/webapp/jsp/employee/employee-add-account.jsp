<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="admin/add-account" method="post" id="add-account" onsubmit="SubmitForm(event)">
    <center><h2>ADD ACCOUNT</h2></center>
    <input type="number" name="customerId" placeholder="Customer ID" required>
    <input type="submit" value="Add ACCOUNT">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #add-account {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #add-account input {
        padding: 2%;
        border: none;
    }
    #add-account input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>