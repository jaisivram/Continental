<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="app.models.*,java.util.List"%>
<%
List<Transaction> transactions = (List<Transaction>)request.getAttribute("transactions");
List<Long> accs = (List<Long>)request.getAttribute("accounts");
List<Object> timeframes = (List<Object>) request.getAttribute("timeframes");
Long primary = (Long)request.getAttribute("currentAccountNumber");
Long pages = (Long)request.getAttribute("pages");
int currentPage = (Integer) request.getAttribute("currentPage");
Object error = request.getAttribute("error");
%>
<style>
.customer-statement {
    display: flex;
    flex-direction: column;
    align-items: center;
    font-family: Arial, sans-serif;
    color: #333;
    margin-top: 20px;
}

form {
    margin-bottom: 20px;
}

form select {
    padding: 8px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    margin-right: 10px;
}

table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ccc;
}

th, td {
    padding: 10px;
    border: 1px solid #ccc;
}

th {
    background-color: cornflowerblue;
    color: white;
}

tbody tr:nth-child(even) {
    background-color: #f2f2f2;
}
tbody tr:nth-child(odd) {
    background-color: bisque;
}
</style>
<%=error==null? "":error.toString()%>
<div class="customer-statement">
    <form id="statement" action="customer/statement" method="post">
        <select name="sourceAccountNumber" onchange="load_statement(true,'statement')">
            <%="<option value='"+primary+"' selected>"+primary+"</option>"%>
            <%for (Long num : accs)  { if(!num.equals(primary)) {%>
            <%="<option value='"+num+"'>"+num+"</option>" %>
            <%}} %>    
        </select>
        <select name="timeFrame" onchange="load_statement(true,'statement')">
            <%for (int i=0;i<timeframes.size();i+=2) { %>
            <%if(timeframes.get(i).toString().equals("selected")) {%>
            <%="<option value='"+timeframes.get(i+2)+"' selected>"+timeframes.get(i+1)+"</option>"%>
            <%;i++;} else {%>
            <%="<option value='"+timeframes.get(i+1)+"'>"+timeframes.get(i)+"</option>"%>
            <%}} %>
        </select>
        <select name="pageNumber" onchange="load_statement(false,'statement')">
            <%for (int i=1;i<=pages;i++) { if(i==currentPage) {%>
            <%="<option value='"+currentPage+"' selected>"+currentPage+"</option>"%>
            <%} else { %>
            <%="<option value='"+i+"'>"+i+"</option>"%>
            <%}}%>
        </select>    
        </form>

    <table>
        <thead>
            <tr>
                <th>S.No</th>
                <th>TransactionId</th>
                <th>Source-Account</th>
                <th>Transaction-Account</th>
                <th>Scope</th>
                <th>Date</th>
                <th>Credit</th>
                <th>Debit</th>
                <th>Balance</th>
            </tr>
        </thead>
        <tbody>
            <% for (int i=0; i<transactions.size(); i++) { 
                Transaction transaction = transactions.get(i);
            %>
            <tr>
                <td><%= (1 + i + ((currentPage-1)*10)) %></td>
                <td><%= transaction.getTransactionId() %></td>
                <td><%= transaction.getSourceAccountNumber() %></td>
                <td><%= transaction.getTransactionAccountNumber() %></td>
                <td><%= transaction.getScope() %></td>
                <td><%= new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date(transaction.getTimeStamp())) %></td>
                <td><%= transaction.getCredit() %></td>
                <td><%= transaction.getDebit() %></td>
                <td><%= transaction.getBalance() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>
