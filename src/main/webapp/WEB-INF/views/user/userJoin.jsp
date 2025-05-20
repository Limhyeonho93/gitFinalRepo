<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>회원등록</title>

            <!-- Bootstrap & Icons -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

            <!-- Custom CSS & JS -->
            <link rel="stylesheet" href="/resources/css/layout.css">
            <script src="/resources/js/layout.js" defer></script>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

        </head>

        <body>
            <div class="wrap">

                <jsp:include page="/WEB-INF/views/common/header.jsp" />
                <main class="content d-flex">
                    <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

                    <form action="${pageContext.request.contextPath}/user/userJoin" method="post"
                        class="container mt-5">
                        <div class="row justify-content-center">
                            <div class="col-md-8 col-lg-6">
                                <h2 class="text-center mb-4">회원 등록</h2>

                                <!-- 회사 선택 -->
                                <div class="mb-3">
                                    <label for="compCd" class="form-label">등록 회사</label>
                                    <select class="form-select" id="compCd" name="compCd" required>
                                        <c:choose>
                                            <c:when test="${user.userLevel eq '1'}">
                                                <c:forEach var="compInfo" items="${compInfoArr}">
                                                    <option value="${compInfo.compCd}">${compInfo.compName}</option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${user.compCd}">
                                                    <c:forEach var="compInfo" items="${compInfoArr}">
                                                        <c:if test="${compInfo.compCd eq user.compCd}">
                                                            ${compInfo.compName}
                                                        </c:if>
                                                    </c:forEach>
                                                </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>

                                <!-- 셀러 권한 선택 (일반 유저만 표시) -->
                                <c:if test="${user.userLevel ne '1'}">
                                    <div class="mb-3">
                                        <label class="form-label d-block">권한 선택</label>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="userLevel" id="adminUser"
                                                value="2" checked>
                                            <label class="form-check-label" for="adminUser">셀러 관리자</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="userLevel"
                                                id="normalUser" value="3">
                                            <label class="form-check-label" for="normalUser">셀러 일반</label>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- 유저 정보 입력 -->
                                <div class="mb-3">
                                    <label for="userId" class="form-label">이메일(ID)</label>
                                    <input type="email" class="form-control" id="userId" name="userId"
                                        placeholder="example@domain.com" required>
                                </div>

                                <div class="mb-3">
                                    <label for="userPw" class="form-label">비밀번호</label>
                                    <input type="password" class="form-control" id="userPw" name="userPw"
                                        placeholder="비밀번호 입력" required>
                                </div>

                                <div class="mb-3">
                                    <label for="userName" class="form-label">이름</label>
                                    <input type="text" class="form-control" id="userName" name="userName"
                                        autocomplete="off" required>
                                </div>

                                <div class="mb-3">
                                    <label for="deptName" class="form-label">부서명</label>
                                    <input type="text" class="form-control" id="deptName" name="deptName" required>
                                </div>

                                <div class="mb-3">
                                    <label for="telNo" class="form-label">전화번호</label>
                                    <input type="text" class="form-control" id="telNo" name="telNo" required>
                                </div>

                                <!-- 고정값 -->
                                <input type="hidden" name="transactionStatus" value="Y">

                                <!-- 제출 버튼 -->
                                <div class="text-center mt-4">
                                    <button type="submit" class="btn btn-primary px-5">가입하기</button>
                                </div>
                            </div>
                        </div>
                    </form>


                </main>
                <jsp:include page="/WEB-INF/views/common/footer.jsp" />
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
