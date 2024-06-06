<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.ArrayList"%>
<%@ page import="app.models.Account"%>
<%
Object errorAccounts = request.getAttribute("error");
Long primaryAccountNumber=(Long)request.getAttribute("primaryAccountNumber");
List<Account> accounts = (List<Account>)request.getAttribute("accounts");
%>
<div class="accounts">
    <% if (errorAccounts == null) {
        for (Account account : accounts) { %>
        <%request.setAttribute("account",account); %>
            <jsp:include page="customer-account.jsp">
                <jsp:param value="<%=primaryAccountNumber%>" name="primaryAccountNumber" />
            </jsp:include>
    <%  }
    } else { %>
        <%=errorAccounts%>
    <% } %>
</div>