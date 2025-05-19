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




.center-div{
	margin-left:30px;
	margin-top:30px;
	width:500px;
}
.hidden{
	display:none;
}

.form-label{
	white-space: nowrap;
	width: 220px;
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
                <form id="cargoRegisterForm" action="/cargo/cargoRegister" method="POST">
             
                    <div class="d-flex flex-column gap-3">
                    	
                    	
                        <!-- 회사명 -->
                        <div class="d-flex gap-2">
                            <span class="input-group-text">검색 회사</span>
                            <%-- 
                            <input type="text" class="form-control" id="compCd" name="compCd" required>
                            --%>
                            <span style="display: inline-block; width: 250px; margin-right: 10px;">
                                <select class="form-select" id="compCd" name="compCd">
                                    <c:choose>
                                        <%-- 관리자일 경우: 전체 회사 표시 --%>
                                        <c:when test="${user.userLevel eq '1'}">
                                            <c:forEach var="compInfo" items="${compInfoArr}">
                                                <option value="${compInfo.compCd}">${compInfo.compName}</option>
                                            </c:forEach>
                                        </c:when>

                                        <%-- 일반 유저일 경우: 본인 회사만 출력 --%>
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
                            </span>
                        </div>

                        <!-- 송장 번호 -->
                        <div class="d-flex gap-2">
                            <label for="trackingNo" class="form-label">송장 번호</label>
                            <input type="text" class="form-control" id="trackingNo" name="trackingNo" required>
                        </div>

                        <!-- 수취인 이름 -->
                        <div class="d-flex gap-2">
                            <label for="receiverName" class="form-label">수취인 이름</label>
                            <input type="text" class="form-control w-100" id="receiverName" name="receiverName" required>
                        </div>

                        <!-- 수취인 주소 -->
                        <div class="d-flex gap-2">
                            <label for="receiverAdd" class="form-label">수취인 주소</label>
                            <input type="text" class="form-control w-100" id="receiverAdd" name="receiverAdd" required>
                        </div>

                        <!-- 수취인 우편번호 -->
                        <div class="d-flex gap-2">
                            <label for="receiverZip" class="form-label">수취인 우편번호</label>
                            <input type="text" class="form-control w-100" id="receiverZip" name="receiverZip" required>
                        </div>

                        <!-- 수취인 전화번호 -->
                        <div class="d-flex gap-2">
                            <label for="receiverTel" class="form-label">수취인 전화번호</label>
                            <input type="text" class="form-control w-100" id="receiverTel" name="receiverTel" required>
                        </div>

                        <!-- 판매자 이름 -->
                        <div class="d-flex gap-2">
                            <label for="sellerName" class="form-label">판매자 이름</label>
                            <input type="text" class="form-control w-100" id="sellerName" name="sellerName" required>
                        </div>

                        <!-- 판매자 주소 -->
                        <div class="d-flex gap-2">
                            <label for="sellerAdd" class="form-label">판매자 주소</label>
                            <input type="text" class="form-control w-100" id="sellerAdd" name="sellerAdd" required>
                        </div>

                        <!-- 판매자 전화번호 -->
                        <div class="d-flex gap-2">
                            <label for="sellerTel" class="form-label">판매자 전화번호</label>
                            <input type="text" class="form-control w-100" id="sellerTel" name="sellerTel" required>
                        </div>
                        
                        <!-- 상품명 -->
                        <div class="d-flex gap-2">
                            <label for="no" class="form-label">상품명</label>
                            <input type="text" class="form-control w-100" id="goodsName" name="goodsName" required>
                        </div>
                        
                        <!-- 상품 개수 -->
                        <div class="d-flex gap-2">
                            <label for="qty" class="form-label">상품 개수</label>
                            <input type="text" class="form-control w-100" id="qty" name="qty" required>
                        </div>
                        
                        <!-- 상품 단가 -->
                        <div class="d-flex gap-2">
                            <label for="unitPrice" class="form-label">상품 단가</label>
                            <input type="text" class="form-control w-100" id="unitPrice" name="unitPrice" required>
                        </div>
						
                        <!-- 총중량 -->
                        <div class="d-flex gap-2">
                            <label for="gw" class="form-label">총 중량 (GW)</label>
                            <input type="text" class="form-control w-100" id="gw" name="gw" required>
                        </div>

                        <!-- 총중량 단위 -->
                        <div class="d-flex gap-2">
                            <label for="gwt" class="form-label">총 중량 단위 (GWT)</label>
                            <input type="text" class="form-control w-100" id="gwt" name="gwt" required>
                        </div>
                    </div>
                    
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
    </script>
</body>
</html>