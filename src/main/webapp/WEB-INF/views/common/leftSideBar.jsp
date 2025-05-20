<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
</style>

</head>

<body>
	<nav class="side-navbar bg-sidebar-bg text-white">
		<div
			class="sidebar-header d-flex align-items-center p-3 border-bottom border-secondary">
			<h4 class="text-white mb-0">물류관리</h4>
		</div>

		<ul class="list-unstyled">
			<li class="sidebar-item"><a
				class="sidebar-link d-flex align-items-center"
				data-bs-toggle="collapse" href="#bondAir" role="button"
				aria-expanded="false"> <span class="material-icons me-2">fire_truck</span>

					화물관리
			</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu" id="bondAir">
					<li><a class="sidebar-link" href="/cargo/search">화물 조회</a></li>
					<li><a class="sidebar-link" href="/cargo/cargoRegisterForm">화물
							등록</a></li>
					<li><a class="sidebar-link" href="#">화물 상태 수정</a></li>
					<c:if test="${user.userLevel eq '1'}">
						<li><a class="sidebar-link" href="/bonded/searchFrm">입출고
								관리</a></li>
					</c:if>
				</ul></li>
			<li class="sidebar-item"><a
				class="sidebar-link d-flex align-items-center"
				data-bs-toggle="collapse" href="#bondSea" role="button"
				aria-expanded="false"> <span class="material-icons me-2">receipt_long</span>
					청구서 관리
			</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu" id="bondSea">
					<li><a class="sidebar-link"
						href="${pageContext.request.contextPath}/invoice/insertShoppingFrm">청구서
							추가</a></li>

					<li><a class="sidebar-link" href="/invoice/dateSearchFrm">청구서
							조회</a></li>
					<li><a class="sidebar-link" href="/invoice/detailFrm">청구서
							상세</a></li>
					<li><a class="sidebar-link" href="/tracking/searchList">화물
							추적</a></li>
				</ul></li>
			<li class="sidebar-item"><a
				class="sidebar-link d-flex align-items-center"
				data-bs-toggle="collapse" href="#userControll" role="button"
				aria-expanded="false"> <span class="material-icons me-2">group</span>

					마이페이지
			</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu"
					id="userControll">
					<%-- 조정필요 --%>
					<c:if test="${not empty sessionScope.user}">
						<%-- 로그인을 하면 마이페이지는 보이게 하지만 3은 안보이게 설정 --%>
						<li><a class="sidebar-link" href="/user/userUpdateFrm">개인정보 수정</a></li>

					</c:if>
					<li><a class="sidebar-link" href="/user/customerInfoList">회사
							정보</a></li>
				</ul></li>
		</ul>
	</nav>
</body>

</html>
