<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>회사 등록</title>

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
        </style>

    </head>

    <body>
        <div class="wrap">
            <jsp:include page="/WEB-INF/views/common/header.jsp" />
            <main class="content d-flex">
                <jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
                <div class="container-fluid" style="margin-top: 30px; margin-bottom: 10px;">
                    <h3>회사 등록</h3>
                     <c:if test="${user.userLevel eq '1'}">
                    	<div class="d-flex justify-content-end mb-2">
                        	<button id="btnAddCompany" class="btn btn-primary me-2">
                            	<i class="fas fa-plus"></i> 추가
                        	</button>
                        	<button id="btnDeleteCompany" class="btn btn-danger">
                            	<i class="fas fa-minus"></i> 삭제
                        	</button>
                    	</div>
                     </c:if>
                    <div id="myGrid" class="ag-theme-alpine"></div>

                </div>
                <!-- 회사 등록/수정 모달 -->
                <div class="modal fade" id="companyModal" tabindex="-1" aria-labelledby="companyModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-md">
                        <div class="modal-content">
                            <div class="modal-header bg-primary text-white">
                                <h5 class="modal-title" id="companyModalLabel">회사 정보</h5>
                            </div>
                            <div class="modal-body">
                                <form id="companyForm">
                                    <div class="mb-3">
                                        <label for="comp_cd" class="form-label">회사코드</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="comp_cd" name="comp_cd">
                                            <button class="btn btn-outline-secondary" type="button"
                                                id="btnCheckCompCd">중복 확인</button>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="comp_name" class="form-label">회사명</label>
                                        <input type="text" class="form-control" id="comp_name" name="comp_name">
                                    </div>
                                    <div class="mb-3">
                                        <label for="comp_addr" class="form-label">회사주소</label>
                                        <input type="text" class="form-control" id="comp_addr" name="comp_addr">
                                    </div>
                                    <div class="mb-3">
                                        <label for="comp_zip" class="form-label">회사우편번호</label>
                                        <input type="text" class="form-control" id="comp_zip" name="comp_zip">
                                    </div>
                                    <div class="mb-3">
                                        <label for="comp_tel" class="form-label">회사전화번호</label>
                                        <input type="text" class="form-control" id="comp_tel" name="comp_tel">
                                    </div>
                                    <div class="mb-3">
                                        <label for="comp_div" class="form-label">회사구분</label> <select
                                            class="form-select" id="comp_div" name="comp_div">
                                            <option value="S">셀러</option>
                                            <option value="L">물류회사</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" id="saveCompanyBtn" class="btn btn-primary">저장</button>
                                <button type="button" class="btn btn-secondary" id="modalClose"
                                    data-bs-dismiss="modal">닫기</button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </body>
    <script type="text/javascript">
        $(function () {
            const companyModal = new bootstrap.Modal(document.getElementById('companyModal'));
            let isEditMode = false;       // 등록(true)/수정(false) 모드 여부
            let isCompCdChecked = false;  // 회사코드 중복 확인 여부

            // + 버튼 클릭 시: 등록 모드
            $('#btnAddCompany').on('click', function () {
                $('#companyModalLabel').text('회사 등록');
                $('#companyForm')[0].reset(); // 폼 초기화
                $('#comp_cd').prop('readonly', false); // 등록 시 회사코드 수정 가능
                isEditMode = false;                         // 등록 모드
                isCompCdChecked = false;                    // 중복확인 초기화
                companyModal.show();                        // 모달 열기
            });
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
                    headerName: "회사코드",
                    field: "comp_cd",
                    maxWidth: 200,

                    cellStyle: function () {
                        return {
                            color: 'blue',
                            textDecoration: 'underline',
                            cursor: 'pointer'
                        };
                    },
                    onCellClicked: function (params) {
                        $('#companyModalLabel').text('회사 정보');
                        $('#companyForm')[0].reset(); // 폼 초기화

                        $('#comp_cd').val(params.data.comp_cd).prop('readonly', true);;
                        $('#comp_name').val(params.data.comp_name);
                        $('#comp_addr').val(params.data.comp_addr);
                        $('#comp_zip').val(params.data.comp_zip);
                        $('#comp_tel').val(params.data.comp_tel);
                        $('#comp_div').val(params.data.comp_div);
                        isEditMode = true;                         // 수정 모드
                        isCompCdChecked = true;                    // 수정 시 중복확인 생략
                        companyModal.show();
                    },

                },
                {
                    headerName: "회사구분",
                    field: "comp_div",
                    maxWidth: 100,
                    cellRenderer: function (params) {

                        if (params.data.comp_div == 'S') {
                            return '셀러';
                        } else {
                            return '물류회사';
                        }
                    }
                },
                {
                    headerName: "회사이름",
                    field: "comp_name",

                },
                {
                    headerName: "회사주소",
                    field: "comp_addr",
                },
                {
                    headerName: "회사우편번호",
                    field: "comp_zip",
                    maxWidth: 140,

                },
                {
                    headerName: "등록일",
                    field: "reg_date",
                    maxWidth: 230
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

            loadGrid();
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
                // 날짜 글자로 yymmdd로 변경
                $.ajax({
                    url: "/user/getCustomerGrid",
                    data: {},
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
                        text: "검증중에 에러가 발생했습니다.",
                        icon: "warning",
                    });
                });

            }

            var compModal = new bootstrap.Modal($('#companyModal'));

            // 회사코드 중복 확인 버튼
            $('#btnCheckCompCd').on('click', function () {
                const compCd = $('#comp_cd').val().trim();

                if (!compCd) {
                    alert('회사코드를 입력하세요.');
                    return;
                }

                $.ajax({
                    url: '/user/checkCompCd',
                    type: 'get',
                    data: { compCd: compCd },
                }).done(function (res) {
                    if (res === 'available') {
                        alert('사용 가능한 코드입니다.');
                        isCompCdChecked = true;
                    } else {
                        alert('이미 사용 중인 코드입니다.');
                        isCompCdChecked = false;
                    }
                }).fail(function (e) {
                    alert('중복 확인 중 오류 발생');
                    isCompCdChecked = false;
                });
            });
            // 저장 버튼 클릭 시
            $('#saveCompanyBtn').on('click', function () {
				let mode = isEditMode ? 'update' : 'insert';
                if (!isEditMode && !isCompCdChecked) {
                    alert('회사코드 중복 확인이 필요합니다.');
                    return;
                }

                $.ajax({
                    url: '/user/saveCompany',
                    type: 'get',
                    data: {
                    	 compCd: $('#comp_cd').val().trim(),
                         compName: $('#comp_name').val().trim(),
                         compAddr: $('#comp_addr').val().trim(),
                         compZip: $('#comp_zip').val().trim(),
                         compTel: $('#comp_tel').val().trim(),
                         compDiv: $('#comp_div').val(),
                         mode:mode
                    },
                }).done(function (res) {
                	if (res === 'success') {
                        alert(isEditMode ? '수정 완료 했습니다.' : '등록 완료 했습니다.');
                    } else {
                        alert(isEditMode ? '수정 실패 했습니다.' : '등록 실패 했습니다.');
                    }
                    companyModal.hide();
                    loadGrid();
                }).fail(function () {
                    alert('회사 등록에 실패했습니다.');
                });


            });
            $('#modalClose').on('click', function () {
                companyModal.hide();
            });


            $('#btnDeleteCompany').on('click', function () {
                compCdArr = [];
                gridApi.getSelectedRows().forEach(function(row){
                	compCdArr.push(row.comp_cd);
                });
                
                if(compCdArr.length <= 0){
                	alert("삭제할 회사를 체크해주세요");
                	return;
                }
                
                $.ajax({
                	  url: '/user/deleteCustomerInfo',
                	    type: 'POST',
                	    data: {
                	        compCdArr: compCdArr
                	    },
                	    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                	    headers: {
                	        'x-requested-with': 'XMLHttpRequest',
                	        'x-csrf-token': 'your_csrf_token_here'
                	    },
                	    cache: false,
                	    traditional: true,
                }).done(function(res){
                	if (res === 'success') {
                        alert('삭제 완료 했습니다.');
                    } else {
                   	 	alert('삭제에 실패했습니다.');
                    }
                	loadGrid();
                }).fail(function(res){
                	 alert('삭제에 실패했습니다.');
                });

            });

        });




    </script>

    </html>