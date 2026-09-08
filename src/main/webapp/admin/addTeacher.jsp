<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.team7.model.User" %>
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
<title>Add Teacher</title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
}
.container{
    width:400px;
    margin:60px auto;
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
}
input, button{
    width:100%;
    padding:10px;
    margin:8px 0;
    box-sizing:border-box;
}
button{
    background:#007bff;
    color:white;
    border:none;
    cursor:pointer;
}
button:hover{
    background:#0056b3;
}
.msg{
    color:green;
    text-align:center;
}
a{
    display:block;
    text-align:center;
    margin-top:10px;
}
</style>
</head>
<body>

<div class="container">

    <h2>Add Teacher</h2>

    <% if ("true".equals(request.getParameter("added"))) { %>
        <p class="msg">Teacher account created successfully!</p>
    <% } %>

    <form action="../AddTeacherServlet" method="post">

        <input type="text" name="username" placeholder="Username" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="subject" placeholder="Subject (e.g. Mathematics)" required>

        <button type="submit">Create Teacher Account</button>

    </form>

    <a href="dashboard.jsp">Back to Dashboard</a>

</div>

</body>
</html>
