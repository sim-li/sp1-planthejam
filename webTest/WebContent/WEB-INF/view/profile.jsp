<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>profile page</title>
<style>
#profile-box {
	width:500px;
	padding: 20px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.loginForm.username.focus();'>

	<h1>User Profile</h1>

	<div id="profile-box">

		<h3>profile</h3>
		<form name='profile' action="profile?username=${username}" method='POST'>

			<table width="500px">
				<tr>
					<td>Username:</td>
					<td><c:if test="${not empty username}">
							<div class="profile">${username}</div>
						</c:if></td>
					<td></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td>************</td>
					<td></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><c:if test="${not empty email}">
							<div class="profile">${email}</div>
						</c:if></td>
					<td><input type='email' name='email' value=''
						min="3" max="15" /></td>
				</tr>
				<tr>
					<td>Age:</td>
					<td><c:if test="${not empty age}">
							<div class="profile">${age}</div>
						</c:if></td>
					<td><input type='text' name='age' value=''
						min="3" max="15" /></td>
				</tr>
				<tr>
					<td>Telephone:</td>
					<td><c:if test="${not empty telephone}">
							<div class="profile">${telephone}</div>
						</c:if></td>
					<td><input type='text' name='telephone' value=''
						min="3" max="15" /></td>
				</tr>
				<tr>
					<td></td>
					<td colspan='2'><input name="submit" type="submit"
						value="update" /></td>
				</tr>
			</table>

		</form>
	</div>

</body>
</html>