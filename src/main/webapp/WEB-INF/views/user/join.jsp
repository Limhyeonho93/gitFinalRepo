<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

<!-- Bootstrap & Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>


</head>
<body>
	<div class="wrap">

		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

			<h2 style="text-align: center;">회원가입</h2>

			<form action="${pageContext.request.contextPath}/user/join"
				method="post">
				<div>
					<label for="userId">유저 ID</label> <input type="email" id="userId"
						name="userId" placeholder="~@~.com 형식으로 입력" required>
				</div>
				<div>
					<label for="compCd">회사 코드</label> <input type="text" id="compCd"
						name="compCd">
				</div>
				<div>
					<label for="userPw">비밀번호</label> <input type="password" id="userPw"
						name="userPw" required>
				</div>
				<div>
					<label for="userName">유저명</label> <input type="text" id="userName"
						name="userName">
				</div>
				<div>
					<label for="deptName">부서</label> <input type="text" id="deptName"
						name="deptName">
				</div>
				<div>
					<label for="telNo">연락처</label> <input type="text" id="telNo"
						name="telNo">
				</div>
				<div>
					<button type="submit">가입하기</button>
				</div>
			</form>
			
			<%-- 회원 가입 완료후 다시 메인 페이지로 돌아가게  --%>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>
