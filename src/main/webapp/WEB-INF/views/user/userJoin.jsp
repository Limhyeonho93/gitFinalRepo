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
<<<<<<< HEAD
	<div class="container">
		<h2 class="text-center">직원 회원 가입</h2>
		<form action="${pageContext.request.contextPath}/user/userJoin"
			method="post">
			<div class="mb-3">
				<label for="compCd" class="form-label">회사 코드</label> <input
					type="text" id="compCd" name="compCd" class="form-control"
					maxlength="10" pattern="^[A-Za-z0-9]{1,10}$"
					placeholder="회사 코드를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userId" class="form-label">유저 ID</label> <input
					type="text" id="userId" name="userId" class="form-control"
					placeholder="유저 ID를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userPw" class="form-label">비밀번호</label> <input
					type="password" id="userPw" name="userPw" class="form-control"
					placeholder="비밀번호를 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="userName" class="form-label">이름</label> <input
					type="text" id="userName" name="userName" class="form-control"
					placeholder="이름을 입력하세요" required>
			</div>
			<div class="mb-3">
				<label for="deptName" class="form-label">부서</label> <input
					type="text" id="deptName" name="deptName" class="form-control"
					placeholder="부서를 입력하세요">
			</div>
			<div class="mb-3">
				<label for="telNo" class="form-label">전화번호</label> <input
					type="text" id="telNo" name="telNo" class="form-control"
					pattern="\\d{2,3}-\\d{3,4}-\\d{4}" placeholder="예: 010-1234-5678">
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-primary">회원 가입</button>
			</div>
			
		</form>
=======
	<div class="wrap">

		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

			<h2 class="text-center">회원가입</h2>
			<form action="${pageContext.request.contextPath}/user/userJoin"
				method="post">
				<div class="mb-3">
					<label for="compCd" class="form-label">회사 코드</label> <input
						type="text" id="compCd" name="compCd" class="form-control"
						maxlength="10" pattern="^[A-Za-z0-9]{1,10}$"
						placeholder="글자와 숫자 섞어서 최대 10글자" required>
				</div>
				<div>
					<label for="userName" class="form-label">유저명</label> <input
						type="text" id="userName" class="form-control" name="userName"
						autocomplete="nope">
				</div>
				<div class="mb-3">
					<label for="userPw" class="form-label">비밀번호</label> <input
						type="password" id="userPw" name="userPw" class="form-control"
						placeholder="비밀번호를 입력하세요" required>
				</div>
				<div class="mb-3">
					<label for="compName" class="form-label">회사 이름</label> <input
						type="text" id="compName" name="compName" class="form-control"
						placeholder="회사 이름 입력" required>
				</div>

				<div class="mb-3">
					<label for="userId" class="form-label">이메일</label> <input
						type="email" id="userId" name="userId" class="form-control"
						placeholder="이메일을 입력하세요 (로그인 ID로 사용됩니다)" required>
				</div>
				<div class="mb-3">
					<label for="compAddr" class="form-label">회사 주소</label> <input
						type="text" id="compAddr" name="compAddr" class="form-control"
						placeholder="회사 주소 입력" required>
				</div>
				<div class="mb-3">
					<label for="compTel" class="form-label">회사 전화번호</label> <input
						type="text" id="compTel" name="compTel" class="form-control"
						placeholder="예: 02-123-4567">
				</div>
				<%-- 거래상황 --%>
				<div class="mb-3" style="display: none;">
					<input type="hidden" id="transactionStatus"
						name="transactionStatus" class="form-control" value="Y" readonly>
				</div>

				<%-- 체크박스 추가 --%>
				<div>
					<label for="gradeCheckbox">등급 선택:</label> <input type="checkbox" id="gradeCheckbox" name="gradeCheckbox"
						value="2" onchange="handleCheckboxChange(this)"> 회사 <input id="gradeCheckbox"
						type="checkbox" name="gradeCheckbox" value="3"
						onchange="handleCheckboxChange(this)"> 직원
				</div>
				<%-- hidden 필드 --%>
				<input type="hidden" id="grade" name="grade" value="">
				<div class="text-center">
					<button type="submit" class="btn btn-primary">가입하기</button>
				</div>
			</form>

		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
>>>>>>> master
	</div>
	<script>
	
		// 체크박스 선택 시 하나만 선택되도록 처리
	    function handleCheckboxChange(checkbox) {
	        const checkboxes = document.querySelectorAll('input[name="gradeCheckbox"]');
	        checkboxes.forEach((box) => {
	            if (box !== checkbox) {
	                box.checked = false; // 다른 체크박스는 선택 해제
	            }
	        });

	        // 선택된 체크박스의 값을 hidden 필드에 설정
	        const gradeField = document.getElementById('grade');
	        gradeField.value = checkbox.value;
	    }
	</script>
</body>
</html>
