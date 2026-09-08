<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.QuizResult"%>
<%@ page import="com.team7.dao.ResultDAO"%>
<%@ page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    ResultDAO resultDAO = new ResultDAO();
    List<QuizResult> results = resultDAO.getResultsByUser(user.getUserId());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Quiz History</title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
    margin:0;
    padding:30px;
}
.container{
    max-width:650px;
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
a.review-link{
    color:#007bff;
    font-weight:bold;
    text-decoration:none;
}
a.review-link:hover{
    text-decoration:underline;
}
a.back{
    display:block;
    margin-top:15px;
}
</style>
</head>
<body>

<div class="container">

    <h2>My Quiz History</h2>

    <% if (results.isEmpty()) { %>

        <p>You haven't taken any quizzes yet.</p>

    <% } else { %>

        <table>
            <tr>
                <th>Subject</th>
                <th>Score</th>
                <th>Total</th>
                <th>Date</th>
                <th></th>
            </tr>

            <% for (QuizResult r : results) { %>
                <tr>
                    <td><%= r.getSubject() %></td>
                    <td><%= r.getScore() %></td>
                    <td><%= r.getTotalQuestions() %></td>
                    <td><%= r.getCompletedAt() %></td>
                    <td><a class="review-link" href="review.jsp?resultId=<%= r.getResultId() %>">Review</a></td>
                </tr>
            <% } %>

        </table>

    <% } %>

    <a class="back" href="home.jsp">Back to Home</a>

</div>

</body>
</html>
