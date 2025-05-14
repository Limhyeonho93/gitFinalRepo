<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- ag-Grid JS -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- ✅ ag-Grid JS -->
<script
	src="https://cdn.jsdelivr.net/npm/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>


<!-- Bootstrap & Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Custom CSS & JS -->
<link rel="stylesheet" href="/resources/css/layout.css">
<script src="/resources/js/layout.js" defer></script>

</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="content d-flex">
			<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
			
			
			<div class="container-fluid" style="margin-top:30px; margin-bottom:10px;">
				<div class="d-flex" style="gap:10px;">
					<textarea class="form-control" id="inputText" placeholder="검색할 내용 입력" style="width:300px; height:40px;"></textarea>
					
					<button class="btn btn-outline-dark" type="button" id="search" >검색</button>
				</div>
				<div id="member_grid" class="ag-theme-alpine" style="height: 500px; width: 100%; margin-top:30px;"></div>
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

			//loadGrid(); //검색 눌렀을때만 작동하도록 변경 예정 
	
			let gridCreated = false;
			
			function loadGrid(e) { //이벤트가 발생했을때 e 이긴한데 지금은 발생하지 않고있음.
				
				// 기존 그리드 제거(초기화)
			    member_grid.innerHTML = "";

				$.ajax({
					url : "/srchCargo",
					data : { keyword: $('#inputText').val() },
					type : "get",
					success : function(res) {
						//gridData = res;
						gridOptions.rowData = res;
						console.log("서버 응답:", res);

						agGrid.createGrid(document
								.getElementById('member_grid'), gridOptions);

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