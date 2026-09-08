<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.QuizSession"%>
<%@ page import="com.team7.model.Question"%>
<%@ page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");
    QuizSession quizSession = (QuizSession) session.getAttribute("quizSession");

    if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    if (quizSession == null) {
        response.sendRedirect("home.jsp");
        return;
    }

    List<Question> questions = quizSession.getQuestions();
    int secondsRemaining = quizSession.getSecondsRemaining();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz - <%= quizSession.getSubject() %></title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
}
.container{
    max-width:600px;
    margin:40px auto;
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
}
#timer{
    text-align:center;
    font-size:22px;
    font-weight:bold;
    color:#c0392b;
    margin-bottom:15px;
}
.question{
    margin-bottom:20px;
    padding-bottom:15px;
    border-bottom:1px solid #ddd;
}
.question p{
    font-weight:bold;
}
label{
    display:block;
    margin:5px 0;
}
button{
    width:100%;
    padding:12px;
    background:#007bff;
    color:white;
    border:none;
    cursor:pointer;
    font-size:16px;
}
button:hover{
    background:#0056b3;
}
</style>
</head>
<body>

<div class="container">

    <h2><%= quizSession.getSubject() %> Quiz</h2>

    <div id="timer">Time Left: <span id="timeDisplay"></span></div>

    <form id="quizForm" action="../SubmitQuizServlet" method="post">

        <% int i = 1;
           for (Question q : questions) { %>

            <div class="question">
                <p><%= i %>. <%= q.getQuestionText() %></p>

                <label><input type="radio" name="q_<%= q.getQuestionId() %>" value="A" required> <%= q.getOptionA() %></label>
                <label><input type="radio" name="q_<%= q.getQuestionId() %>" value="B"> <%= q.getOptionB() %></label>
                <label><input type="radio" name="q_<%= q.getQuestionId() %>" value="C"> <%= q.getOptionC() %></label>
                <label><input type="radio" name="q_<%= q.getQuestionId() %>" value="D"> <%= q.getOptionD() %></label>
            </div>

        <% i++; } %>

        <button type="submit">Submit Quiz</button>

    </form>

</div>

<script src="../js/timer.js"></script>
<script>
    // secondsRemaining comes from the server (QuizSession), not a
    // client-guessed value, so a refreshed page still shows correct time.
    startTimer(<%= secondsRemaining %>, "timeDisplay", "quizForm");
</script>

</body>
</html>
