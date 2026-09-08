<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.Question"%>
<%@ page import="com.team7.dao.QuestionDAO"%>
<%

    User user = (User) session.getAttribute("user");
 
    if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {

        response.sendRedirect("../login.jsp");

        return;

    }
 
    int questionId;

    try {

        questionId = Integer.parseInt(request.getParameter("questionId"));

    } catch (Exception e) {

        response.sendRedirect("viewQuestions.jsp");

        return;

    }
 
    QuestionDAO questionDAO = new QuestionDAO();

    Question q = questionDAO.getQuestionById(questionId);
 
    // Ownership check: can only edit a question belonging to this teacher's subject

    if (q == null || !q.getSubject().equalsIgnoreCase(user.getSubject())) {

        response.sendRedirect("viewQuestions.jsp");

        return;

    }

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Question</title>
<style>

body{

    font-family:Arial;

    background:#f2f2f2;

}

.container{

    width:450px;

    margin:60px auto;

    background:white;

    padding:25px;

    border-radius:10px;

    box-shadow:0 0 10px gray;

}

input, textarea, select{

    width:100%;

    padding:10px;

    margin:8px 0;

    box-sizing:border-box;

}

button{

    width:100%;

    padding:10px;

    background:#007bff;

    color:white;

    border:none;

    cursor:pointer;

}

button:hover{

    background:#0056b3;

}

a.back{

    display:block;

    text-align:center;

    margin-top:10px;

}
</style>
</head>
<body>
 
<div class="container">
 
    <h2>Edit Question — <%= q.getQuizTitle() %></h2>
 
    <form action="../EditQuestionServlet" method="post">
 
        <input type="hidden" name="questionId" value="<%= q.getQuestionId() %>">
 
        <textarea name="questionText" rows="3" required><%= q.getQuestionText() %></textarea>
 
        <input type="text" name="optionA" value="<%= q.getOptionA() %>" required>
<input type="text" name="optionB" value="<%= q.getOptionB() %>" required>
<input type="text" name="optionC" value="<%= q.getOptionC() %>" required>
<input type="text" name="optionD" value="<%= q.getOptionD() %>" required>
 
        <select name="correctOption" required>
<option value="A" <%= "A".equalsIgnoreCase(q.getCorrectOption()) ? "selected" : "" %>>A</option>
<option value="B" <%= "B".equalsIgnoreCase(q.getCorrectOption()) ? "selected" : "" %>>B</option>
<option value="C" <%= "C".equalsIgnoreCase(q.getCorrectOption()) ? "selected" : "" %>>C</option>
<option value="D" <%= "D".equalsIgnoreCase(q.getCorrectOption()) ? "selected" : "" %>>D</option>
</select>
 
        <button type="submit">Save Changes</button>
 
    </form>
 
    <a class="back" href="viewQuestions.jsp?quizTitle=<%= q.getQuizTitle() %>">Cancel</a>
 
</div>
 
</body>
</html>

 