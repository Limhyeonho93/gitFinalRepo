<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>창고 작업</title>

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
                height: 600px;
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
        </style>

    </head>

    <body>
        <div class="wrap">
            <jsp:include page="/WEB-INF/views/common/header.jsp" />
            <main class="content d-flex">
                <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
                <div class="container-fluid" style="margin-top: 30px; margin-bottom: 10px;">
                    <h3>창고 작업</h3>

                    <div class="input-group mb-1">
                        <span class="input-group-text">작업날짜</span>
                        <div class="col-mb-2">
                            <input type="text" class="form-control datepicker" size="15" id="searchDate"
                                autocomplete="off">
                        </div>


                        <button class="btn btn-outline-dark" type="button" id="search">검색</button>
                    </div>

                    <div class="d-flex justify-content-end mb-2">
                        <input type="text" id="filterTextBox" class="form-control form-control-sm" placeholder="퀵 서치"
                            style="width: 300px;">
                    </div>

                    <div id="myGrid" class="ag-theme-alpine"></div>

                </div>
            </main>
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </body>
    <script type="text/javascript">
        $(function () {
            // jquery datepicker 한국 로컬버젼
            $('.datepicker').datepicker(
                {
                    closeText: "닫기",
                    prevText: "&#x3C;전",
                    nextText: "후&#x3E;",
                    currentText: "당일",
                    monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月",
                        "8月", "9月", "10月", "11月", "12月"],
                    monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月",
                        "7月", "8月", "9月", "10月", "11月", "12月"],
                    dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일",
                        "토요일"],
                    dayNamesShort: ["<font color='red'>일</font>", "월", "화",
                        "수", "목", "금", "<font color='blue'>토</font>"],
                    dayNamesMin: ["<font color='red'>일</font>", "월", "화",
                        "수", "목", "금", "<font color='blue'>토</font>"],
                    weekHeader: "주",
                    dateFormat: "yy-mm-dd",
                    firstDay: 0,
                    isRTL: false,
                    showMonthAfterYear: true,
                    yearSuffix: "년"
                });
            // 초기 날짜의 값 설정
            $('#searchDate').datepicker('setDate', new Date());


            // 그리드에 들어갈 칼럼 해더 정의
            const columnDefs = [
                {
                    headerName: '',
                    field: "idx",
                    minWidth: 50,
                    width: 50,
                    maxWidth: 50,
                    pinned: 'left',
                    headerCheckboxSelection: true,
                    headerCheckboxSelectionFilteredOnly: true,
                    checkboxSelection: true,
                },

                {
                    headerName: "창고이름",
                    field: "wareName",
                    width: 160,
                    pinned: 'left',

                },
                {
                    headerName: "총 중량",
                    field: "totalWeight",
                    pinned: 'left',
                    maxWidth: 100

                },
                {
                    headerName: "화물 수량 총합",
                    field: "totalQty",
                    pinned: 'left',
                    maxWidth: 130
                },
                {
                    headerName: "송장 건수",
                    field: "allCount",
                    pinned: 'left',
                    maxWidth: 130
                },
                {
                    headerName: "작업시작",
                    field: "",
                    cellRenderer: function (params) {
                        var yymmdd = $('#searchDate').val().replace(/-/g, '').slice(2);

                        if (params.data.allCount == params.data.tbCount) {
                            var completeBtn = $('<button>', {
                                class: 'btn btn-outline-secondary btn-sm',
                                disabled: true,
                                style: 'margin-left:12px;'
                            }).html('<i class="fa-solid fa-check text-success"></i> 완료');

                            return completeBtn[0]; // jQuery 객체 → DOM 반환
                        } else {
                            var startBtn = $('<button>', {
                                class: 'btn btn-outline-primary btn-sm',
                                type: 'button',
                                style: 'margin-left:12px;'
                            }).text('작업시작');

                            startBtn.on('click', function () {
                                startBondedTask(yymmdd, params.data.wareCd);
                            });

                            return startBtn[0];
                        }
                    }
                },
                {
                    headerName: "반입 갯수",
                    field: "inCount"
                },
                {
                    headerName: "반입 작업",
                    field: "",
                    cellRenderer: function (params) {
                        var yymmdd = $('#searchDate').val().replace(/-/g, '').slice(2);

                        if (params.data.tbCount == params.data.inCount && params.data.tbCount != 0) {
                            var completeBtn = $('<button>', {
                                class: 'btn btn-outline-secondary btn-sm',
                                disabled: true,
                                style: 'margin-left:12px;'
                            }).html('<i class="fa-solid fa-check text-success"></i> 반입 완료');

                            return completeBtn[0]; // jQuery 객체 → DOM 반환
                        } else if(params.data.tbCount == 0){
                        	
                        } else {
                            var startBtn = $('<button>', {
                                class: 'btn btn-outline-success btn-sm',
                                type: 'button',
                                style: 'margin-left:12px;'
                            }).text('반입');

                            startBtn.on('click', function () {
                                updateBondedTask(yymmdd, params.data.wareCd, "imp");
                            });

                            return startBtn[0];
                        }
                    }
                },
                {
                    headerName: "반출 갯수",
                    field: "outCount"
                },
                {
                    headerName: "반출 작업",
                    field: "",
                    cellRenderer: function (params) {

                        var yymmdd = $('#searchDate').val().replace(/-/g, '').slice(2);

                        if (params.data.tbCount == params.data.outCount && params.data.tbCount != 0) {
                            var completeBtn = $('<button>', {
                                class: 'btn btn-outline-secondary btn-sm',
                                disabled: true,
                                style: 'margin-left:12px;'
                            }).html('<i class="fa-solid fa-check text-success"></i> 반출 완료');

                            return completeBtn[0]; // jQuery 객체 → DOM 반환
                        } else if(params.data.tbCount != 0 && params.data.inCount != 0){
                            var startBtn = $('<button>', {
                                class: 'btn btn-outline-danger btn-sm',
                                type: 'button',
                                style: 'margin-left:12px;'
                            }).text('반출');

                            startBtn.on('click', function () {
                                updateBondedTask(yymmdd, params.data.wareCd, "exp");
                            });

                            return startBtn[0];
                        	
                        } else {
                        }
                    }
                }
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

            loadGrid();
            // 전역으로 grid관리 첫번쨰만 생성해서 메모리 절약
            const gridDiv = document.getElementById('myGrid');
            const gridApi = agGrid.createGrid(gridDiv, gridOptions);

            // grid를 발생시키는 함수
            function loadGrid() {
                var yymmdd = $('#searchDate').val().replace(/-/g, '').slice(2);
                const headerHeight = document.querySelector('.navbar')?.offsetHeight || 60;
                const searchBoxHeight = document.querySelector('.input-group')?.offsetHeight || 60;
                const footerHeight = 0;
                const gridTopMargin = 50;

                const offset = headerHeight + searchBoxHeight + footerHeight + gridTopMargin;
                // 날짜 글자로 yymmdd로 변경
                $.ajax({
                    url: "/bonded/searchGrid",
                    data: {
                        searchDate: yymmdd,
                    },
                    type: "get",
                }).done(function (res) {
                    // 기존 행 모두 제거
                    const allData = [];
                    gridApi.forEachNode(node => allData.push(node.data));
                    gridApi.applyTransaction({ remove: allData });
                    // 새로운 데이터 추가한다.
                    gridApi.applyTransaction({ add: res });

                    gridDiv.style.height = (window.innerHeight - offset - 200) + 'px';
                }).fail(function (e) {
                    swal({
                        title: "알림",
                        text: "검증중에 에러가 발생했습니다.",
                        icon: "warning",
                    });
                });

            }

            // 검색시 그리드 기동
            $('#search').on('click', function (e) {
                loadGrid();
            })
            // 창고 작업 시작
            function startBondedTask(yymmdd, wareCd) {
                $.ajax({
                    url: "/bonded/insertBond",
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