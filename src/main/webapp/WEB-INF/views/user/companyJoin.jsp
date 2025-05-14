<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회사 회원가입</title>

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

			<h2 class="text-center">회사 회원가입</h2>
			<form action="${pageContext.request.contextPath}/user/companyJoin"
				method="post">
				<div class="mb-3">
					<label for="compCd" class="form-label">회사 코드</label> <input
						type="text" id="compCd" name="compCd" class="form-control"
						maxlength="10"
						pattern="^[A-Za-z0-9]{1,10}$"
						placeholder="글자와 숫자 섞어서 최대 10글자" required>
				</div>
				<div class="mb-3">
					<label for="compName" class="form-label">회사 이름</label> <input
						type="text" id="compName" name="compName" class="form-control"
						placeholder="회사 이름 입력" required>
				</div>
				<div class="mb-3" style="display: none;">
					<input type="hidden" id="grade" name="grade" class="form-control"
						value="2" readonly>
					<%-- 등급 --%>
				</div>
				<div class="mb-3">
					<label for="compAddr" class="form-label">회사 주소</label> <input
						type="text" id="compAddr" name="compAddr" class="form-control"
						placeholder="회사 주소 입력" required>
				</div>
				<div class="mb-3">
					<label for="postalCode" class="form-label">우편번호</label> <input
						type="text" id="postalCode" name="postalCode" class="form-control"
						pattern="\\d{5}" placeholder="5자리 숫자 입력" >
				</div>
				<div class="mb-3">
					<label for="compTel" class="form-label">회사 전화번호</label> <input
						type="text" id="compTel" name="compTel" class="form-control"
						placeholder="예: 02-123-4567"
						>
				</div>
				<div class="mb-3" style="display: none;">
					<input type="hidden" id="transactionStatus"
						name="transactionStatus" class="form-control" value="Y" readonly>
					<%-- 거래상황 --%>
				</div>
				<div class="mb-3" style="display: none;">
					<input type="hidden" id="regDate" name="regDate"
						class="form-control" required>
					<%-- 등록일 --%>
				</div>
				<div class="mb-3" style="display: none;">
					<input type="hidden" id="updDate" name="updDate"
						class="form-control" required>
					<%-- 갱신일 --%>
				</div>
				<div class="text-center">
					<button type="submit" class="btn btn-primary">가입하기</button>
				</div>
			</form>

		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<script>
		// 등록일 오늘 날짜로 지정
		document.getElementById('regDate').value = new Date().toISOString()
				.split('T')[0];
	</script>
</body>
</html>
