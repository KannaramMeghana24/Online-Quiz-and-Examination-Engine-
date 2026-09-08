<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
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

    List<String[]> quizzes = questionDAO.getAvailableQuizzes(); // each: {subject, quizTitle}

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
<p class="error">Please select a quiz.</p>
<% } else if ("noQuestions".equals(error)) { %>
<p class="error">That quiz has no questions yet.</p>
<% } %>
 
    <% if (quizzes.isEmpty()) { %>
 
        <p>No quizzes are available right now. Please check back later.</p>
 
    <% } else { %>
 
        <form action="../StartQuizServlet" method="post">
 
            <select name="quizChoice" required onchange="fillHidden(this)">
<option value="">-- Select a Quiz --</option>
<% for (String[] quiz : quizzes) {

                       String subject = quiz[0];

                       String title = quiz[1];

                %>
<option value="<%= subject %>|<%= title %>"><%= subject %> — <%= title %></option>
<% } %>
</select>
 
            <input type="hidden" name="subject" id="subjectField">
<input type="hidden" name="quizTitle" id="quizTitleField">
 
            <button type="submit">Start Quiz</button>
 
        </form>
 
    <% } %>
 
    <a href="history.jsp">View My Past Results</a>
 
</div>
 
<script>

    function fillHidden(select) {

        var parts = select.value.split("|");

        document.getElementById("subjectField").value = parts[0] || "";

        document.getElementById("quizTitleField").value = parts[1] || "";

    }
</script>
 
</body>
</html>

 