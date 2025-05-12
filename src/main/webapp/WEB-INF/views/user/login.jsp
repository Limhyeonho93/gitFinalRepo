<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	<form action="${pageContext.request.contextPath}/user/join"
		method="post">
		<div>
			<label for="userId">User ID:</label> <input type="text" id="userId"
				name="userId" required>
		</div>
		<div>
			<label for="password">Password:</label> <input type="password"
				id="password" name="password" required>
		</div>
		<div>
			<label for="name">Name?? huh? 지워도 됨</label> <input type="text"
				id="name" name="name" required>
		</div>
		<div>
			<button type="submit">Join</button>
			<button type="button"
				onclick="location.href='${pageContext.request.contextPath}/user/joinFrm'">
				회원가입</button>
		</div>
	</form>
</body>
</html>