<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>비밀번호 찾기</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    
<style>
        body {
            background: #f0f8ff;
            padding: 40px;
        }

        h2 {
            text-align: center;
            color: #007acc;
            margin-bottom: 30px;
        }

        form {
            width: 100%;
            max-width: 400px;
            margin: 0 auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
		
        label {
            font-weight: bold;
            color: #333;
        }
		
        input[type="email"],
        input[type="text"] {
            width: 100%;
            padding: 12px;
            margin-top: 5px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #1a8cd8;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #005f99;
        }

        hr {
            margin: 30px 0;
            border: none;
            border-top: 1px solid #ddd;
        }
        
    	.footer-container {
        text-align: center;
        margin-top: 250px;
        font-size: 14px;
        color: #777;
    }
</style>
</head>

<body>
    <h2>비밀번호 찾기</h2>

    <form action="<c:url value='/user/forgotPw'/>" method="post">
        <div>
            <label for="userId">이메일 (아이디)</label><br>
            <input type="email" name="userId" id="userId" required placeholder="example@email.com">
        </div>

        <div>
            <label for="userName">이름</label><br>
            <input type="text" name="userName" id="userName" required placeholder="이름">
        </div>

        <div>
            <button type="button" onclick="requestTempPw()">비밀번호 발급</button>
        </div>

        <hr>

        <div>
            <label for="inputTempPw">임시 비밀번호 입력</label><br>
            <input type="text" name="inputTempPw" id="inputTempPw" required placeholder="임시 비밀번호 입력">
        </div>

        <div>
            <button type="submit">임시 비밀번호 확인</button>
        </div>
    </form>
<div class="footer-container">
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</div>
<script>
    function requestTempPw() {
        const userId = document.getElementById("userId").value.trim();
        const userName = document.getElementById("userName").value.trim();

        if (!userId || !userName) {
            Swal.fire("알림", "아이디와 이름을 모두 입력해주세요.", "warning");
            return;
        }

        //ajax 요청
        fetch("<c:url value='/user/forgotPw'/>", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                userId: userId,
                userName: userName
            })
        })
        .then(res => res.text())
        .then(result => {
            if (result === "true") {
                Swal.fire("성공", "임시 비밀번호가 이메일로 전송되었습니다.", "success");
            } else {
                Swal.fire("실패", "일치하는 회원 정보가 없습니다.", "error");
            }
        });
    }
</script>
</body>
</html>