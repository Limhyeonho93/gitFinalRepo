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

<style>
	/* 1) 기존 .main-content 설정 덮어쓰기 */
	.main-content {
	  /* 세로 방향 플렉스 컨테이너로 */
	  display: flex;
	  flex-direction: column;
	  /* 자식(.home_img)을 가로 중앙으로 */
	  align-items: center;
	  /* 위쪽 패딩을 없애거나 줄이기 (p-4 때문에 있던 pt-4를 재정의) */
	  padding-top: 0 !important;
	}
	
	/* 2) 이미지 컨테이너 */
	.home_img {
	  /* 너비 100% 유지하면서 텍스트(이미지)를 중앙 */
	  width: 100%;
	  text-align: center;
	  /* header 바로 아래 딱 붙이고 싶으면 margin-top 조절 */
	  margin-top: 2rem; /* 필요에 따라 0~2rem 사이로 조절하세요 */
	}
	
	/* 3) img 태그 반응형 유지 */
	.home_img img {
	  display: inline-block;
	  max-width: 100%;
	  height: auto;
	}
	
</style>
</head>

<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
			    <div class="home_img">
			    	<img src="${pageContext.request.contextPath}/resources/images/home_img1.jpg"/>
			    </div>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
</html>