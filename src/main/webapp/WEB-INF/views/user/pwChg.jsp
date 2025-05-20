<%@page import="kr.or.iei.user.model.vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	.content-pw-container{
	 display: flex;
  	 align-items: flex-start;
 	 gap: 1rem;         
	}
</style>
</head>
<body>	
	<h2>비밀번호 변경</h2>
	<div class="wrap">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content-pw-container">
		<aside class="sidebar">
		<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
		</aside>
			<section class="section">
				<div class="pwInfo">
					<form id="pwChgForm" action="/user/pwChg" method="post" onsubmit="return validateForm()">
						<div class="input-wrap">
							<div class="input-title">
								<label for="userPw">현재 비밀번호</label>
							</div>
							<div class="input-item">
							
								<input type="password" id="userPw" name="userPw">
							</div>
						</div>
						
						<div class="input-wrap">
							<div class="input-title">
								<label for="newUserPw">새 비밀번호</label>
							</div>
							<div class="input-item">
								<input type="password" id="newUserPw" name="newUserPw" placeholder="영어, 숫자, 특수문자(!,@,#,$) 6~30글자">
							</div>
						</div>
						
						<div class="input-wrap">
							<div class="input-title">
								<label for="newUserPwRe">새 비밀번호 확인</label>
							</div>
							<div class="input-item">
								<input type="password" id="newUserPwRe" placeholder="새 비밀번호 확인">
							</div>
							<p id="pwMessage" class="input-msg"></p>
						</div>
						
						<div class="pw-btn">
							<button type="submit" class="btn-first">변경하기</button>
						</div>
					</form>
				</div>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
<script>
	function validateForm(){
	
	
	const newPw = /^[a-zA-Z0-9!@#$]{8, 50}$/; //영어, 숫자, 특수문자 (!@#$) 6~30글자
	
	if(!newPw.test($('#newUserPw').val())){
		swal({
			title : "알림",
			text : "새 비밀번호가 유효하지 않습니다.",
			icon : "warning"
		});
		
		return false;
	}
	
	
	//새 비밀번호와 비밀번호 확인값이 같은지 확인		
	if($('#newUserPw').val() != $('#newUserPwRe').val()){
		swal({
			title : "알림",
			text : "새 비밀번호와 새 비밀번호 확인값이 일치하지 않습니다.",
			icon : "warning"
		});
		
		return false;
		}
		
	}
</script>
</body>
</html>