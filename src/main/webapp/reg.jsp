<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeQuest - Student Registration</title>

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

    .register-box {
        width: 90%;
        max-width: 420px;
        background: #1c1d32;
        padding: 35px;
        border-radius: 16px;
        box-shadow: 0 0 25px rgba(139, 92, 246, 0.2);
    }

    h1 {
        color: #a78bfa;
        text-align: center;
    }

    p {
        color: #c9c9dc;
        text-align: center;
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
        margin-top: 20px;
        text-align: center;
    }
</style>
</head>

<body>

<div class="register-box">
    <h1>Join CodeQuest</h1>
    <p>Create your account and begin your coding journey!</p>

    <form action="Reg" method="post">

        <label for="username">Username</label>
        <input type="text" id="username" name="username"
               maxlength="50" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email"
               maxlength="100" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password"
               minlength="8" required>

        <button type="submit">Create Account</button>
    </form>

    <div class="bottom">
        Already registered? <a href="login.jsp">Login here</a>
    </div>

    <div class="bottom">
        <a href="index.html">Back to Home</a>
    </div>
</div>

</body>
</html>