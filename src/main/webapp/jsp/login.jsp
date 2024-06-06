<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // Get the session object
    if(request.getSession().getAttribute("userId")!=null){
        response.sendRedirect("/Continental/app/home");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href=<%=request.getContextPath()+"/css/style.css"%>>
</head>
<body>
    <div class="page">
        <div class="top-nav">

        </div>
        <form action="<%=request.getContextPath()+"/app/login"%>" id="login-form" method="post" >
            <center><h2>LOGIN</h2></center>
            <input type="number" placeholder="User ID" name="userId" required min="1" max="10000000">
            <input type="password" placeholder="Password" name="password" required>
            <input type="submit" value="LOGIN">
            <center>
            <div class="error" style="color:red;font-style:bold">
            	<%=request.getAttribute("login-error")==null? "":request.getAttribute("login-error").toString().toUpperCase()%>
            </div>
            </center>
        </form>
    </div>
    <script src=<%=request.getContextPath() + "/js/script.js"%>></script>
</body>
</html>