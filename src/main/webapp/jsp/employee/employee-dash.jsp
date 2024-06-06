<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="app.models.Branch"%>
<%@ page import="java.text.NumberFormat,java.util.Locale"%>
<%
    Branch branch  = (Branch)request.getAttribute("branch");
    Object error = request.getAttribute("error");
    if(error!=null){
        branch = new Branch();
    }
%>
<table class="Branch-table">
    <tr>
        <th>Branch ID</th>
        <td><%= branch.getBranchId() %></td>
    </tr>
    <tr>
        <th>IFSC</th>
        <td><%= branch.getIfsc() %></td>
    </tr>
    <tr>
        <th>Address</th>
        <td><%= branch.getAddress() %></td>
    </tr>
    <tr>
        <th>Branch Name</th>
        <td><%= branch.getBranchName() %></td>
    </tr>
    <tr>
        <th>Account Count</th>
        <td><%= branch.getAccountCount() %></td>
    </tr>
    <tr>
        <th>Customer Count</th>
        <td><%= branch.getCustomerCount() %></td>
    </tr>
    <tr>
        <th>Branch Worth</th>
        <td><%= NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(branch.getBranchWorth()) %></td>
    </tr>
    <tr>
        <th>Pincode</th>
        <td><%= branch.getPincode() %></td>
    </tr>
</table>    
<br>
<h3 style="color:red">
<%=error==null ? "":error.toString().toUpperCase()%>
</h3>
<br>

<style>
.Branch-table {
    border-collapse: collapse;
    width: 100%;
}

.Branch-table th, .Branch-table td {
    border: 1px solid #205a9c;
    padding: 1rem;
    background-color: bisque;
}

.Branch-table th {
    background-color: rgb(156, 185, 202);
    font-weight: bold;
    font-style: italic;
}

.Branch-table th, .Branch-table td {
    border-radius: 5px;
}

.Branch-table  {
    border: 2px transparent;
    background-color: rgb(224, 210, 147);
    border-radius: 3px;
    font-size: 16px;
    padding: 1.5%;
    outline: none;
    cursor: pointer;
}
</style>
