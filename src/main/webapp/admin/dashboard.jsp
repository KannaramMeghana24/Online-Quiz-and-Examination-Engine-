<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
}
.container{
    width:400px;
    margin:80px auto;
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
    text-align:center;
}
a.button{
    display:block;
    padding:12px;
    margin-top:15px;
    background:#007bff;
    color:white;
    text-decoration:none;
    border-radius:5px;
}
a.button:hover{
    background:#0056b3;
}
a.logout{
    display:block;
    margin-top:20px;
}
</style>
</head>
<body>

<div class="container">

    <h2>Welcome, <%= user.getUsername() %></h2>

    <a class="button" href="addTeacher.jsp">Add Teacher</a>

    <a class="logout" href="../LogoutServlet">Logout</a>

</div>

</body>
</html>
