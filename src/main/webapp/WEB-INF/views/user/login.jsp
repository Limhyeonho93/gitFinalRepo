<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>

<!-- Bootstrap & Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resource/css/layout.css">
<script src="/resource/js/layout.js" defer></script>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

			<h1>로그인 페이지</h1>
			<form action="${pageContext.request.contextPath}/user/login"
				method="post">
				<div>
					<%-- 회원 아이디 입력 --%>
					<label for="userId">User ID:</label> <input type="text" id="userId"
						name="userId" required>
				</div>
				<div>
					<%-- 회원 비밀번호 --%>
					<label for="password">Password:</label> <input type="password"
						id="userPw" name="userPw" required>
				</div>
				<div>
					<button type="submit">로그인</button>
				</div>
			</form>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<script>
		
	<%-- 아이디 중복 체크 확인 --%>
		
	</script>
</body>
</html>