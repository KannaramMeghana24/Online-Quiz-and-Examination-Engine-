<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Question</title>
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

    <h2>Add Question — <%= user.getSubject() %></h2>

    <% if ("true".equals(request.getParameter("added"))) { %>
        <p class="msg">Question added successfully!</p>
    <% } %>

    <form action="../AddQuestionServlet" method="post">

        <textarea name="questionText" rows="3" placeholder="Question text" required></textarea>

        <input type="text" name="optionA" placeholder="Option A" required>
        <input type="text" name="optionB" placeholder="Option B" required>
        <input type="text" name="optionC" placeholder="Option C" required>
        <input type="text" name="optionD" placeholder="Option D" required>

        <select name="correctOption" required>
            <option value="">-- Correct Option --</option>
            <option value="A">A</option>
            <option value="B">B</option>
            <option value="C">C</option>
            <option value="D">D</option>
        </select>

        <button type="submit">Add Question</button>

    </form>

    <a href="dashboard.jsp">Back to Dashboard</a>

</div>

</body>
</html>
