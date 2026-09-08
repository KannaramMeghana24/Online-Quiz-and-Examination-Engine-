<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
    pageEncoding="UTF-8" %>
=======
    pageEncoding="UTF-8"%>
>>>>>>> 592fd293a5394fcb3d919e6d7477f9c830245822
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.dao.QuestionDAO"%>
<%@ page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    QuestionDAO questionDAO = new QuestionDAO();
    List<String> subjects = questionDAO.getDistinctSubjects();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Home</title>
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
}
select, button{
    width:100%;
    padding:10px;
    margin:10px 0;
    box-sizing:border-box;
}
button{
    background:#28a745;
    color:white;
    border:none;
    cursor:pointer;
}
button:hover{
    background:#1e7e34;
}
.error{
    color:red;
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

    <h2>Welcome, <%= user.getUsername() %></h2>

    <% String error = request.getParameter("error");
       if ("noSubject".equals(error)) { %>
        <p class="error">Please select a subject.</p>
    <% } else if ("noQuestions".equals(error)) { %>
        <p class="error">No questions available for that subject yet.</p>
    <% } %>

    <% if (subjects.isEmpty()) { %>

        <p>No quizzes are available right now. Please check back later.</p>

    <% } else { %>

        <form action="../StartQuizServlet" method="post">

            <select name="subject" required>
                <option value="">-- Select Subject --</option>
                <% for (String s : subjects) { %>
                    <option value="<%= s %>"><%= s %></option>
                <% } %>
            </select>

            <button type="submit">Start Quiz</button>

        </form>

    <% } %>

    <a href="history.jsp">View My Past Results</a>

</div>

</body>
</html>
