<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>청구서 추가</title>

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

	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<main class="content d-flex">
		<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

		<h2>청구서 요금 수정</h2>
		<form action="/invoice/insertShopping" method="get">

			<label>지역 선택:</label> <select name="disGrade">
				<option value="A">수도권</option>
				<option value="B">충청/강원</option>
				<option value="C">경상/전라</option>
				<option value="D">제주/도서산간</option>

			</select> <br> <br> <label>무게 선택:</label> <select name="weight">
				<option value="100">100</option>
				<option value="500">500</option>
				<option value="1000">1000</option>
				<option value="2000">2000</option>
				<option value="4000">4000</option>
			</select> <br> <br>
			<p>선택한 지역: ${regionName}</p>
			<p>기본 요금: ${total}</p>



			<h3>추가 요금 입력</h3>
			<br> <label>추가 금액:</label> <input type="number" id="adPrice" name="adPrice"
				min="0" max="1000000" step="1000"> <br> <br>
				
<br>
				<p>${total} + ${adPrice}  </p>
			<button>요금 계산</button>
		</form>

	</main>


</body>
</html>