<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>화물 추적</title>

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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <style>
            /* 페이지와 그리드의 크기 기본 설정 */
            html,
            body {
                height: 100%;
                margin: 0;
                font-family: Arial, sans-serif;
            }

            #myGrid {
                width: 100%;
                flex-grow: 1;
                height: 100%;
            }

            .toolbar {
                margin-bottom: 5px;
            }

            .textarea-search {
                position: relative;
                display: block;
                height: 38px;
            }

            .textarea-search textarea {
                height: 34px;
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
            #myGridModal {
   				height: 300px;
			}
        </style>

    </head>

    <body>
        <div class="wrap">
            <jsp:include page="/WEB-INF/views/common/header.jsp" />
            <main class="content d-flex">
                <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
                <div class="container-fluid" style="margin-top: 30px; margin-bottom: 10px;">
                    <h3>화물 추적</h3>

                    <div class="input-group mb-1">
                    	<span>
                        <select class="form-select" id="searchOption" aria-label="Default select example" style="display: inline-block; width: 250px; margin-right: 10px;">
                            <option value="warehouse_moveId" selected>창고이동ID</option>
                            <option value="tracking_no">송장번호</option>
                            <option value="manage_no">사내관리번호</option>
                        </select>
                        </span>
                        <div style="margin-left: 15px; width: 250px;" class="col-sm-2 textarea-search">
                            <textarea class="form-control form-control-md" id="searchValue"
                                placeholder="검색할 내용 입력"></textarea>
                        </div>


                        <button class="btn btn-outline-dark" type="button" id="search">검색</button>
                    </div>

                    <div class="d-flex justify-content-end mb-2">
                        <input type="text" id="filterTextBox" class="form-control form-control-sm" placeholder="퀵 서치"
                            style="width: 300px;">
                    </div>

                    <div id="myGrid" class="ag-theme-alpine"></div>

                </div>
                <!-- Modal -->
                <div class="modal fade-lg" id="trackingModal" tabindex="-1" aria-labelledby="trackingModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="trackingModalLabel">화물 상세 이력</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div id="myGridModal" class="ag-theme-alpine"></div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal"
                                    style="width:90px;">닫기</button>

                            </div>
                        </div>
                    </div>
                </div>
                <!-- Modal -->
            </main>
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </body>
    <script type="text/javascript">
        $(function () {
            // 그리드에 들어갈 칼럼 해더 정의
            const columnDefs = [
                {
                    headerName: "회사이름",
                    field: "compName",
                    pinned: 'left',

                },
                {
                    headerName: "창고이동ID",
                    field: "warehouseMoveid",
                    pinned: 'left',

                },
                {
                    headerName: "송장번호",
                    field: "trackingNo",
                    pinned: 'left',
                    cellStyle: function() {
                        return { color: 'blue',
                            textDecoration: 'underline',
                            cursor: 'pointer'
                        };
                    },
                    onCellClicked: function (params) {
                    	if (!params.data.manageNo) {
                    	    swal("알림", "관리번호가 없습니다.", "warning");
                    	    return;
                    	}
                        $.ajax({
                            url: "/tracking/trackingDetail",
                            data: {
                                manageNo: params.data.manageNo,
                            },
                            type: "get",
                        })
                            .done(function (result) {
                                // 모달 그리드 초기화 후 데이터 바인딩
                                const oldData = [];
                                gridModalApi.forEachNode(node => oldData.push(node.data));
                                gridModalApi.applyTransaction({ remove: oldData });
                                gridModalApi.applyTransaction({ add: result });

                                trackingModal.show();
                            }).fail(function (err) {
                                console.error("모달 데이터 조회 실패", err);

                            });
                        trackingModal.show();
                    },
                },
                {
                    headerName: "관리번호",
                    field: "manageNo",
                    pinned: 'left',
                },
                {
                    headerName: "최근 화물 상태",
                    field: "stateTitle",

                },
                {
                    headerName: "갱신일",
                    field: "stateChange"
                },
            ];

            const columnModalDefs = [
                {
                    headerName: "창고 ID",
                    field: "warehouseMoveid",
                },
                {
                    headerName: "관리번호",
                    field: "manageNo",
                },
                {
                    headerName: "화물 상태",
                    field: "stateTitle",
                },
                {
                    headerName: "갱신일",
                    field: "stateChange"
                },
            ];

            // grid 옵션 정의
            const gridOptions = {
                // 해더
                columnDefs: columnDefs,
                // 33버젼으론 넘어가면서 기존 테마라고 설정해야된다.
                theme: "legacy",
                defaultColDef: {
                    editable: false,
                    flex: 1,
                    minWidth: 75,
                    resizable: true,
                    filter: true,
                },
                // 다중행 선택 가능
                rowSelection: "multiple",
                debounceVerticalScrollbar: true,
                enableCellTextSelection: true,
                suppressRowClickSelection: true,
                onGridReady: function (params) {
                    $('#filterTextBox').on('input', function () {
                        const value = $(this).val();
                        params.api.setGridOption("quickFilterText", value);
                    });

                }

            };
            const gridModalOptions = {
                columnDefs: columnModalDefs,
                theme: "legacy",
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

            // 2. 모달용 grid 생성
            const gridModalDiv = document.getElementById('myGridModal');
            const gridModalApi = agGrid.createGrid(gridModalDiv, gridModalOptions);


            // 전역으로 grid관리 첫번쨰만 생성해서 메모리 절약
            const gridDiv = document.getElementById('myGrid');
            const gridApi = agGrid.createGrid(gridDiv, gridOptions);

            // grid를 발생시키는 함수
            function loadGrid() {
                const headerHeight = document.querySelector('.navbar')?.offsetHeight || 60;
                const searchBoxHeight = document.querySelector('.input-group')?.offsetHeight || 60;
                const footerHeight = 0;
                const gridTopMargin = 50;

                const offset = headerHeight + searchBoxHeight + footerHeight + gridTopMargin;

                // 검색 내용
                let searchValArr = $('#searchValue').val().split(/\r?\n/).map(x => x.trim()).filter(x => x.length > 0);
                // 날짜 글자로 yymmdd로 변경
                $.ajax({
                    url: "/tracking/trackingGrid",
                    data: {
                           searchValue: searchValArr,  // <- key 이름을 "searchValue"로 명확히
                    	   searchOption: $('#searchOption').val(),
                    },
                    traditional: true,

                    type: "get",
                }).done(function (res) {
                    // 기존 행 모두 제거
                    const allData = [];
                    gridApi.forEachNode(node => allData.push(node.data));
                    gridApi.applyTransaction({ remove: allData });
                    // 새로운 데이터 추가한다.
                    gridApi.applyTransaction({ add: res });

                    gridDiv.style.height = (window.innerHeight - offset - 150) + 'px';
                }).fail(function (e) {
                    swal({
                        title: "알림",
                        text: "검색중에 에러가 발생했습니다.",
                        icon: "warning",
                    });
                });

            }

            // modal변수선언
            var trackingModal = new bootstrap.Modal($('#trackingModal'), {
                keyboard: false,
                backdrop: false
            })

            // 검색시 그리드 기동
            $('#search').on('click', function (e) {
                loadGrid();
            })
            // 창고 작업 시작
            function startBondedTask(yymmdd, wareCd) {
                $.ajax({
                    url: "/tracking/insertBond",
                    data: {
                        searchDate: yymmdd,
                        wareCd: wareCd,
                    },
                    type: "get",
                }).done(function (res) {
                    loadGrid();

                    if (res === "success") {
                        alert("화물 창고 등록 성공했습니다.");
                    } else {
                        alert("화물 창고 등록 실패했습니다.");
                    }
                }).fail(function (e) {
                    loadGrid();

                    swal({
                        title: "알림",
                        text: "검증중에 에러가 발생했습니다.",
                        icon: "warning",
                    });
                });

            };
            // 반입 반출 시작
            function updateBondedTask(yymmdd, wareCd, updateColumn) {

                $.ajax({
                    url: "/bonded/updateBonded",
                    data: {
                        searchDate: yymmdd,
                        wareCd: wareCd,
                        updateColumn: updateColumn,

                    },
                    type: "get",
                }).done(function (res) {
                    loadGrid();
                    var rseStr = "반입";
                    if (updateColumn == "exp") {
                        rseStr = "반출";
                    }
                    if (res === "success") {

                        alert(rseStr + " 작업 성공했습니다.");
                    } else {
                        alert(rseStr + " 작업 실패했습니다.");
                    }
                }).fail(function (e) {
                    loadGrid();

                    swal({
                        title: "알림",
                        text: "검증중에 에러가 발생했습니다.",
                        icon: "warning",
                    });
                });

            };

        });


    </script>

    </html>