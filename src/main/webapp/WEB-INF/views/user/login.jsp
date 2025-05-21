<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="forgotPwUrl" value="/user/forgotPwFrm"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>

<!-- Bootstrap & Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>
<style type="text/css">
.card {
		border: 1px solid #ddd;
		border-radius: 12px;
	}
	.card-header {
		background-color: #2F4B8A;
		color: #fff;
		font-weight: 600;
	}
	.btn-primary {
		background-color: #2F4B8A;
		border-color: #2F4B8A;
	}
	.btn-primary:hover {
		background-color: #23376B;
		border-color: #23376B;
	}
	a {
		color: #2F4B8A;
	}
	a:hover {
		color: #1d2c53;
		text-decoration: underline;
	}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

			<div class="container mt-5">
				<div class="row justify-content-center">
					<div class="col-md-6">
						<div class="card shadow-sm">
							<div class="card-header text-center">
								<h4 class="mb-0">로그인</h4>
							</div>
							<div class="card-body">
								<form action="${pageContext.request.contextPath}/user/login" method="post">
									<div class="mb-3">
										<label for="userId" class="form-label">아이디 (이메일 형식)</label>
										<input type="email" id="userId" name="userId" class="form-control" placeholder="example@domain.com" required>
									</div>
									<div class="mb-3">
										<label for="userPw" class="form-label">비밀번호</label>
										<input type="password" id="userPw" name="userPw" class="form-control" required>
									</div>
									<div class="d-grid mb-3">
										<button type="submit" class="btn btn-primary">로그인</button>
									</div>
									<div class="text-center">
										<a href="javascript:void(0)" onclick="location.href='${forgotPwUrl}'">비밀번호 찾기</a>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>
