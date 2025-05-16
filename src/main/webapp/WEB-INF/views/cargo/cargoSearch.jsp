<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화물 조회</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
   rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- AG Grid CSS -->
<link href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/styles/ag-grid.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/styles/ag-theme-alpine.min.css" rel="stylesheet">

<!-- jQuery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- AG Grid JS -->
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/dist/ag-grid-community.min.js"></script>

<!-- Bootstrap JS -->
<script
   src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
   href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/i18n/jquery-ui-i18n.min.js"></script>

<!-- SweetAlert -->
<script src="/resources/js/sweetalert.min.js"></script>

<style type="text/css">

/*
grid css
*/
#myGrid {
   width: 100%;
   flex-grow: 1;
   height: 100%;
   margin-top:25px;
}

.toolbar {
   margin-bottom: 5px;
}



/*
text area css
*/
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


/*
select css
*/

.form-select{
   width: 200px;
}

/*
modal css
*/

.th-label {
  width: 30%;
}

</style>

</head>
<body>
   <div class="wrap">
      <jsp:include page="/WEB-INF/views/common/header.jsp" />
      <main class="content d-flex">
         <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
         
         <%-- 검색창 (AG-grid) --%>
         <div class="container-fluid" style="margin-top:30px; margin-bottom:10px;">
            <div class="d-flex" style="gap:10px;">
               <select class="form-select" id="searchOption" aria-label="Default select example">
                  <option value="warehouse_moveId" selected>창고이동ID</option>
                  <option value="tracking_no">송장번호</option>
                  <option value="manage_no">사내관리번호 </option>
               </select>
               <div style="margin-left: 15px; width: 250px;" class="col-sm-2 textarea-search">
                     <textarea class="form-control form-control-md" id="inputText" placeholder="검색할 내용 입력"></textarea>
               </div>
               <button class="btn btn-outline-dark" type="button" id="search" >검색</button>
            </div>
            <div id=myGrid class="ag-theme-alpine"></div>
         </div>
         
         <%-- 상세정보 창 띄우기(모달) --%>

         <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailModalLabel">화물 상세 정보</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                   </div>
                   <div class="modal-body" id="modalContent">
                        <%-- 상세 내용(const contentHtml)이 여기에 들어감 --%>
                   </div>
                   <div class="modal-footer">
                     <button type="button" class="btn btn-primary" id="saveChangesBtn">수정</button>
    				 <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    </div>
                </div>
            </div>
         </div>

      </main>
      <jsp:include page="/WEB-INF/views/common/footer.jsp" />
   </div>
   <script>
      
      $(function() {
         
         //검색창
         $('#search').on('click',function(){
            var searchValue = $('#inputText').val();

            if (searchValue.trim() !== "") {
               //console.log('검색어:', searchValue);
               loadGrid();

            } else {
               //console.log('검색어 미입력');
            }
         });
         
         //헤더부분
         const columnDefs = [
            {
               headerName : '',
               field : "idx",
               minWidth : 50,
               width : 50,
               maxWidth : 50,
               pinned : 'left',
               headerCheckboxSelection : true,
               headerCheckboxSelectionFilteredOnly : true,
               checkboxSelection : true,
               pinned : 'left'
            },

            {
               headerName : "회사코드",
               field : "compCd",
               width : 160,
               pinned : 'left',
               
            },
            {
               headerName : "창고 이동 ID",
               field : "warehouseMoveid",
               pinned : 'left'
            },
            {
               headerName : "송장번호",
               field : "trackingNo",
               width : 160,
               pinned : 'left',
               onCellClicked : function(params) {
                    
                    //console.log(params);
                    //console.log(params.data);
                    
                    //클릭한 트래킹 넘버 가져오기
                    const trackingNo=params.data.trackingNo; 
                    console.log("선택한 trackingNo:", trackingNo);
                  
                    // AJAX로 상세 데이터 가져오기
                    $.ajax({
                      url: "/srchCargoDetail",
                      type: 'get',
                      data: { trackingNo: trackingNo },
                      success: function(detail) {
                        
                           console.log("상세 데이터:" +  detail);
                           console.log(detail.compCd);
                           console.log(detail.warehouseMoveid);
                           console.log(detail.manageNo);
                           console.log(detail.goodsName);
                           console.log(detail.no);
                           console.log(detail.unitPrice);

                         //모달 html에 넣을 데이터

					                         const contentHtml = 
					  			 '<table class="table table-bordered">' +
						         '<tr><th class="th-label">회사코드</th><td>' + detail.compCd + '</td></tr>' +
						         '<tr><th class="th-label">창고이동ID</th><td>' + detail.warehouseMoveid + '</td></tr>' +
						         '<tr><th class="th-label">사내 관리번호</th><td>' + detail.manageNo + '</td></tr>' +
						         '<tr><th class="th-label">상품명</th><td><input type="text" name="goodsName" class="form-control" value="' + detail.goodsName + '"></td></tr>' +
						         '<tr><th class="th-label">상품개수</th><td><input type="text" name="no" class="form-control" value="' + detail.no + '"></td></tr>' +
						         '<tr><th class="th-label">상품단가</th><td><input type="text" name="unitPrice" class="form-control" value="' + detail.unitPrice + '"></td></tr>' +
						         '<tr><th class="th-label">상품무게</th><td><input type="text" name="unitWeight" class="form-control" value="' + detail.unitWeight + '"></td></tr>' +
						         '<tr><th class="th-label">배송지 주소</th><td><input type="text" name="receiverAdd" class="form-control" value="' + params.data.receiverAdd + '"></td></tr>' +
						    '</table>';

                           $('#modalContent').html(contentHtml);
                           
                          // 모달 제목 업데이트
                          $('#detailModalLabel').html('<b>[화물 상세 정보] 송장번호 : '+ trackingNo +'</b>');
                           
                          // 모달 열기
                          const modal = new bootstrap.Modal(document.getElementById('detailModal'));
                          modal.show();
                           
                           
                      },
                      error: function() {
                          $('#modalContent').html(`<p class="text-danger">상세 정보를 불러오지 못했습니다.</p>`);
                      }
                    });
                  
                    
                    
               }
            },
            {
               headerName : "사내 관리번호",
               field : "manageNo",
               
            },
            {
               headerName : "받는이 이름",
               field : "receiverName",
               cellStyle: function(params) {                                                                           
                  return {                                                                           
                     'color': 'blue',                                                                           
                  }    
               }
            },
            {
               headerName : "받는이 주소",
               field : "receiverAdd"
            },
            {
               headerName : "셀러 이름",
               field : "sellerName"
            },
            {
               headerName : "셀러 주소",
               field : "sellerAdd"
            },
            {
               headerName : "중량",
               field : "gw"
            },
            {
               headerName : "화물갯수",
               field : "no"
            },
            ];
         
         // grid 옵션 정의
         const gridOptions = {
            // 해더
            columnDefs : columnDefs,
            // 33버젼으론 넘어가면서 기존 테마라고 설정해야된다.
            theme : "legacy",
            defaultColDef : {
               editable : false,
               flex : 1,
               minWidth : 75,
               resizable : true,
               filter : true,
            },
            // 다중행 선택 가능
            rowSelection : "multiple",
            debounceVerticalScrollbar : true,
            enableCellTextSelection : true,
            suppressRowClickSelection : true,

         };

         /*
         //AG-Gird 코드
         //let gridData=[];
         const gridOptions = {
            columnDefs : [ {
               headerName : "회사코드", 
               field : "compCd"
            }, {
               headerName : "창고이동ID",
               field : "warehouseMoveid"
            }, ],
            rowData : null, // 데이터를 데이터베이스에서 가져온 이후에 넣는다 
         };
         */
         //loadGrid(); //검색 눌렀을때만 작동하도록 변경 예정 
   
         // html로드된 순간 grid표시후 재 로드 X
         const gridDiv = document.getElementById('myGrid');
         const gridApi = agGrid.createGrid(gridDiv, gridOptions);
         
         
         function loadGrid(e) { //이벤트가 발생했을때 e이긴한데 지금은 발생하지 않고있음.
            
            //검색창에 입력된 값 가져오기
            let inputTextArr = $('#inputText').val().split(/\r?\n/).map(x => x.trim()).filter(x => x.length > 0);
            
            //모달에 들어가있던 값 초기화
            $('#detailModal').on('hidden.bs.modal', function () {
                $('#modalContent').empty(); // 모달 내용 비우기
                $('#detailModalLabel').text('화물 상세 정보'); // 제목 초기화
            });
            
            //검색했을때 동작
            $.ajax({
               url : "/srchCargo",
               data : {
                  searchValue: inputTextArr, 
                  searchOption: $("#searchOption").val()
               },
               traditional: true, // 배열로 보낼시 꼭 추가
               type : "get",
               success : function(res) {
                  // 기존 행 모두 제거
                  
                  const allData = [];
                     gridApi.forEachNode(node => allData.push(node.data));
                     gridApi.applyTransaction({ remove: allData });
                  
                     gridApi.applyTransaction({ add: res });
                     
                     gridDiv.style.height = "800px";
                  /*//gridData = res;
                  gridOptions.rowData = res;
                  console.log("서버 응답:", res);

                  agGrid.createGrid(document
                        .getElementById('member_grid'), gridOptions);
                  */
               },
               error : function(err) {
                  console.log("에러 발생:", err);
                  swal({
                     title : "알림",
                     text : "데이터 불러오기 실패",
                     icon : "warning",
                  });
               }
            });
         }
         $('#saveChangesBtn').on('click', function () {
             const goodsName = $('#modalContent input[name="goodsName"]').val();
             const no = $('#modalContent input[name="no"]').val();
             const unitPrice = $('#modalContent input[name="unitPrice"]').val();
             const unitWeight = $('#modalContent input[name="unitWeight"]').val();
             const receiverAdd = $('#modalContent input[name="receiverAdd"]').val();
             const trackingNo = $('#detailModalLabel').text().split("송장번호 : ")[1].split("]")[0].trim();

             $.ajax({
                 url: "/cargo/updateCargoDetails",
                 type: 'POST',
                 data: {
                     trackingNo: trackingNo,
                     goodsName: goodsName,
                     no: no,
                     unitPrice: unitPrice,
                     unitWeight: unitWeight,
                     receiverAdd: receiverAdd
                 },
                 success: function (response) {
                     if (response.success) {
                         swal({
                             title: "성공",
                             text: "수정이 완료되었습니다.",
                             icon: "success",
                         });
                         $('#detailModal').modal('hide');
                         loadGrid(); // 그리드 새로고침
                     } else {
                         swal({
                             title: "실패",
                             text: "수정에 실패했습니다.",
                             icon: "error",
                         });
                     }
                 },
                 error: function () {
                     swal({
                         title: "에러",
                         text: "서버 요청에 실패했습니다.",
                         icon: "warning",
                     });
                 }
             });
         });

      });
      
      
      
      
   </script>

</body>
</html>