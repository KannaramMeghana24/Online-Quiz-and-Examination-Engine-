<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%

    User user = (User) session.getAttribute("user");
 
    if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {

        response.sendRedirect("../login.jsp");

        return;

    }
 
    Object scoreObj = session.getAttribute("lastScore");

    Object totalObj = session.getAttribute("lastTotal");

    Object subjectObj = session.getAttribute("lastSubject");

    Object quizTitleObj = session.getAttribute("lastQuizTitle");
 
    if (scoreObj == null || totalObj == null) {

        response.sendRedirect("home.jsp");

        return;

    }
 
    int score = (Integer) scoreObj;

    int total = (Integer) totalObj;

    String subject = (String) subjectObj;

    String quizTitle = (String) quizTitleObj;
 
    // Clear so refreshing this page doesn't keep re-showing a stale result

    session.removeAttribute("lastScore");

    session.removeAttribute("lastTotal");

    session.removeAttribute("lastSubject");

    session.removeAttribute("lastQuizTitle");
 
    double percent = total > 0 ? (score * 100.0 / total) : 0;

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz Result</title>
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

.score{

    font-size:40px;

    font-weight:bold;

    color:#007bff;

    margin:15px 0;

}

a{

    display:block;

    margin-top:15px;

}
</style>
</head>
<body>
 
<div class="container">
 
    <h2><%= quizTitle %></h2>
<p style="color:#666;margin-top:-10px;"><%= subject %></p>
 
    <div class="score"><%= score %> / <%= total %></div>
 
    <p><%= String.format("%.0f", percent) %>%</p>
 
    <a href="home.jsp">Take Another Quiz</a>
<a href="history.jsp">View My History</a>
 
</div>
 
</body>
</html>

 