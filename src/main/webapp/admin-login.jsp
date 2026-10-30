<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeQuest - Admin Login</title>

<style>
* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #10111f;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}

.box {
    width: 90%;
    max-width: 420px;
    padding: 35px;
    background: #1c1d32;
    border-radius: 16px;
    box-shadow: 0 0 25px rgba(139, 92, 246, 0.2);
}

h1 {
    text-align: center;
    color: #a78bfa;
}

p {
    text-align: center;
    color: #c9c9dc;
}

label {
    display: block;
    margin: 18px 0 7px;
}

input {
    width: 100%;
    padding: 12px;
    background: #10111f;
    color: white;
    border: 1px solid #454562;
    border-radius: 8px;
}

button {
    width: 100%;
    padding: 13px;
    margin-top: 25px;
    background: #8b5cf6;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
}

button:hover {
    background: #6d28d9;
}

a {
    color: #c4b5fd;
    text-decoration: none;
}

.bottom {
    text-align: center;
    margin-top: 20px;
}

.error {
    color: #fca5a5;
    text-align: center;
    margin-top: 15px;
}
</style>
</head>

<body>
<div class="box">

    <h1>⚙ Admin Login</h1>
    <p>Welcome back, Administrator!</p>

    <form action="ALogin" method="post">
        <label for="username">Admin Username</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Login as Admin</button>
    </form>

    <%
        String error = request.getParameter("error");

        if ("invalid".equals(error)) {
    %>
        <div class="error">Invalid admin username or password.</div>
    <%
        } else if ("empty".equals(error)) {
    %>
        <div class="error">Please fill in both fields.</div>
    <%
        }
    %>

    <div class="bottom">
        <a href="index.html">Back to Home</a>
    </div>

</div>
</body>
</html>
