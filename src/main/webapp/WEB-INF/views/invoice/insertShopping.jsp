<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>청구 요금 기준표</title>

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
.center-box {
	max-width: 600px;
	margin: 30px auto;
	background: #ffffff;
	padding: 30px;
	border-radius: 12px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>

</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

			<div class="container-fluid scroll-area">
				<div class="center-box">
					<h2 class="text-center mb-4">청구 요금 기준표</h2>

					<form action="/invoice/insertShopping" method="get">
						<div class="mb-3">
							<label for="disGrade" class="form-label">지역 선택</label> <select
								name="disGrade" id="disGrade" class="form-select"
								onchange="updateDisplay()">
								<option value="">지역 선택</option>
								<option value="A">수도권</option>
								<option value="B">충청/강원</option>
								<option value="C">경상/전라</option>
								<option value="D">제주/도서산간</option>
							</select>
						</div>

						<div class="mb-3">
							<label for="weight" class="form-label">무게 선택</label> <select
								name="weight" id="weight" class="form-select"
								onchange="updateDisplay()">
								<option value="">무게 선택</option>
								<option value="100">100</option>
								<option value="500">500</option>
								<option value="1000">1000</option>
								<option value="2000">2000</option>
								<option value="4000">4000</option>
							</select>
						</div>

						<div class="mb-3">
							<div>
								선택한 지역: <strong><span id="selectedRegion">${regionName}</span></strong>
							</div>
							<div>
								기본 요금: <strong><span id="basePrice">${price}</span></strong>
							</div>
						</div>

						<hr>

						<h5 class="mt-4 mb-3">추가 요금 입력</h5>

						<div class="mb-3">
							<label for="adPrice" class="form-label">추가 금액</label> <input
								type="number" id="adPrice" name="adPrice" class="form-control"
								min="0" max="1000000" step="1000">
						</div>

						<p>
							최종 요금 예상: <strong id="finalPrice">${price} + ${adPrice}</strong>
						</p>

						<div class="text-center mt-4">
							<button type="button" class="btn btn-primary px-4"
								onclick="calculateTotal()">요금 계산</button>
						</div>
						<button type="submit" id="total" name="total">저장</button>
					</form>
				</div>
			</div>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>

	<script>
	function calculateTotal() {
		const basePrice = Number(${price}); // 서버에서 넘긴 기본요금
		const addPrice = Number(document.getElementById("adPrice").value) || 0;
		const final = basePrice + addPrice;
		// 최종 요금 화면에 표시
		document.getElementById("finalPrice").innerText = final.toLocaleString();
		// 최조 요금 hidden 필드에서 보여줌
		document.getElementById("total").value = final;
	}
	
	function updateDisplay() {
	    const disGrade = document.getElementById("disGrade").value;
	    const weight = document.getElementById("weight").value;

	    if (disGrade && weight) {
	        // AJAX 요청
	        fetch(`/invoice/insertShopping?disGrade=${disGrade}&weight=${weight}`, {
	            method: 'GET',
	            headers: {
	                'Accept': 'application/json'
	            }
	        })
	        .then(response => response.json())
	        .then(data => {
	            // 서버에서 받은 데이터로 화면 업데이트
	            document.getElementById("selectedRegion").innerText = data.regionName;
	            document.getElementById("basePrice").innerText = data.price.toLocaleString();
	        })
	        .catch(error => console.error('Error:', error));
	    }
	}
	

     </script>
</body>
</html>
