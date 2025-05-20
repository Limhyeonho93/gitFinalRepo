<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>화물관리시스템 - 대시보드</title>

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
			<div class="main-content flex-grow-1 p-4">
				<%-- 메인 페이지에서는 그냥 메인 페이지만 보이게(ex 사진 등등) 하지만 url이나 다른 카테고리 클릭시 로그인 화면으로 이동 --%>
				<main class="content"></main>
				<%-- 로그인 성공 시 로그인 중이라고 보여줄 코드 --%>
				
				<% // 세션에서 user 속성 확인
				Object user = session.getAttribute("user");
				if (user != null) {
				%>
				<div class="alert alert-success" role="alert">
				
				</div>
				<%
				} else {
				%>
				<div class="alert alert-warning" role="alert">로그인되지 않았습니다.
					로그인이 필요합니다.</div>
				<%
				}
				%>
			</div>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>