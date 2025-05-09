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
<link rel="stylesheet" href="/resource/css/layout.css">
<script src="/resource/js/layout.js" defer></script>
</head>
<body>
	 <div class="wrap">
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <main class="content d-flex">
      <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
      <div class="main-content flex-grow-1 p-4">
        <h2>📦 화물관리 시스템 대시보드</h2>
        <p>이곳에 화물 요약 현황, 알림, 업무 바로가기 등을 표시할 수 있습니다.</p>
      </div>
    </main>
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
  </div>
</body>
</html>