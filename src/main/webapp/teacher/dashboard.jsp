<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.QuizResult"%>
<%@ page import="com.team7.dao.ResultDAO"%>
<%@ page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    String subject = user.getSubject();

    ResultDAO resultDAO = new ResultDAO();
    List<QuizResult> results = resultDAO.getResultsBySubject(subject);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Dashboard</title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
    margin:0;
    padding:30px;
}
.container{
    max-width:800px;
    margin:0 auto;
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
}
table{
    width:100%;
    border-collapse:collapse;
    margin-top:15px;
}
th, td{
    padding:10px;
    border:1px solid #ddd;
    text-align:left;
}
th{
    background:#007bff;
    color:white;
}
tr:nth-child(even){
    background:#f9f9f9;
}
a.logout{
    float:right;
}
</style>
</head>
<body>

<div class="container">

    <a class="logout" href="../LogoutServlet">Logout</a>

    <h2>Welcome, <%= user.getUsername() %></h2>
    <p>Subject: <b><%= subject %></b></p>

    <h3>Student Results</h3>

    <% if (results.isEmpty()) { %>

        <p>No students have taken a quiz in this subject yet.</p>

    <% } else { %>

        <table>
            <tr>
                <th>Student</th>
                <th>Score</th>
                <th>Total Questions</th>
                <th>Completed At</th>
            </tr>

            <% for (QuizResult r : results) { %>
                <tr>
                    <td><%= r.getStudentUsername() %></td>
                    <td><%= r.getScore() %></td>
                    <td><%= r.getTotalQuestions() %></td>
                    <td><%= r.getCompletedAt() %></td>
                </tr>
            <% } %>

        </table>

    <% } %>

</div>

</body>
</html>
