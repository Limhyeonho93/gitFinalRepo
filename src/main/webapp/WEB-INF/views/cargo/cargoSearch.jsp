<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>화물 조회</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <!-- Custom CSS & JS -->
        <link rel="stylesheet" href="/resources/css/layout.css">
        <script src="/resources/js/layout.js" defer></script>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- AG Grid CSS -->
        <link href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/styles/ag-grid.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/styles/ag-theme-alpine.min.css"
            rel="stylesheet">

        <!-- jQuery -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <!-- AG Grid JS -->
        <script src="https://cdn.jsdelivr.net/npm/ag-grid-community@33.2.4/dist/ag-grid-community.min.js"></script>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
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
                height: 600px;
                margin-top: 25px;
            }

            .toolbar {
                margin-bottom: 5px;
            }

            /*
   text area css(검색 텍스트 창)
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
            .form-select {
                width: 200px;
            }

            /*
   modal css
   */
            .th-label {
                width: 30%;
                vertical-align: middle;
                white-space: nowrap;
            }

            input[readonly] {
                background-color: #f0f0f0;
                color: #555;
                cursor: not-allowed;
            }

            input[readonly]:focus {
                background-color: #f0f0f0 !important;
                outline: none;
                box-shadow: none;
                cursor: not-allowed;
            }

            #updateGoodsModal {
                z-index: 1060;
                /* Bootstrap 기본 modal z-index는 1055 */
            }
        </style>

    </head>

    <body>

        <div class="wrap">
            <jsp:include page="/WEB-INF/views/common/header.jsp" />
            <main class="content d-flex">
                <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />

                <%-- 검색창 (AG-grid) --%>
                    <div class="container-fluid" style="margin-top: 30px; margin-bottom: 10px;">
                        <div>
                            <h3 class="mb-4">화물 조회</h3>
                        </div>
                        <div class="d-flex" style="gap: 10px;">
                            <!-- 검색창 관련 버튼들은 왼쪽에 위치 -->
                            <div class="d-flex" style="gap: 10px;">
                                <select class="form-select" id="searchOption" aria-label="Default select example">
                                    <option value="warehouse_moveId">창고이동ID</option>
                                    <option value="tracking_no" selected>송장번호</option>
                                    <option value="manage_no">사내관리번호</option>
                                </select>
                                <div style="margin-left: 15px; width: 250px;" class="col-sm-2 textarea-search">
                                    <textarea class="form-control form-control-md" id="inputText"
                                        placeholder="검색할 내용 입력"></textarea>
                                </div>
                                <button class="btn btn-outline-dark" type="button" id="search">검색</button>
                            </div>

                            <!-- 체크목록 추출 버튼은 오른쪽에 위치 -->
                            <div class="ms-auto">
                                <button class="btn btn-outline-dark" type="button" id="exportSelected">체크목록 추출</button>
                                <button class="btn btn-danger" type="button" id="deleteSelected">체크목록 삭제</button>
                            </div>
                        </div>

                        <div id="myGrid" class="ag-theme-alpine"></div>
                    </div>

                    <%-- 상세'정보' 창 띄우기(모달) --%>

                        <div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel"
                            aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="detailModalLabel">화물 상세 정보</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="닫기"></button>
                                    </div>
                                    <div class="modal-body" id="modalContent">
                                        <%-- 상세 내용(const contentHtml)이 여기에 들어감 --%>
                                    </div>
                                    <div class="modal-footer d-flex justify-content-between">
                                        <div>
                                            <button type="button" class="btn btn-outline-secondary" id="goodsUpdPopBtn"
                                                data-tracking-no="">화물 상세정보 변경</button>
                                        </div>
                                        <div>
                                            <button type="button" class="btn btn-primary" id="updateBtn">수정</button>
                                            <button type="button" class="btn btn-danger" id="deleteModalBtn">삭제</button>
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">닫기</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%-- 상세'수정' 창 띄우기(모달) --%>
                            <div class="modal fade" id="updateGoodsModal" tabindex="-1"
                                aria-labelledby="updateGoodsModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="updateGoodsModalLabel">화물 정보 수정</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="닫기"></button>
                                        </div>
                                        <div class="modal-body" id="updateModalContent">
                                            <%-- 상세 수정(const contentHtml) 내용이 여기에 들어감 --%>
                                        </div>
                                        <div class="modal-footer d-flex justify-content-end">
                                            <button type="button" class="btn btn-primary"id="goodsUpdateBtn">수정</button>
                                            <button type="button" class="btn btn-danger" id="goodsdeleteBtn">삭제</button>
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                        </div>
                                    </div>
                                </div>
                            </div>


            </main>
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>

        <script type="text/javascript">

            $(function () {
                // 팝업창 트래킹넘버
                let selectedManageNo = '';
                let goodsUpdList = [];

                // 검색창 클릭 시 동작
                $('#search').on('click', function () {
                    var searchValue = $('#inputText').val();
                    if (searchValue.trim() !== "") {
                        loadGrid(); // 검색 시 그리드 새로 로드
                    } else {
                        // 검색어 미입력 시 처리
                    }
                });

                // 그리드 설정
                const columnDefs = [
                    {
                        headerName: '',
                        field: "idx",
                        minWidth: 50,
                        width: 50,
                        maxWidth: 50,
                        headerCheckboxSelection: true,
                        headerCheckboxSelectionFilteredOnly: true,
                        checkboxSelection: true,
                        pinned: 'left'
                    },
                    {
                        headerName: "회사코드",
                        field: "compCd",
                        width: 160,
                        pinned: 'left',
                    },
                    {
                        headerName: "창고이동ID",  // 회사코드와 송장번호 사이에 창고이동ID 배치
                        field: "warehouseMoveid",
                        width: 160,
                        pinned: 'left'
                    },
                    {
                        headerName: "송장번호",
                        field: "trackingNo",
                        cellStyle: function () {
                            return {
                                'color': 'blue',
                                'textDecoration': "underline",
                                'cursor': "pointer"
                            };
                        },
                        width: 160,
                        pinned: 'left',
                        onCellClicked: function (params) {
                            const trackingNo = params.data.trackingNo;
                            const manageNo=params.data.manageNo;


                            const contentHtml =
                                '<table class="table table-bordered">' +
                                '<tr><th class="th-label">회사코드</th><td><input type="text" name="compCd" class="form-control" value="' + params.data.compCd + '" readonly></td></tr>' +
                                '<tr><th class="th-label">창고이동ID</th><td><input type="text" name="warehouseMoveid" class="form-control" value="' + params.data.warehouseMoveid + '" readonly></td></tr>' +
                                '<tr><th class="th-label">사내 관리번호</th><td><input type="text" name="manageNo" class="form-control" value="' + params.data.manageNo + '" readonly></td></tr>' +

                                '<tr><th class="th-label">수취인 이름</th><td><input type="text" name="receiverName" class="form-control" value="' + params.data.receiverName + '"></td></tr>' +
                                '<tr><th class="th-label">수취인 주소</th><td><input type="text" name="receiverAdd" class="form-control" value="' + params.data.receiverAdd + '"></td></tr>' +
                                '<tr><th class="th-label">수취인 우편번호</th><td><input type="text" name="receiverZip" class="form-control" value="' + params.data.receiverZip + '"></td></tr>' +
                                '<tr><th class="th-label">수취인 전화번호</th><td><input type="text" name="receiverTel" class="form-control" value="' + params.data.receiverTel + '"></td></tr>' +
                                '<tr><th class="th-label">판매자 이름</th><td><input type="text" name="sellerName" class="form-control" value="' + params.data.sellerName + '"></td></tr>' +
                                '<tr><th class="th-label">판매자 주소</th><td><input type="text" name="sellerAdd" class="form-control" value="' + params.data.sellerAdd + '"></td></tr>' +
                                '<tr><th class="th-label">판매자 전화번호</th><td><input type="text" name="sellerTel" class="form-control" value="' + params.data.sellerTel + '"></td></tr>' +
                                '<tr>' +
                                '<th class="th-label">총 중량</th>' +
                                '<td colspan="2">' +
                                '<div class="d-flex">' +
                                '<input type="number" step="0.01" name="gw" class="form-control me-2" value="' + params.data.gw + '" style="flex: 2;">' +
                                '<input type="text" name="gwt" class="form-control" maxlength="3" value="' + params.data.gwt + '" style="flex: 1;">' +
                                '</div>' +
                                '</td>' +
                                '</tr>' +
                                '<tr><th class="th-label">화물 개수</th><td><input type="number" maxlength="5" name="no" class="form-control" value="' + params.data.no + '"></td></tr>' +
                                '<tr><th class="th-label">배송 중지 여부</th><td><input type="text" name="deliveryStop" class="form-control" value="' + params.data.deliveryStop + '"></td></tr>' +
                                '</table>';

                            $('#modalContent').html(contentHtml);

                            // 모달 제목 업데이트
                            $('#detailModalLabel').html('<b>[화물 상세 정보] 송장번호 : ' + trackingNo + '</b>');

                            // 화물상세수정 버튼에 trackingNo 값을 넣어줌
                            $('#goodsUpdPopBtn').attr('data-tracking-no', trackingNo);
                            selectedManageNo = manageNo;
                            // 모달 열기
                            const modal = new bootstrap.Modal(document.getElementById('detailModal'));
                            modal.show();



                        }
                    },
                    {
                        headerName: "사내 관리번호",
                        field: "manageNo",
                    },
                    {
                        headerName: "받는이 이름",
                        field: "receiverName",
                    },
                    {
                        headerName: "받는이 주소",
                        field: "receiverAdd"
                    },
                    {
                        headerName: "셀러 이름",
                        field: "sellerName"
                    },
                    {
                        headerName: "중량",
                        field: "gw"
                    },
                    {
                        headerName: "화물갯수",
                        field: "no"
                    },
                ];

                // 그리드 옵션 정의
                const gridOptions = {
                    columnDefs: columnDefs,
                    theme: 'legacy',
                    defaultColDef: {
                        editable: false,
                        flex: 1,
                        minWidth: 75,
                        resizable: true,
                        filter: true,
                    },
                    rowSelection: "multiple",
                    debounceVerticalScrollbar: true,
                    enableCellTextSelection: true,
                    suppressRowClickSelection: true,
                };

                // 그리드 생성
                const gridDiv = document.getElementById('myGrid');
                const gridApi = agGrid.createGrid(gridDiv, gridOptions);

                function loadGrid() {
                    let inputTextArr = $('#inputText').val().split(/\r?\n/).map(x => x.trim()).filter(x => x.length > 0);

                    $('#detailModal').on('hidden.bs.modal', function () {
                        $('#modalContent').empty();
                        $('#detailModalLabel').text('화물 상세 정보');
                    });

                    $.ajax({
                        url: "/srchCargo",
                        data: { searchValue: inputTextArr, searchOption: $("#searchOption").val() },
                        traditional: true,
                        type: "get",
                        success: function (res) {
                            const allData = [];
                            gridApi.forEachNode(node => allData.push(node.data));
                            gridApi.applyTransaction({ remove: allData });
                            gridApi.applyTransaction({ add: res });
                            gridDiv.style.height = "600px";
                        },
                        error: function (err) {
                            console.log("에러 발생:", err);
                            swal({ title: "알림", text: "데이터 불러오기 실패", icon: "warning" });
                        }
                    });
                }

                // '화물 메인'의 수정 버튼 클릭 시
                $('#updateBtn').on('click', function () {

                    const trackingNo = $('#detailModalLabel').text().split(': ')[1].trim();
                    const compCd = $('#modalContent input[name="compCd"]').val();
                    const warehouseMoveid = $('#modalContent input[name="warehouseMoveid"]').val();
                    const manageNo = $('#modalContent input[name="manageNo"]').val();
                    const receiverName = $('#modalContent input[name="receiverName"]').val();
                    const receiverAdd = $('#modalContent input[name="receiverAdd"]').val();
                    const receiverZip = $('#modalContent input[name="receiverZip"]').val();
                    const receiverTel = $('#modalContent input[name="receiverTel"]').val();
                    const sellerName = $('#modalContent input[name="sellerName"]').val();
                    const sellerAdd = $('#modalContent input[name="sellerAdd"]').val();
                    const sellerTel = $('#modalContent input[name="sellerTel"]').val();
                    const gw = parseFloat($('#modalContent input[name="gw"]').val()) || 0;
                    const gwt = $('#modalContent input[name="gwt"]').val();
                    const no = parseInt($('#modalContent input[name="no"]').val()) || 0;
                    const deliveryStop = $('#modalContent input[name="deliveryStop"]').val();

                    if (isNaN(no) || isNaN(gw)) {
                        swal("경고", "중량과 화물 개수는 숫자여야 합니다.", "warning");
                        return;
                    }

                    $.ajax({
                        url: '/cargo/updateCargoDetails',
                        type: 'POST',
                        data: {
                            trackingNo: trackingNo,
                            compCd: compCd,
                            warehouseMoveid: warehouseMoveid,
                            manageNo: manageNo,
                            receiverName: receiverName,
                            receiverAdd: receiverAdd,
                            receiverZip: receiverZip,
                            receiverTel: receiverTel,
                            sellerName: sellerName,
                            sellerAdd: sellerAdd,
                            sellerTel: sellerTel,
                            gw: gw,
                            gwt: gwt,
                            no: no,
                            deliveryStop: deliveryStop

                        },
                        success: function (result) {
                            if (result > 0) {
                                swal("성공", "화물 정보가 수정되었습니다.", "success");
                                /*
                                const modalInstance = bootstrap.Modal.getInstance(document.getElementById('detailModal'));//현재 열려 있는 모달 인스턴스를 가져옴
                                modalInstance.hide();// 모달 닫기
                                $('#search').click(); // 재조회
                                
                                */
                            } else {
                                swal("실패", "수정에 실패했습니다.", "error");
                            }
                        },
                        error: function () {
                            swal("오류", "서버 요청 중 오류가 발생했습니다.", "error");
                        }
                    });
                });

            //========================='화물 상세 수정' 시작========================

                //'화물 상세 수정' 버튼 누를 시 동작 (cargoGoods 수정)
                $(document).on('click', '#goodsUpdPopBtn', function () { // 이렇게 해야 두번 세번 클릭해도 정상적으로 데이터 가져옴
                    const manageNo = selectedManageNo;
                    const trackingNo = $('#goodsUpdPopBtn').attr('data-tracking-no');
                
                    let tabs = '';
                    let tabContents = '';
                    // AJAX로 상세 데이터 가져오기
                    $.ajax({
                        url: "/srchCargoDetail",
                        type: 'get',
                        data: { manageNo: manageNo },
                        success: function (goodsList) {
                            goodsUpdList = goodsList;
                     console.log(goodsUpdList);
                            
                            // 모달 제목 업데이트
                            $('#updateGoodsModalLabel').html('<b>[상품 상세 조회] 송장번호 : ' + trackingNo + '</b>');

                            // 모달 열기
                            const modal = new bootstrap.Modal(document.getElementById('updateGoodsModal'));
                            modal.show();

                            // 모달 내용 불러오기
                            $('#updateModalContent').html('');
                            renderTabs(goodsUpdList);
                            if(goodsUpdList.length < 2){
                            	$('#goodsdeleteBtn').hide();
                            }else{
                            	$('#goodsdeleteBtn').show();
                            }
                        },
                        error: function () {
                            $('#updateModalContent').html('<p class="text-danger">상세 정보를 불러오지 못했습니다.</p>');
                        }
                    });
                });

                //탭 데이터 넣기
                function renderTabs(goodsUpdList) {
                    var tabs = '';
                    var tabContents = '';

                    for (var i = 0; i < goodsUpdList.length; i++) {
                        var item = goodsUpdList[i];
                        var activeClass = (i == 0) ? 'active' : '';
                        var showClass = (i == 0) ? 'show' : '';
                        var tabId = 'tab' + i;

                        // 탭 버튼
                        tabs += '<li class="nav-item" role="presentation">' +
                            '<button class="nav-link ' + activeClass + '" type="button" data-tab-id="' + tabId + '"data-seq="' + item.seq + '">' + //item.seq == cargoGoods의 인덱스 값.
                            (item.goodsName ? item.goodsName : '상품' + (i + 1)) +
                            '</button>' +
                            '</li>';
                  
                       console.log('Rendering tab for item seq: ', item.seq);
 
                        // 탭 내용
                        tabContents += '<div class="tab-pane fade ' + showClass + ' ' + activeClass + '" id="' + tabId + '">' +
                            '<table class="table table-bordered">' +
                            '<tr><th class="th-label">회사코드</th><td><input type="text" name="compCd" class="form-control" value="' + item.compCd + '" readonly></td></tr>' +
                            '<tr><th class="th-label">창고이동ID</th><td><input type="text" name="warehouseMoveid" class="form-control" value="' + item.warehouseMoveid + '" readonly></td></tr>' +
                            '<tr><th class="th-label">사내 관리번호</th><td><input type="text" name="manageNo" class="form-control" value="' + item.manageNo + '" readonly></td></tr>' +
                            '<tr><th class="th-label">상품명</th><td><input type="text" name="goodsName" class="form-control" value="' + item.goodsName + '"></td></tr>' +
                            '<tr><th class="th-label">상품단가</th><td><input type="text" name="unitPrice" class="form-control" value="' + item.unitPrice + '"></td></tr>' +
                            '<tr><th class="th-label">상품개수</th><td><input type="text" name="qty" class="form-control" value="' + item.qty + '"></td></tr>' +
                            '<tr><th class="th-label">중량</th><td><input type="text" name="unitWeight" class="form-control" value="' + item.unitWeight + '"></td></tr>' +
                            '</table>' +
                            '</div>';
                    }

                    var contentHtml = '<ul class="nav nav-tabs" role="tablist" id="tabList">' + tabs + '</ul>' +
                        '<div class="tab-content">' + tabContents + '</div>';

                    $('#updateModalContent').html(contentHtml);
                }

                // 탭 클릭 이벤트
                $(document).on('click', '#tabList button.nav-link', function () {
                    const tabId = $(this).data('tab-id');
                    showTab(tabId, this);
                    
                });

                // 탭 전환 기능
                function showTab(tabId, clickedBtn) {
                    $('.nav-link').removeClass('active');
                    $(clickedBtn).addClass('active');

                    $('.tab-pane').removeClass('active show');
                    $('#' + tabId).addClass('active show');
                }
                
                
                // '화물 상세 수정'의 '수정' 버튼을 눌렀을 때 동작
                $('#goodsUpdateBtn').on('click', function () {

                    const trackingNo = $('#updateGoodsModalLabel').text().split(': ')[1].trim();
                    const seq=$('#tabList .nav-link.active').data('seq'); // 현재 활성화 된 탭 버튼에 data-seq로 저장해놓은 시퀀스를 가져옴
               
                    //const manageNo=selectedManageNo;
                    const compCd = $('.tab-pane.active').find('input[name="compCd"]').val();// 활성화 된 탭에는 tab-pane.active클래스가 붙어있음 여기의 값만 가져옴
                    const goodsName = $('.tab-pane.active').find('input[name="goodsName"]').val(); 
                    const unitPrice = $('.tab-pane.active').find('input[name="unitPrice"]').val();
                    const qty =  $('.tab-pane.active').find('input[name="qty"]').val();
                    const unitWeight = $('.tab-pane.active').find('input[name="unitWeight"]').val();
                    
                    if (isNaN(unitPrice) || isNaN(qty) || isNaN(unitWeight) ) {
                        swal("경고", "상품단가, 상품개수, 중량은 숫자여야 합니다.", "warning");
                        return;
                    }

                    $.ajax({
                        url: '/cargo/updCargoGoodsDetails',
                        type: 'POST',
                        data: {
                            trackingNo: trackingNo,
                            seq:seq,
                            //manageNo:manageNo,
                            compCd:compCd,
                            goodsName: goodsName,
                            unitPrice: unitPrice,
                            qty: qty,
                            unitWeight: unitWeight,
                        },
                        success: function (result) {
                            if (result > 0) {
                                
                                //수정된 내용으로 다시 불러오기
                                $('#goodsUpdPopBtn').trigger('click');
                                //renderTabs(goodsUpdList);
                                
                                //현재 활성화된 탭으로 다시 이동 시켜줌 // 지금 로직대로라면 하단 두 줄은 실행이 안됨. 생각 필요
                                const updatedTab = $('#tabList .nav-link[data-seq="' + seq + '"]');
                                showTab(updatedTab.data('tab-id'), updatedTab);
                                
                                swal("성공", "화물 정보가 수정되었습니다.", "success");
                            } else {
                                swal("실패", "수정에 실패했습니다.", "error");
                            }
                        },
                        error: function () {
                            swal("오류", "서버 요청 중 오류가 발생했습니다.", "error");
                        }
                    });
                });
                
                // '화물 상세 수정'의 '삭제' 버튼을 눌렀을 때 동작
                $('#goodsdeleteBtn').on('click', function () {
                   const trackingNo = $('#updateGoodsModalLabel').text().split(': ')[1].trim();
                    const seq=$('#tabList .nav-link.active').data('seq'); // 현재 활성화 된 탭 버튼에 data-seq로 저장해놓은 시퀀스를 가져옴
                    const compCd = $('.tab-pane.active').find('input[name="compCd"]').val();
                    
                    swal({
                        title: "정말 삭제하시겠습니까?",
                        text: "이 작업은 되돌릴 수 없습니다.",
                        icon: "warning",
                        buttons: {
                            cancel: {
                                text: "취소",
                                value: false,
                                visible: true,
                                closeModal: true
                            },
                            confirm: {
                                text: "삭제",
                                value: true,
                                visible: true,
                                closeModal: true
                            }
                        }
                    }).then(function(val) {
                        if (val) { // 삭제 버튼을 눌렀을 경우
                            $.ajax({
                                url: '/cargo/delCargoGoodsServlet',
                                type: 'POST',
                                data: {
                                    trackingNo: trackingNo,
                                    seq: seq,
                                    compCd:compCd
                                },
                                success: function (result) {
                                    if (result > 0) {
                                        swal("성공", "화물 정보가 삭제되었습니다.", "success");
                                        
                                        //삭제된 내용으로 갱신
                                        $('#goodsUpdPopBtn').trigger('click');
                                    } else {
                                        swal("실패", "삭제에 실패했습니다.", "error");
                                    }
                                },
                                error: function () {
                                    swal("오류", "서버 요청 중 오류가 발생했습니다.", "error");
                                }
                            });
                        }
                    });
                });
                
                
                
                //=============================='화물 상세 수정' 끝==============================


                //화물 메인 삭제
                $('#deleteModalBtn').on('click', function () {
                    const trackingNo = $('#detailModalLabel').text().split(': ')[1].trim(); // 또는 data-attribute 추천

                    swal({
                        title: "정말 삭제하시겠습니까?",
                        text: "이 작업은 되돌릴 수 없습니다.",
                        icon: "warning",
                        buttons: ["취소", "삭제"],
                        dangerMode: true,
                    }).then((willDelete) => {
                        if (willDelete) {
                            $.ajax({
                                url: "/cargo/deleteCargo",
                                type: "POST", // 또는 DELETE
                                data: { trackingNo: trackingNo },
                                success: function (res) {
                                    if (res.success) {
                                        swal("삭제 완료", "화물 정보가 삭제되었습니다.", "success");
                                        bootstrap.Modal.getInstance(document.getElementById('detailModal')).hide();
                                        $('#search').click(); // 그리드 재조회
                                    } else {
                                        swal("실패", "삭제에 실패했습니다.", "error");
                                    }
                                },
                                error: function () {
                                    swal("오류", "서버와의 통신 중 문제가 발생했습니다.", "error");
                                }
                            });
                        }
                    });
                });


                // 체크목록 추출 버튼 클릭 시 동작
                $('#exportSelected').on('click', function () {
                    // 선택된 행들 가져오기
                    const selectedNodes = gridApi.getSelectedNodes();
                    if (selectedNodes.length === 0) {
                        swal("알림", "선택된 항목이 없습니다.", "warning");
                        return;
                    }

                    // 선택된 데이터 추출
                    const selectedData = selectedNodes.map(node => node.data);

                    // 데이터 출력 (콘솔에 출력하거나 다른 방식으로 처리할 수 있음)
                    console.log("선택된 데이터:", selectedData);

                    // 예시: 데이터를 CSV 형식으로 변환하여 다운로드
                    const csvContent = convertToCSV(selectedData);
                    downloadCSV(csvContent, "checked_data.csv");
                });

                // 데이터를 CSV 형식으로 변환하는 함수
                function convertToCSV(data) {
                    const header = Object.keys(data[0]);
                    const rows = data.map(row =>
                        header.map(field => `"${row[field] || ''}"`).join(',')
                    );
                    return [header.join(','), ...rows].join('\n');
                }

                // CSV 파일 다운로드 함수
                function downloadCSV(content, filename) {
                    const blob = new Blob([content], { type: 'text/csv;charset=utf-8;' });
                    const link = document.createElement('a');
                    if (link.download !== undefined) {  // for compatibility with browsers
                        const url = URL.createObjectURL(blob);
                        link.setAttribute('href', url);
                        link.setAttribute('download', filename);
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);
                    }
                }
                // 체크목록 삭제버튼 클릭 시
                $('#deleteSelected').on('click', function () {

                    // 선택된 행들 가져오기
                    let trackingNoArr = [];
                    gridApi.getSelectedRows().forEach(function (row) {
                        trackingNoArr.push(row.trackingNo);
                    });
                    // 선택된 항목이 없을 때
                    if (trackingNoArr.length == 0) {
                        swal("알림", "선택된 항목이 없습니다.", "warning");
                        return;
                    }

                    swal({
                        title: "선택한 항목을 삭제하시겠습니까?",
                        text: "이 작업은 되돌릴 수 없습니다",
                        icon: "warning",
                        buttons: ["취소", "삭제"],
                        dangerMode: true
                    }).then((willDelete) => {
                        if (willDelete) {
                            // 사용자가 삭제를 확인한 경우, AJAX로 서버에 삭제 요청
                            $.ajax({
                                url: "/cargo/DeleteMultiCargo",
                                type: "POST",
                                traditional: true, //배열 전송을 위함
                                data: { trackingNos: trackingNoArr },
                                success: function (res) {
                                    if (res.success) {
                                        swal("삭제 완료", `${trackingNoArr.length}건이 삭제되었습니다.`, "success");
                                        $('#search').click();  // 그리드 재조회
                                    } else {
                                        swal("실패", "삭제에 실패하였습니다.", "error");
                                    }
                                },
                                error: function () {
                                    // 서버 오류시   
                                    swal("실패", "서버 요청중 오류가 발생하였습니다", "error");
                                }
                            });
                        }
                    })
                });

            });


        </script>
    </body>

    </html>