<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<form action="register" method="post">
		<div class="container">
			<h1>Register</h1>
			<p>Please fill in this form to create an account.</p>
			<hr>

			<label for="email"><b>Email</b></label>
			<input type="text" placeholder="Enter Email" name="email" id="email" required>

			<label for="fullname"><b>Full Name</b></label>
			<input type="text" placeholder="Enter Full Name" name="fullname" id="fullname" required>

			<label for="phone"><b>Phone Number</b></label>
			<input type="text" placeholder="Enter Phone Number" name="phone" id="phone" required>

			<label for="psw"><b>Password</b></label>
			<input type="password" placeholder="Enter Password" name="passwd" id="passwd" required>

			<label for="psw-repeat"><b>Repeat Password</b></label>
			<input type="password" placeholder="Repeat Password" name="passwd-repeat" id="passwd-repeat" required>
			<hr>


			<button type="submit" class="registerbtn">Register</button>
		</div>

		<div class="container signin">
			<p>Already have an account? <a href="#">Sign in</a>.</p>
		</div>
	</form>
</body>
</html>