<%@page import="kr.or.iei.user.model.vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개인정보 수정 페이지</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/resources/js/sweetalert.min.js"></script>

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
	.content-container {
	  display: flex;
	  align-items: flex-start; 
	  gap: 1rem;               
	}
    /* 전체 컨테이너 */
    .content-container {
        display: flex;
        gap: 20px;
        padding: 40px;
        background-color: #f8f9fc;
    }


    /* 본문 */
    .main-content {
        flex: 1;
        background: #ffffff;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.08);
    }

    .main-content h1 {
        font-size: 24px;
        color: #007acc;
        margin-bottom: 30px;
        text-align: center;
    }

    /* 테이블 */
    .customer {
        width: 100%;
        border-collapse: collapse;
    }

    .customer th {
        background-color: #f0f8ff;
        text-align: left;
        padding: 12px;
        width: 30%;
        font-weight: bold;
        border-bottom: 1px solid #ccc;
    }

    .customer td {
        padding: 12px;
        border-bottom: 1px solid #eee;
    }

    .input-wrap {
        width: 100%;
    }

    .input-item input[type="text"],
    .input-item input[type="email"],
    .input-item input[type="password"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
        background-color: #fdfdfd;
    }

    input[readonly] {
        background-color: #f5f5f5;
        color: #555;
    }

    /* 버튼 모ㅗ양 */
    .userUpdate-btn {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
        gap: 10px;
    }

    .btn-first,
    .btn-second,
    .btn-cp {
        flex: 1;
        padding: 12px;
        font-size: 15px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .btn-first {
        background-color: #007acc;
        color: #fff;
    }

    .btn-first:hover {
        background-color: #005f99;
    }

    .btn-second {
        background-color: #ccc;
        color: #333;
    }

    .btn-second:hover {
        background-color: #999;
    }

    .btn-cp {
        background-color: #00bcd4;
        color: #fff;
    }

    .btn-cp:hover {
        background-color: #0097a7;
    }

    /* 반응형??? */
    @media (max-width: 768px) {
        .content-container {
            flex-direction: column;
        }

        .sidebar {
            width: 100%;
        }

        .userUpdate-btn {
            flex-direction: column;
        }
    }

</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		
		<main class="content-container">
		<aside class="sidebar">
		<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
		</aside>
		
		<div class="main-content">
			<h1>개인정보 수정 페이지</h1>
			<section class="section update-wrap">
				<div class="my-info-wrap">
					<form id="updateForm" 
					      action="/user/update" 
					      method="post">
					    <%-- 사용자에게 보이지는 않지만 SQL 수행 시 필요한 유저아이디 hidden --%>
					    <input type="hidden" name="userId" value="${loginUser.userId}">
						<table class="customer">
							<tr>
								<th>아이디</th>
								<td class="User">
									<input type="text" name="userId" value="${loginUser.userId}" readonly>
								</td>
							</tr>
							<tr> 
								<th>비밀번호</th>
								<td class="User">
									<div class="input-wrap">
										<div class="input-item">
											<button type="button" class="btn-cp" onclick="chgPw()">비밀번호 변경</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>
									<label for="UserName">이름</label>
								</th>
								<td class="User">
									<div class="input-wrap">
										<div class="input-item">
											<input type="text"
												   id="userName"
												   name="userName"
												   value="${loginUser.userName}"
											  	   maxlength="12"
											  	   placeholder="이름을 입력하세요">
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>회사코드</th>
								<td class="User">
									<input type="text" name="compCd" value="${loginUser.compCd}" readonly>
								</td>
							</tr>
							<tr>
								<th>
									<label for="telNo">전화번호</label>
								</th>
								<td class="User">
									<div class="input-wrap">
										<div class="input-item">
											<input type="text"
												   id="telNo"
												   name="telNo"
												   value="${loginUser.telNo}"
												   maxlength="13"
												   placeholder="010-0000-0000">
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>등록일</th>
								<td>
									<input type="text" name="regDate" value="${loginUser.regDate}" readonly>
								</td>
							</tr>
						</table>
						<div class="userUpdate-btn">
							<button type="button" onclick="updateUserInfo()" class="btn-first">정보수정</button>
							<button type="button" onclick="#" class="btn-second">회원탈퇴</button>
						</div>
					</form>
				</div>
			</section>
		</div>
		</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</div>
<script>
	function updateUserInfo(){
		
		const userNameRe = /^[a-zA-Z가-힣]{1,15}$/;
		const telNoRe = /^010-\d{4}-\d{4}/;
		
		const userName = $('#userName');
		const telNo = $('#telNo');
		
		if(!userNameRe.test($(userName).val())){
			swal({
				title : "알림",
				text  : "형식에 맞게 입력하세요",
				icon  : "warning"
			});
			return;
		}
		
		if(!telNoRe.test($(telNo).val())){
			swal({
				title : "알림",
				text  : "형식에 맞게 입력하세요",
				icon  : "warning"
			});
			return;
		}
		
		swal({
			title : "수정",
			text : "회원 정보를 수정하시겠습니까?",
			icon : "warning",
			buttons : {
				confirm : {
					text : "수정",
					value : true,
					visible : true,
					closeModal : true
				},
				cancel : {
					text : "취소",
					value : false,
					visible : true,
					closeModal : true
				}
			}
		}).then(function(val){
			
			if(val){
				$('#updateForm').submit();		
			}
		});
	}
		 
	function chgPw() {
		window.location.href = '${pageContext.request.contextPath}/user/pwChgFrm';
	
	}
	</script>
</body>
</html>
