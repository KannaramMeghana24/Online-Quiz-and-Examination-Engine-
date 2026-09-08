<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
    pageEncoding="UTF-8" %>
=======
    pageEncoding="UTF-8"%>
>>>>>>> 592fd293a5394fcb3d919e6d7477f9c830245822
<%@ page import="com.team7.model.User"%>
<%@ page import="com.team7.model.QuizResult"%>
<%@ page import="com.team7.model.QuestionReview"%>
<%@ page import="com.team7.dao.ResultDAO"%>
<%@ page import="java.util.List"%>
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("STUDENT")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    int resultId;
    try {
        resultId = Integer.parseInt(request.getParameter("resultId"));
    } catch (Exception e) {
        response.sendRedirect("history.jsp");
        return;
    }

    ResultDAO resultDAO = new ResultDAO();

    // Ownership check: only allow reviewing a result that belongs to
    // this logged-in student, so a student can't view someone else's
    // review just by editing the resultId in the URL.
    List<QuizResult> myResults = resultDAO.getResultsByUser(user.getUserId());
    boolean owns = false;
    for (QuizResult r : myResults) {
        if (r.getResultId() == resultId) {
            owns = true;
            break;
        }
    }

    if (!owns) {
        response.sendRedirect("history.jsp");
        return;
    }

    List<QuestionReview> reviews = resultDAO.getReviewForResult(resultId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz Review</title>
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
.question-block{
    border:1px solid #ddd;
    border-radius:8px;
    padding:15px;
    margin-bottom:15px;
}
.question-block.wrong{
    border-color:#dc3545;
    background:#fff5f5;
}
.question-block.right{
    border-color:#28a745;
    background:#f2fff5;
}
.option{
    padding:6px 10px;
    margin:4px 0;
    border-radius:5px;
}
.option.correct-answer{
    background:#d4edda;
    font-weight:bold;
}
.option.your-wrong-answer{
    background:#f8d7da;
    font-weight:bold;
}
.tag{
    font-size:12px;
    font-weight:bold;
    padding:2px 8px;
    border-radius:10px;
    margin-left:8px;
}
.tag.correct{
    background:#28a745;
    color:white;
}
.tag.incorrect{
    background:#dc3545;
    color:white;
}
a.back{
    display:block;
    margin-top:15px;
}
</style>
</head>
<body>

<div class="container">

    <h2>Quiz Review</h2>

    <% int i = 1;
       for (QuestionReview r : reviews) {
           boolean correct = r.isCorrect();
    %>

        <div class="question-block <%= correct ? "right" : "wrong" %>">

            <p><b><%= i %>. <%= r.getQuestionText() %></b>
                <span class="tag <%= correct ? "correct" : "incorrect" %>"><%= correct ? "Correct" : "Incorrect" %></span>
            </p>

            <%
                String[] optionLetters = {"A", "B", "C", "D"};
                String[] optionTexts = {r.getOptionA(), r.getOptionB(), r.getOptionC(), r.getOptionD()};

                for (int j = 0; j < 4; j++) {
                    String letter = optionLetters[j];
                    String cssClass = "option";

                    if (letter.equalsIgnoreCase(r.getCorrectOption())) {
                        cssClass += " correct-answer";
                    } else if (letter.equalsIgnoreCase(r.getSelectedOption())) {
                        cssClass += " your-wrong-answer";
                    }
            %>
                    <div class="<%= cssClass %>">
                        <%= letter %>. <%= optionTexts[j] %>
                        <% if (letter.equalsIgnoreCase(r.getCorrectOption())) { %>
                            (Correct Answer)
                        <% } else if (letter.equalsIgnoreCase(r.getSelectedOption())) { %>
                            (Your Answer)
                        <% } %>
                    </div>
            <% } %>

            <% if (r.getSelectedOption() == null) { %>
                <p><i>You did not answer this question.</i></p>
            <% } %>

        </div>

    <% i++; } %>

    <a class="back" href="history.jsp">Back to History</a>

</div>

</body>
</html>
