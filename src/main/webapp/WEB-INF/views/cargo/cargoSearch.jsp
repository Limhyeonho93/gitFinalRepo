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

</style>

</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
			
			
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
			
			
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<script>
		
		$(function() {
			
			//검색창
			$('#search').on('click',function(){
				var searchValue = $('#inputText').val();

				if (searchValue.trim() !== "") {
					console.log('검색어:', searchValue);
					loadGrid();

				} else {
					console.log('검색어 미입력');
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
						alert(params.data.trackingNo);
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
				
				let inputTextArr = $('#inputText').val().split(/\r?\n/).map(x => x.trim()).filter(x => x.length > 0);
				//let inputTextArr = $('#inputText').val().split(/\r?\n/);
				//let inputText = inputTextArr.join("','");
				//let searchOption="warehouse_moveId"; //샘플 데이터. 나중에 셀렉트 가능하게 바꿀것 
				
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

		});
	</script>

</body>
</html>