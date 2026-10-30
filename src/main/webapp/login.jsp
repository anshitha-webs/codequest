<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeQuest - Student Login</title>

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

    .login-box {
        width: 90%;
        max-width: 420px;
        background: #1c1d32;
        padding: 35px;
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
        margin-top: 18px;
        margin-bottom: 7px;
    }

    input {
        width: 100%;
        padding: 12px;
        border: 1px solid #454562;
        border-radius: 8px;
        background: #10111f;
        color: white;
    }

    button {
        width: 100%;
        padding: 13px;
        margin-top: 25px;
        border: none;
        border-radius: 8px;
        background: #8b5cf6;
        color: white;
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

<div class="login-box">
    <h1>Welcome Back!</h1>
    <p>Log in to continue your coding quest.</p>

    <form action="Login" method="post">

        <label for="username">Username or Email</label>
        <input type="text" id="username" name="username"
               maxlength="100" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Login</button>
    </form>

    <%
        String error = request.getParameter("error");
        String logout = request.getParameter("logout");

        if ("invalid".equals(error)) {
    %>
        <div class="error">Invalid username/email or password.</div>
    <%
        } else if ("empty".equals(error)) {
    %>
        <div class="error">Please enter your username and password.</div>
    <%
        }

        if ("success".equals(logout)) {
    %>
        <p>You have logged out successfully.</p>
    <%
        }
    %>

    <div class="bottom">
        New to CodeQuest? <a href="reg.jsp">Create an account</a>
    </div>

    <div class="bottom">
        <a href="index.html">Back to Home</a>
    </div>
</div>

</body>
</html>