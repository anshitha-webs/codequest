<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Mission - CodeQuest</title>

<style>
*{box-sizing:border-box}
body{margin:0;padding:25px;font-family:Arial,sans-serif;background:#10111f;color:white}
.container{max-width:700px;margin:25px auto;background:#1c1d32;padding:30px;border-radius:15px}
h1{color:#a78bfa}
label{display:block;margin-top:16px;margin-bottom:7px}
input,textarea,select{width:100%;padding:12px;background:#10111f;color:white;border:1px solid #454562;border-radius:8px}
textarea{min-height:110px;resize:vertical}
button{margin-top:25px;padding:13px 20px;background:#8b5cf6;color:white;border:0;border-radius:8px;cursor:pointer;font-size:16px}
button:hover{background:#6d28d9}
a{color:#c4b5fd;text-decoration:none}
.back{display:inline-block;margin-left:12px}
</style>
</head>

<body>
<div class="container">

<h1>➕ Add a Mission</h1>
<p>Create a multiple-choice programming question for students.</p>

<form action="AddMission" method="post">

    <label for="title">Mission Title</label>
    <input type="text" id="title" name="title" maxlength="100" required>

    <label for="question">Question</label>
    <textarea id="question" name="question" required></textarea>

    <label for="option1">Option 1</label>
    <input type="text" id="option1" name="option1" maxlength="255" required>

    <label for="option2">Option 2</label>
    <input type="text" id="option2" name="option2" maxlength="255" required>

    <label for="option3">Option 3</label>
    <input type="text" id="option3" name="option3" maxlength="255" required>

    <label for="option4">Option 4</label>
    <input type="text" id="option4" name="option4" maxlength="255" required>

    <label for="correctAnswer">Correct Option</label>
    <select id="correctAnswer" name="correctAnswer" required>
        <option value="">Choose the correct option</option>
        <option value="1">Option 1</option>
        <option value="2">Option 2</option>
        <option value="3">Option 3</option>
        <option value="4">Option 4</option>
    </select>

    <label for="xp">XP Reward</label>
    <input type="number" id="xp" name="xp" min="1" max="1000" value="10" required>

    <button type="submit">Save Mission</button>
    <a class="back" href="AdminDash">Back to Dashboard</a>

</form>
</div>
</body>
</html>