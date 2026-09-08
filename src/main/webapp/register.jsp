<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Registration</title>
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

input{

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
</style>
</head>
<body>
 
<div class="container">
<h2>Student Registration</h2>
 
<form action="RegisterServlet" method="post">
 
    <!-- Only students self-register; teacher accounts are created by an admin -->
<input type="hidden" name="role" value="STUDENT">
 
    <input type="text" name="username" placeholder="Enrollment No. (e.g. 26001)"

           pattern="26[0-9]{3}" maxlength="5" title="Must start with 26 followed by 3 digits (e.g. 26001)" required>
 
    <input type="password" name="password" placeholder="Choose a Password" required>
 
    <button type="submit">Register</button>
 
</form>
 
<p style="text-align:center;">Already have an account? <a href="login.jsp">Login</a></p>
 
</div>
 
</body>
</html>

 