<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="admin/view-branch" method="post" id="view-branch" onsubmit="submitForm(event)">
    <center><h2>FIND BRANCH</h2></center>
    <input type="number" name="branchId" placeholder="Branch ID">
        <input type="submit" value="VIEW BRANCH">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #view-branch {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #view-branch input {
        padding: 2%;
        border: none;
    }
    #view-branch input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>