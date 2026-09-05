<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Quiz Registration</title>
<style>
body{
    font-family:Arial;
    background:#f2f2f2;
}
.container{
    width:350px;
    margin:80px auto;
    background:white;
    padding:25px;
    border-radius:10px;
    box-shadow:0 0 10px gray;
}
input, select{
    width:100%;
    padding:10px;
    margin:10px 0;
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
h2{
    text-align:center;
}
#subjectField{
    display:none;
}
</style>
</head>
<body>

<div class="container">
<h2>Create Account</h2>

<form action="RegisterServlet" method="post">

    <input type="text" name="username" placeholder="Choose a Username" required>

    <input type="password" name="password" placeholder="Choose a Password" required>

    <select name="role" id="role" onchange="toggleSubject()" required>
        <option value="">-- Select Role --</option>
        <option value="STUDENT">Student</option>
        <option value="TEACHER">Teacher</option>
    </select>

    <div id="subjectField">
        <input type="text" name="subject" placeholder="Subject you teach (e.g. Mathematics)">
    </div>

    <button type="submit">Register</button>

</form>

<p style="text-align:center;">Already have an account? <a href="login.jsp">Login</a></p>

</div>

<script>
function toggleSubject() {
    var role = document.getElementById("role").value;
    var subjectField = document.getElementById("subjectField");
    subjectField.style.display = (role === "TEACHER") ? "block" : "none";
}
</script>

</body>
</html>
