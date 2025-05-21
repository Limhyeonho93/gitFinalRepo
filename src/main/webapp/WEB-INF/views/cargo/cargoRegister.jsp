<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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




.center-div {
    margin-left: 30px;
    margin-top: 30px;
    margin-bottom : 30px;
    width: 500px;
}

.hidden {
    display: none;
}


.table-rounded {
  border-radius: 10px;
  overflow: hidden;
}




/* 오류가 발생한 입력 필드에 빨간 테두리 */
.input-error {
    border: 1px solid red; /* 오류 발생 시 빨간 테두리 */
    box-sizing: border-box; /* 오류 필드도 패딩과 테두리 포함 크기 계산 */
    padding: 0.375rem 0.75rem;
    width: 100%; /* 오류 필드도 너비 100% */
    height: 2.25rem; /* 기본 높이와 동일하게 설정 */
}

/* 오류 메시지 스타일 */
.error-message {
    color: red;
    font-size: 12px;
    margin-top: 5px;
}

</style>

</head>
<body>

    <div class="wrap">
        <jsp:include page="/WEB-INF/views/common/header.jsp" />
          
        <main class="content d-flex">
            <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
            
            <div class="center-div">
                <h2 class="mb-4">화물 등록</h2>
                
                <%-- 버튼 --%>
                <div class="btn-group w-100 mb-3">
                    <button id="singleRegBtn" class="btn btn-primary w-50">단건 등록</button>
                    <button id="batchRegBtn" class="btn btn-outline-primary w-50">일괄 등록</button>
            </div>
                
                <!-- 화물 단건 등록 폼 -->
                <form id="cargoRegisterForm" action="/cargo/cargoRegister" method="POST" >
             
                    <table class="table table-bordered align-middle ">
                        <tbody>
				          	<!-- 회사명 -->
				            <tr>
				                <th>검색 회사</th>
				                <td>
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
				                </td>
				            </tr>

	                        <!-- 송장 번호 -->
							<tr>
							    <th>송장 번호</th>
							    <td>
							        <input type="text" class="form-control" id="trackingNo" name="trackingNo" required>
							    </td>
							</tr>
	
	                        <!-- 수취인 이름 -->
							<tr>
							    <th>수취인 이름</th>
							    <td>
							        <input type="text" class="form-control" id="receiverName" name="receiverName" required>
							    </td>
							</tr>
	
	                        <!-- 수취인 주소 -->
							<tr>
							    <th>수취인 주소</th>
							    <td>
							        <input type="text" class="form-control" id="receiverAdd" name="receiverAdd" required>
							    </td>
							</tr>
	
	                        <tr>
							    <th>수취인 우편번호</th>
							    <td>
							        <input type="text" class="form-control" id="receiverZip" name="receiverZip" required>
							    </td>
							</tr>
							<tr>
							    <th>수취인 전화번호</th>
							    <td>
							        <input type="text" class="form-control" id="receiverTel" name="receiverTel" required pattern="\d{3}-\d{3,4}-\d{4}">
							    </td>
							</tr>
							<tr>
							    <th>판매자 이름</th>
							    <td>
							        <input type="text" class="form-control" id="sellerName" name="sellerName" required>
							    </td>
							</tr>
							<tr>
							    <th>판매자 주소</th>
							    <td>
							        <input type="text" class="form-control" id="sellerAdd" name="sellerAdd" required>
							    </td>
							</tr>
							<tr>
							    <th>판매자 전화번호</th>
							    <td>
							        <input type="text" class="form-control" id="sellerTel" name="sellerTel" required pattern="\d{3}-\d{3,4}-\d{4}">
							    </td>
							</tr>
							<tr>
							    <th>상품명</th>
							    <td>
							        <input type="text" class="form-control" id="goodsName" name="goodsName" required>
							    </td>
							</tr>
							<tr>
							    <th>상품 개수</th>
							    <td>
							        <input type="text" class="form-control" id="qty" name="qty" required>
							    </td>
							</tr>
							<tr>
							    <th>상품 단가</th>
							    <td>
							        <input type="text" class="form-control" id="unitPrice" name="unitPrice" required>
							    </td>
							</tr>
							<tr>
							    <th>총 중량</th>
							    <td>
							        <input type="text" class="form-control" id="gw" name="gw" required>
							    </td>
							</tr>
							<tr>
							    <th>총 중량 단위</th>
							    <td>
							        <input type="text" class="form-control" id="gwt" name="gwt" required>
							    </td>
							</tr>
                    
                    	</tbody>
                    </table>
                    
                    
                    <!-- 제출 버튼 -->
                    <div class="mt-4 d-flex justify-content-end">
                        <button type="submit" class="btn btn-outline-primary w-100" id="registerBtn">등록</button>
                    </div>
                </form>
                
                <%--일괄 등록 폼 --%>
               <form action="/cargo/cargoBatchReg" method="post" id="cargoBatchRegFrm" class="hidden" enctype="multipart/form-data">
                   
                   <div class="mb-3">
                       <label for="cargoFile" class="form-label">파일 선택</label>
                       <input type="file" class="form-control" id="cargoFile" name="cargoFile">
                   </div>
         
                   <div class="text-center">
                       <button class="btn btn-outline-primary w-100">등록</button>
                   </div>
             </form>
    
            </div>
        </main>
        <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </div>

    <script>
    
   // 화물 등록 버튼 클릭 시 화면 이동 기능
    /*
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
   
   */
        

    $('#singleRegBtn').on('click', function() {
        $('#cargoRegisterForm').removeClass('hidden');
        $('#cargoBatchRegFrm').addClass('hidden');
        $(this).addClass('btn-primary').removeClass('btn-outline-primary');
        $('#batchRegBtn').addClass('btn-outline-primary').removeClass('btn-primary');
    });

    $('#batchRegBtn').on('click', function() {
        $('#cargoBatchRegFrm').removeClass('hidden');
        $('#cargoRegisterForm').addClass('hidden');
        $(this).addClass('btn-primary').removeClass('btn-outline-primary');
        $('#singleRegBtn').addClass('btn-outline-primary').removeClass('btn-primary');
    });
    
    $('#registerBtn').on('click', function(event){
        event.preventDefault();
        
        // 기존의 오류 메시지 및 스타일 초기화
        $('.form-control').removeClass('input-error');
        $('.error-message').remove();
        
        let isValid = true;
        
        
        
        // 송장 번호 (숫자만 허용)
       let trackingNo = $('#trackingNo').val();
          if (!trackingNo || trackingNo.length < 6 || trackingNo.length > 10) {
              $('#trackingNo').addClass('input-error');
              $('#trackingNo').after('<div class="error-message">송장 번호는 6~10자 이내여야 합니다.</div>');
              isValid = false;
        }

        // 수취인 이름 
        let receiverName = $('#receiverName').val();
        if (!receiverName || /[0-9]/.test(receiverName) || /[ㄱ-ㅎ]/.test(receiverName) || /[^\wㄱ-ㅎ가-힣\s]/.test(receiverName)) {
               $('#receiverName').addClass('input-error');
               $('#receiverName').after('<div class="error-message">수취인 이름을 제대로 입력해 주세요.</div>');
               isValid = false;
        }

        // 수취인 주소
       let receiverAdd = $('#receiverAdd').val();
       if (!receiverAdd || receiverAdd.length < 15 || receiverAdd.length > 30 || /[^a-zA-Z0-9가-힣\s]/.test(receiverAdd)) {
                 $('#receiverAdd').addClass('input-error');
                 $('#receiverAdd').after('<div class="error-message">주소는 15~30자, 특수문자 없이 입력해야 합니다.</div>');
                  isValid = false;
        }

        // 수취인 우편번호
        let receiverZip = $('#receiverZip').val();
        if (!receiverZip || !/^\d{5}$/.test(receiverZip)) {
               $('#receiverZip').addClass('input-error');
               $('#receiverZip').after('<div class="error-message">우편번호는 5자리 숫자만 입력 가능합니다.</div>');
               isValid = false;
        }

        // 수취인 전화번호 (숫자만 허용)
        let receiverTel = $('#receiverTel').val();
        if (!receiverTel || !/^\d{3}-\d{3,4}-\d{4}$/.test(receiverTel)) {
               $('#receiverTel').addClass('input-error');
               $('#receiverTel').after('<div class="error-message">전화 번호는 010-1234-5678 형식으로 입력해주세요.</div>');
               isValid = false;
        }

        // 판매자 이름
        let sellerName = $('#sellerName').val();
        if (!sellerName || /[0-9]/.test(sellerName) || /[ㄱ-ㅎ]/.test(sellerName) || /[^\wㄱ-ㅎ가-힣\s]/.test(sellerName)) {
               $('#sellerName').addClass('input-error');
               $('#sellerName').after('<div class="error-message">판매자 이름을 제대로 입력해주세요.</div>');
               isValid = false;
        }

        // 판매자 주소
          let sellerAdd = $('#sellerAdd').val();
          if (!sellerAdd || sellerAdd.length < 15 || sellerAdd.length > 30 || /[^a-zA-Z0-9가-힣\s]/.test(sellerAdd)) {
                $('#sellerAdd').addClass('input-error');
               $('#sellerAdd').after('<div class="error-message">주소는 15~30자, 특수문자 없이 입력해야 합니다.</div>');
                  isValid = false;
        }

        // 판매자 전화번호 
        let sellerTel = $('#sellerTel').val();
        if (!sellerTel || !/^\d{3}-\d{3,4}-\d{4}$/.test(sellerTel)) {
               $('#sellerTel').addClass('input-error');
               $('#sellerTel').after('<div class="error-message">전화 번호는 010-1234-5678 형식으로 입력해주세요.</div>');
               isValid = false;
        }

        // 상품명
        let goodsName = $('#goodsName').val();
        if (!goodsName || goodsName.length < 2 || goodsName.length > 100) {
               $('#goodsName').addClass('input-error');
               $('#goodsName').after('<div class="error-message">상품명은 2자 이상 100자 이하로 입력해 주세요.</div>');
               isValid = false;
        }

        // 상품 갯수
        let qty = $('#qty').val();
        if (!qty || !/^[0-9]+$/.test(qty)) {
               $('#qty').addClass('input-error');
               $('#qty').after('<div class="error-message">상품 갯수는 숫자만 입력 가능합니다.</div>');
               isValid = false;
        }

        // 상품 단가
        let unitPrice = parseFloat($('#unitPrice').val());
       if (isNaN(unitPrice) || unitPrice <= 0) {
                 $('#unitPrice').addClass('input-error');
                $('#unitPrice').after('<div class="error-message">상품 단가는 0보다 커야 합니다.</div>');
                 isValid = false;
        }

        // 총 중량(GW)
       let gw = parseFloat($('#gw').val());
         if (isNaN(gw) || gw <= 0) {
              $('#gw').addClass('input-error');
              $('#gw').after('<div class="error-message">총 중량은 0보다 커야 합니다.</div>');
               isValid = false;
        }

        // 총 중량 단위(GWT)
       let gwt = $('#gwt').val();
      if (!gwt || !/^(?!0(\.0+)?$)\d+(\.\d+)?(kg|lbs)$/i.test(gwt)) {  // 숫자가 0보다 크고, 'kg' 또는 'lbs'만 허용
             $('#gwt').addClass('input-error');
             $('#gwt').after('<div class="error-message">총 중량은 0보다 큰 숫자와 "kg" 또는 "lbs"만 입력 가능합니다.</div>');
             isValid = false;
        }

        // 유효성 검사 실패 시 폼 전송 막기
        if (!isValid) {
            swal({
                 title: "화물 등록 실패",
                 text: "모든 항목을 올바르게 입력해 주세요.",
                 icon: "warning",
             });
            return false;  // 폼 제출을 막음
        }

        // 유효성 검사 통과 시 폼을 제출
        $('#cargoRegisterForm').submit();
        

    });
    console.log(`${user}`);
    </script>
</body>
</html>