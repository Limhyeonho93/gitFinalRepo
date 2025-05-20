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

			<button type="submit">요금 계산</button>


			<br>
			<br>

			<h3>총 요금 결과</h3>
			<p>선택한 지역: ${regionName}</p>
			<br>

			<p>
				기본 요금: <span id="baseTotal">${total}</span> 원 + 추가 금액 : <input
					type="number" id="price" name="price" min="0" max="1000000"
					step="100" required> 총 전체 금액 : <span id="sumTotal">${total}</span>
				원
			</p>




		</form>


	</main>

	<script>
		const base = parseInt(document.getElementById("baseTotal").innerText);
		const input = document.getElementById("price");
		const output = document.getElementById("sumTotal");

		input.addEventListener("input", function() {
			const add = parseInt(input.value) || 0;
			output.innerText = base + add;
		});
	</script>
</body>
</html>