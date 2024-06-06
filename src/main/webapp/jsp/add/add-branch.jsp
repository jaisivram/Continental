<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="admin/add-branch" method="post" id="add-branch" onsubmit="submitForm(event)">
    <center><h2>ADD BRANCH</h2></center>
    <input type="text" name="branchName" placeholder="Branch Name" required>
    <input type="text" name="branchAddress" placeholder="Address" required>
        <input type="text" name="ifsc" placeholder="IFSC" required>
    <input type="number" name="pincode" placeholder="Pincode" required>
    <input type="submit" value="Add BRANCH">
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #add-branch {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #add-branch input {
        padding: 2%;
        border: none;
    }
    #add-branch input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>