<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
    pageEncoding="UTF-8" %>
<%@ page import="com.team7.model.User" %>
=======
    pageEncoding="UTF-8"%>
<%@ page import="com.team7.model.User"%>
>>>>>>> 592fd293a5394fcb3d919e6d7477f9c830245822
<%
    User user = (User) session.getAttribute("user");

    if (user == null || !user.getRole().equalsIgnoreCase("TEACHER")) {
        response.sendRedirect("../login.jsp");
        return;
    }

    final int TOTAL_QUESTIONS = 15;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Questions</title>
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
.tracker{
    position:sticky;
    top:0;
    background:white;
    padding:12px;
    text-align:center;
    font-size:20px;
    font-weight:bold;
    border-bottom:2px solid #007bff;
    margin-bottom:20px;
    z-index:10;
}
.tracker.complete{
    color:#28a745;
}
.question-block{
    border:1px solid #ddd;
    border-radius:8px;
    padding:15px;
    margin-bottom:15px;
    background:#fafafa;
}
.question-block.done{
    border-color:#28a745;
    background:#f2fff5;
}
.question-block h4{
    margin-top:0;
}
input, select, textarea{
    width:100%;
    padding:8px;
    margin:6px 0;
    box-sizing:border-box;
}
button{
    width:100%;
    padding:14px;
    background:#ccc;
    color:white;
    border:none;
    cursor:not-allowed;
    font-size:16px;
    margin-top:10px;
}
button.enabled{
    background:#007bff;
    cursor:pointer;
}
button.enabled:hover{
    background:#0056b3;
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

    <h2>Add Questions — <%= user.getSubject() %></h2>
    <p>Enter all <%= TOTAL_QUESTIONS %> questions below. The submit button unlocks once every question is complete.</p>

    <% if ("true".equals(request.getParameter("added"))) { %>
        <p class="msg">All <%= TOTAL_QUESTIONS %> questions added successfully!</p>
    <% } %>

    <div class="tracker" id="tracker">0 / <%= TOTAL_QUESTIONS %> completed</div>

    <form action="../AddQuestionServlet" method="post" id="batchForm">

        <input type="hidden" name="totalQuestions" value="<%= TOTAL_QUESTIONS %>">

        <% for (int i = 1; i <= TOTAL_QUESTIONS; i++) { %>

            <div class="question-block" id="block_<%= i %>">
                <h4>Question <%= i %></h4>

                <textarea name="questionText_<%= i %>" rows="2" placeholder="Question text" required></textarea>

                <input type="text" name="optionA_<%= i %>" placeholder="Option A" required>
                <input type="text" name="optionB_<%= i %>" placeholder="Option B" required>
                <input type="text" name="optionC_<%= i %>" placeholder="Option C" required>
                <input type="text" name="optionD_<%= i %>" placeholder="Option D" required>

                <select name="correctOption_<%= i %>" required>
                    <option value="">-- Correct Option --</option>
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                </select>
            </div>

        <% } %>

        <button type="submit" id="submitBtn" disabled>Submit All <%= TOTAL_QUESTIONS %> Questions</button>

    </form>

    <a class="back" href="dashboard.jsp">Back to Dashboard</a>

</div>

<script>
    var TOTAL = <%= TOTAL_QUESTIONS %>;
    var form = document.getElementById("batchForm");
    var tracker = document.getElementById("tracker");
    var submitBtn = document.getElementById("submitBtn");

    function isBlockComplete(i) {
        var fields = [
            "questionText_" + i,
            "optionA_" + i,
            "optionB_" + i,
            "optionC_" + i,
            "optionD_" + i,
            "correctOption_" + i
        ];

        for (var j = 0; j < fields.length; j++) {
            var el = form.elements[fields[j]];
            if (!el || el.value.trim() === "") {
                return false;
            }
        }
        return true;
    }

    function updateTracker() {
        var completedCount = 0;

        for (var i = 1; i <= TOTAL; i++) {
            var block = document.getElementById("block_" + i);
            if (isBlockComplete(i)) {
                completedCount++;
                block.classList.add("done");
            } else {
                block.classList.remove("done");
            }
        }

        tracker.textContent = completedCount + " / " + TOTAL + " completed";

        if (completedCount === TOTAL) {
            tracker.classList.add("complete");
            submitBtn.disabled = false;
            submitBtn.classList.add("enabled");
        } else {
            tracker.classList.remove("complete");
            submitBtn.disabled = true;
            submitBtn.classList.remove("enabled");
        }
    }

    form.addEventListener("input", updateTracker);
    form.addEventListener("change", updateTracker);

    updateTracker();
</script>

</body>
</html>
