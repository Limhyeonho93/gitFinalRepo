<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화물 등록</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
    rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>

<!-- SweetAlert -->
<script src="/resources/js/sweetalert.min.js"></script>

<style type="text/css">

/* 기본 레이아웃 스타일 */
.container-fluid {
    margin-top: 30px;
    margin-bottom: 10px;
}

.textarea-search {
    position: relative;
    display: block;
    height: 40px;
}

.textarea-search textarea {
    height: 40px;
    resize: none;
    overflow: auto;
}

.textarea-search textarea.input-sm {
    height: 30px;
}

.textarea-search textarea:FOCUS {
    height: 250px;
    width: 250px;
    position: absolute;
    top: -20px;
    z-index: 4;
}

.form-select {
    width: 200px;
}

</style>

</head>
<body>
    <div class="wrap">
        <jsp:include page="/WEB-INF/views/common/header.jsp" />
        <main class="content d-flex">
            <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
            
            <div class="container-fluid">
                <h2 class="mb-4">화물 등록</h2>
                
                <!-- 화물 등록 폼 -->
                <form id="cargoRegisterForm" action="/registerCargo" method="POST">
                    <div class="d-flex flex-column gap-3">
                        <!-- 회사명 -->
                        <div class="d-flex gap-2">
                            <label for="compCd" class="form-label">회사명</label>
                            <input type="text" class="form-control" id="compCd" name="compCd" required>
                        </div>

                        <!-- 창고 이동 ID -->
                        <div class="d-flex gap-2">
                            <label for="warehouseMoveid" class="form-label">창고 이동 ID</label>
                            <input type="text" class="form-control" id="warehouseMoveid" name="warehouseMoveid" required>
                        </div>

                        <!-- 트래킹 번호 -->
                        <div class="d-flex gap-2">
                            <label for="trackingNo" class="form-label">트래킹 번호</label>
                            <input type="text" class="form-control" id="trackingNo" name="trackingNo" required>
                        </div>

                        <!-- 관리 번호 -->
                        <div class="d-flex gap-2">
                            <label for="manageNo" class="form-label">관리 번호</label>
                            <input type="text" class="form-control" id="manageNo" name="manageNo" required>
                        </div>

                        <!-- 수취인 이름 -->
                        <div class="d-flex gap-2">
                            <label for="receiverName" class="form-label">수취인 이름</label>
                            <input type="text" class="form-control" id="receiverName" name="receiverName" required>
                        </div>

                        <!-- 수취인 주소 -->
                        <div class="d-flex gap-2">
                            <label for="receiverAdd" class="form-label">수취인 주소</label>
                            <input type="text" class="form-control" id="receiverAdd" name="receiverAdd" required>
                        </div>

                        <!-- 수취인 우편번호 -->
                        <div class="d-flex gap-2">
                            <label for="receiverZip" class="form-label">수취인 우편번호</label>
                            <input type="text" class="form-control" id="receiverZip" name="receiverZip" required>
                        </div>

                        <!-- 수취인 전화번호 -->
                        <div class="d-flex gap-2">
                            <label for="receiverTel" class="form-label">수취인 전화번호</label>
                            <input type="text" class="form-control" id="receiverTel" name="receiverTel" required>
                        </div>

                        <!-- 판매자 이름 -->
                        <div class="d-flex gap-2">
                            <label for="sellerName" class="form-label">판매자 이름</label>
                            <input type="text" class="form-control" id="sellerName" name="sellerName" required>
                        </div>

                        <!-- 판매자 주소 -->
                        <div class="d-flex gap-2">
                            <label for="sellerAdd" class="form-label">판매자 주소</label>
                            <input type="text" class="form-control" id="sellerAdd" name="sellerAdd" required>
                        </div>

                        <!-- 판매자 전화번호 -->
                        <div class="d-flex gap-2">
                            <label for="sellerTel" class="form-label">판매자 전화번호</label>
                            <input type="text" class="form-control" id="sellerTel" name="sellerTel" required>
                        </div>

                        <!-- 총중량 -->
                        <div class="d-flex gap-2">
                            <label for="gw" class="form-label">총중량 (GW)</label>
                            <input type="number" class="form-control" id="gw" name="gw" required>
                        </div>

                        <!-- 총중량 단위 -->
                        <div class="d-flex gap-2">
                            <label for="gwt" class="form-label">총중량 단위 (GWT)</label>
                            <input type="text" class="form-control" id="gwt" name="gwt" required>
                        </div>

                        <!-- 품목 번호 -->
                        <div class="d-flex gap-2">
                            <label for="no" class="form-label">품목 번호</label>
                            <input type="number" class="form-control" id="no" name="no" required>
                        </div>
                    </div>
                    
                    <!-- 제출 버튼 -->
                    <div class="mt-4 d-flex justify-content-end">
                        <button type="submit" class="btn btn-primary" id="registerBtn">등록</button>
                    </div>
                </form>
            </div>
        </main>
        <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </div>

    <script>
        // 화물 등록 버튼 클릭 시 화면 이동 기능
      $('#registerBtn').on('click', function(event) {
    event.preventDefault();  // 폼 제출을 막고

    // 폼 데이터가 유효한지 확인
    if ($('#cargoRegisterForm')[0].checkValidity()) {
        // SweetAlert로 성공 메시지 표시
        swal({
            title: "화물 등록",
            text: "화물이 성공적으로 등록되었습니다.",
            icon: "success",
        }).then(() => {
            // 화물 등록이 성공적으로 이루어지면 폼을 제출
            $('#cargoRegisterForm').submit();  // 서버로 폼 데이터 전송
        });
    } else {
        // 폼이 유효하지 않으면 알림 표시
        swal({
            title: "알림",
            text: "모든 필드를 채워주세요.",
            icon: "warning",
        });
    }
});
    </script>
</body>
</html>