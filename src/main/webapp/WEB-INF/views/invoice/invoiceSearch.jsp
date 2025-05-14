<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>청구서 조회</title>

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
<style>
/* 페이지와 그리드의 크기 기본 설정 */
html, body {
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
</style>

</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
			<div class="container-fluid"
				style="margin-top: 30px; margin-bottom: 10px;">

				<div class="input-group mb-1">
					<span class="input-group-text">청구 기간</span>
					<div class="col-mb-2">
						<input type="text" class="form-control datepicker" size="15"
							id="from" placeholder="기간 시작" aria-label="기간 시작"
							autocomplete="off">
					</div>
					<span class="input-group-text">〜</span>
					<div class="col-mb-2">
						<input type="text" class="form-control datepicker" size="15"
							id="to" placeholder="기간 종료" aria-label="기간 종료" autocomplete="off">
					</div>

					<div style="margin-left: 15px; width: 250px;"
						class="col-sm-2 textarea-search">
						<textarea class="form-control form-control-md"></textarea>
					</div>
					<button class="btn btn-outline-dark" type="button" id="search">검색</button>
				</div>

				<br>
				<div class="col-auto">
					<span><input type="text" id="filterTextBox"
						class="form-control form-control-sm" placeholder="퀵 서치"
						style="width: 300px;"> </span>
					<hr>
				</div>

				<div id="myGrid" class="ag-theme-alpine"></div>

			</div>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
</body>
<script type="text/javascript">
	$(function() {
		// jquery datepicker 한국 로컬버젼
		$('.datepicker').datepicker(
				{
					closeText : "닫기",
					prevText : "&#x3C;전",
					nextText : "후&#x3E;",
					currentText : "당일",
					monthNames : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月",
							"8月", "9月", "10月", "11月", "12月" ],
					monthNamesShort : [ "1月", "2月", "3月", "4月", "5月", "6月",
							"7月", "8月", "9月", "10月", "11月", "12月" ],
					dayNames : [ "일요일", "월요일", "화요일", "수요일", "목요일", "금요일",
							"토요일" ],
					dayNamesShort : [ "<font color='red'>일</font>", "월", "화",
							"수", "목", "금", "<font color='blue'>토</font>" ],
					dayNamesMin : [ "<font color='red'>일</font>", "월", "화",
							"수", "목", "금", "<font color='blue'>토</font>" ],
					weekHeader : "주",
					dateFormat : "yy-mm-dd",
					firstDay : 0,
					isRTL : false,
					showMonthAfterYear : true,
					yearSuffix : "년"
				});
		// 초기 날짜의 값 설정
		$('#from').datepicker('setDate', new Date());
		$('#to').datepicker('setDate', new Date());

		// 그리드에 들어갈 칼럼 해더 정의
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
				},

				{
					headerName : "회사이름",
					field : "compName",
					width : 160,
					pinned : 'left',
					cellStyle: {color: 'blue', textDecoration: "underline",cursor: "pointer"  },
					
					cellRenderer: function(params) {
					    if (!params.data || !params.data.compCd) return '';

					    const from = document.getElementById('from').value;
					    const to = document.getElementById('to').value;

					    const href = '/invoice/detailFrm?compCd='+ params.data.compCd+'&from='+$('#from').val()+'&to='+$('#to').val();

					    return "<a href='" + href + "' style='color: blue; text-decoration: underline;'>" + params.data.compName + "</a>";
					  }
				},
				{
					headerName : "총 청구 금액",
					field : "totalPayment"
				},
				{
					headerName : "총 중량",
					field : "totalWeight",
					width : 160,
					pinned : 'left'
				},
				{
					headerName : "화물 갯수",
					field : "totalWeight"
				},
				{
					headerName : "청구서 다운로드",
					field : "",
					cellRenderer : function() {
						return '<button class="btn btn-outline-success btn-sm" type="button" style="margin-left:12px;">다운로드</button>';
					}
				} ];

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

			$.ajax({
				url : "/invoice/dataGrid",
				data : {
					from : $('#from').val(),
					to : $('#to').val(),
				},
				type : "get",
			}).done(function(res) {
				// 기존 행 모두 제거
				const allData = [];
			      gridApi.forEachNode(node => allData.push(node.data));
			      gridApi.applyTransaction({ remove: allData });
			      // 새로운 데이터 추가한다.
			      gridApi.applyTransaction({ add: res });
			      
			      gridDiv.style.height = (window.innerHeight - offset -150) + 'px';
			}).fail(function(e) {
				swal({
					title : "알림",
					text : "검증중에 에러가 발생했습니다.",
					icon : "warning",
				});
			});

		}

		// 검색시 그리드 기동
		$('#search').on('click', function(e) {
			loadGrid();
		})
	});
</script>
</html>