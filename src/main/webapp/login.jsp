<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Online Quiz Login</title>

<style>

body{

font-family:Arial;

background:#f2f2f2;

}

.container{

width:350px;

margin:100px auto;

background:white;

padding:25px;

border-radius:10px;

box-shadow:0 0 10px gray;

}

input{

width:100%;

padding:10px;

margin:10px 0;

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

<h2>Online Quiz Login</h2>

<form action="LoginServlet" method="post">

<input type="text"

name="username"

placeholder="Enter Username"

required>

<input type="password"

name="password"

placeholder="Enter Password"

required>

<button type="submit">

Login

</button>

</form>

</div>

</body>

</html>