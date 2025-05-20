<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<header
		class="header text-white px-4 py-2 d-flex justify-content-between align-items-center">

		<div class="d-flex align-items-center">
			<button id="toggleSidebar" class="btn btn-outline-light btn-sm me-3">&#8801;</button>
			<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/car_nuggi2.png" alt="홈으로" style="height:100px;"/></a>
		</div>
		<div>
			<span class="me-3 text-white fw-bold">관리자</span>
			<%Object user = session.getAttribute("user");
			 if (user != null) {
			%>
			 <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline-light btn-sm">로그아웃</a>
			 <%} else { %>
			 
			 <a	href="${pageContext.request.contextPath}/user/loginFrm"	class="btn btn-outline-light btn-sm">로그인</a>
			 <%} %>
		</div>
	</header>
</body>
</html>