<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="app.models.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
    Object error = request.getAttribute("error");
    Long pageNumber = Long.parseLong(request.getAttribute("pageNumber").toString());
    Long pages = (Long) request.getAttribute("pages");
%>
<form id="statementForm" action="employee/view-statement" method="post" onsubmit="submitForm(event)">
    <select name="pageNumber" onchange="document.getElementById('statementForm').dispatchEvent(new Event('submit'));">
        <% for (int i = 1; i <= pages; i++) { %>
            <% if (i == pageNumber) { %>
                <option value="<%= i %>" selected><%= i %></option>
            <% } else { %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        <% } %>
    </select>
    <input type="text" name="transactionId" class="hidden" value=<%=request.getAttribute("transactionId").toString()%>>
    <input type="text" name="customerId" class="hidden" value=<%=request.getAttribute("customerId").toString()%>>
    <input type="text" name="sourceAccountNumber" class="hidden" value=<%=request.getAttribute("sourceAccountNumber").toString()%>>
	<input type="text" name="fromDate" class="hidden" value=<%=request.getAttribute("fromDate").toString()%>>
	<input type="text" name="toDate" class="hidden" value=<%=request.getAttribute("toDate").toString()%>>    
</form>
    <table>
        <thead>
            <tr>
                <th>S.No</th>
                <th>TransactionId</th>
                <th>CustomerId</th>
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
                <td><%= (1 + i + ((pageNumber-1)*10)) %></td>
                <td><%= transaction.getTransactionId() %></td>
                <td><%= transaction.getCustomerId() %></td>
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
<style>
    button {
        padding: 0.5rem;
        border: none;
    }
    button {
        background-color: rgb(62, 212, 107);
        outline: none;
        cursor: pointer;
    }
table {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #ccc;
}

th, td {
    padding: 10px;
    border: 1px solid #ccc;
    height: 2vh;
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
