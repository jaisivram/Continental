<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
    Object error = request.getAttribute("error");
    
    %>
<form action="employee/view-statement" method="post" id="view-statement" onsubmit="submitForm(event)">
    <center><h2>FIND TRANSACTION</h2></center>
    <input type="number" name="customerId" placeholder="Customer ID">
	    <input type="number" name="sourceAccountNumber" placeholder="Source Account Number">
	        <input type="text" name="fromDate" placeholder="From DD/MM/YYYY" >
	        <input type="hidden" name="transactionId" placeholder="TRANSCATION ID">
	        <input type="text" name="toDate" placeholder="To DD/MM/YYYY" >
	        <input type="text" name="pageNumber" class="hidden" value="1">
        <input type="submit" value="VIEW TRANSACTION">
</form> 
<br>
<form action="employee/view-statement" method="post" id="view-statement2" onsubmit="submitForm(event)">
    <input type="hidden" name="customerId"  type="hidden" placeholder="Customer ID">
	    <input type="hidden" name="sourceAccountNumber" placeholder="Source Account Number">
	        <input type="text" name="transactionId" placeholder="TRANSCATION ID">
	        <input type="hidden" name="fromDate" value="" placeholder="From DD/MM/YYYY" >
	        <input type="hidden" name="toDate" value="" placeholder="To DD/MM/YYYY" >
	        <input type="text" name="pageNumber" class="hidden" value="1">
        <input type="submit" value="VIEW TRANSACTION">
</form> 
        <center><H2 style="color:red;"><%=error==null ? "":error.toString() %></H2></center>
<style>
    #view-statement,#view-statement2 {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #view-statement input, #view-statement2 input {
        padding: 2%;
        border: none;
    }
    #view-statement input[type="submit"],#view-statement2 input[type="submit"]{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>