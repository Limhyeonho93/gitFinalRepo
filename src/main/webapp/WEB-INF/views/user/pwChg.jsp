<%@page import="kr.or.iei.user.model.vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>

<!-- Bootstrap & Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>
<script src="/resources/js/sweetalert.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style>
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
								<h4 class="mb-0">비밀번호 변경</h4>
							</div>
							<div class="card-body">
								<form id="pwChgForm" action="/user/pwChg" method="post" onsubmit="return validateForm()">
									<div class="mb-3">
										<label for="userPw" class="form-label">현재 비밀번호</label>
										<input type="password" id="userPw" name="userPw" class="form-control" required>
									</div>

									<div class="mb-3">
										<label for="newUserPw" class="form-label">새 비밀번호</label>
										<input type="password" id="newUserPw" name="newUserPw" class="form-control" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30글자" required>
									</div>

									<div class="mb-3">
										<label for="newUserPwRe" class="form-label">새 비밀번호 확인</label>
										<input type="password" id="newUserPwRe" name="newUserPwRe" class="form-control" placeholder="새 비밀번호 확인" required>
										<p id="pwMessage" class="form-text text-danger"></p>
									</div>

									<div class="d-grid">
										<button type="submit" class="btn btn-primary">변경하기</button>
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

<script>
function validateForm(){
	const newPw = /^[a-zA-Z0-9!@#$]{6,30}$/;
	
	
	if(!newPw.test($('#newUserPw').val())){
		swal({
			title : "알림",
			text : "새 비밀번호가 유효하지 않습니다.",
			icon : "warning"
		});
		return false;
	}

	if($('#newUserPw').val() != $('#newUserPwRe').val()){

		swal({
			title : "알림",
			text : "새 비밀번호와 확인값이 일치하지 않습니다.",
			icon : "warning"
		});
		return false;
	}
	return true;
}
</script>
</body>
</html>
