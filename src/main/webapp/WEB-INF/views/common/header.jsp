<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<header
		class="header bg-dark text-white px-4 py-2 d-flex justify-content-between align-items-center">

		<div class="d-flex align-items-center">
			<button id="toggleSidebar" class="btn btn-outline-light btn-sm me-3">&#8801;</button>
			<h5 class="mb-0">물류 화물관리 시스템</h5>
		</div>
		<div>
			<c:if test="${not empty loginMember}">
				<span class="me-3 text-white fw-bold">관리자</span>
				<a href="${pageContext.request.contextPath}/member/logout"
					class="btn btn-outline-light btn-sm">로그아웃</a>
			</c:if>
		</div>
	</header>
</body>
</html>