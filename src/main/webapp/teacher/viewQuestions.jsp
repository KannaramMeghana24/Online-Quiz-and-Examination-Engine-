<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.Question"%>
<%@ page import="com.team7.dao.QuestionDAO"%>
<%@ page import="java.util.List"%>
<%

    User user = (User) session.getAttribute("user");
 
    if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {

        response.sendRedirect("../login.jsp");

        return;

    }
 
    QuestionDAO questionDAO = new QuestionDAO();

    List<String> quizTitles = questionDAO.getQuizTitlesBySubject(user.getSubject());
 
    String selectedTitle = request.getParameter("quizTitle");

    List<Question> questions = null;
 
    if (selectedTitle != null && !selectedTitle.isBlank()) {

        questions = questionDAO.getQuestionsByQuizTitle(user.getSubject(), selectedTitle);

    }

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Questions</title>
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

select, button{

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

.question-block{

    border:1px solid #ddd;

    border-radius:8px;

    padding:15px;

    margin-bottom:12px;

}

.question-block p{

    font-weight:bold;

    margin-top:0;

}

.question-block .opt{

    margin:2px 0;

}

.question-block .correct{

    color:#28a745;

    font-weight:bold;

}

a.edit-link{

    display:inline-block;

    margin-top:8px;

    color:#007bff;

    text-decoration:none;

    font-weight:bold;

}

a.edit-link:hover{

    text-decoration:underline;

}

.msg{

    color:green;

    text-align:center;

}

a.back{

    display:block;

    text-align:center;

    margin-top:15px;

}
</style>
</head>
<body>
 
<div class="container">
 
    <h2>My Questions — <%= user.getSubject() %></h2>
 
    <% if ("true".equals(request.getParameter("updated"))) { %>
<p class="msg">Question updated successfully!</p>
<% } %>
 
    <% if (quizTitles.isEmpty()) { %>
 
        <p>You haven't added any questions yet.</p>
 
    <% } else { %>
 
        <form method="get" action="viewQuestions.jsp">
<select name="quizTitle" onchange="this.form.submit()">
<option value="">-- Select a Quiz --</option>
<% for (String title : quizTitles) { %>
<option value="<%= title %>" <%= title.equals(selectedTitle) ? "selected" : "" %>><%= title %></option>
<% } %>
</select>
</form>
 
        <% if (questions != null) {

               int i = 1;

               for (Question q : questions) { %>
 
            <div class="question-block">
<p><%= i %>. <%= q.getQuestionText() %></p>
<div class="opt">A. <%= q.getOptionA() %></div>
<div class="opt">B. <%= q.getOptionB() %></div>
<div class="opt">C. <%= q.getOptionC() %></div>
<div class="opt">D. <%= q.getOptionD() %></div>
<div class="correct">Correct: <%= q.getCorrectOption() %></div>
 
                <a class="edit-link" href="editQuestion.jsp?questionId=<%= q.getQuestionId() %>">Edit</a>
</div>
 
        <%     i++; }

           } %>
 
    <% } %>
 
    <a class="back" href="dashboard.jsp">Back to Dashboard</a>
 
</div>
 
</body>
</html>

 