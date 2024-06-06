<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
        <%
    Object error = request.getAttribute("error");
    Object isAdmin = request.getAttribute("isAdmin");
    %>
<form action='employee/view-account' method='post' id='view-account' onsubmit='submitForm(event)'>
    <center><h2>FIND ACCOUNT</h2></center>
    <input type='number' name='accountNumber' placeholder='Account Number'>
    <input type='number' name='customerId' placeholder='Customer ID'>
   <%-- <%=isAdmin != null ? "<input type='number' name='customerId' placeholder='Customer ID'>" : "" %>  --%>
        <input type='submit' value='VIEW ACCOUNT'>
        <center><H2 style='color:red;'><%=error==null ? "":error.toString() %></H2></center>
</form> 
<style>
    #view-account {
        display: flex;
        flex-direction: column;
        border: 1px transparent;
        background-color: rgb(156, 185, 202);
        padding: 1rem;
        gap: 1rem;
    }
    #view-account input {
        padding: 2%;
        border: none;
    }
    #view-account input[type='submit']{
        background-color: greenyellow;
        cursor: pointer;
    }
</style>