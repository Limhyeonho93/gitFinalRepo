<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>직원 회원 가입</title>
<!-- Bootstrap & Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Custom CSS -->
<link rel="stylesheet" href="/resources/css/layout.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 class="text-center">직원 회원 가입</h2>
		<form action="${pageContext.request.contextPath}/user/userJoin"
			method="post">
			<div class="mb-3">
				<label for="compCd" class="form-label">회사 코드</label> <input
					type="text" id="compCd" name="compCd" class="form-control"
					maxlength="10" pattern="^[A-Za-z0-9]{1,10}$"
					placeholder="회사 코드를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userId" class="form-label">유저 ID</label> <input
					type="text" id="userId" name="userId" class="form-control"
					placeholder="유저 ID를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userPw" class="form-label">비밀번호</label> <input
					type="password" id="userPw" name="userPw" class="form-control"
					placeholder="비밀번호를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userName" class="form-label">이름</label> <input
					type="text" id="userName" name="userName" class="form-control"
					placeholder="이름을 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="deptName" class="form-label">부서</label> <input
					type="text" id="deptName" name="deptName" class="form-control"
					placeholder="부서를 입력하세요">
			</div>
			<div class="mb-3">
				<label for="telNo" class="form-label">전화번호</label> <input
					type="text" id="telNo" name="telNo" class="form-control"
					pattern="\\d{2,3}-\\d{3,4}-\\d{4}" placeholder="예: 010-1234-5678">
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-primary">회원 가입</button>
			</div>
		</form>
	</div>
</body>
</html>