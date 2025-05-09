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
	

</head>
<body>
	<div>
		<form>
			<input type="text" id="search" placeholder="검색할 내용 입력">
			<button type="button" onclick="searchCargo(this)">검색</button>
		</form>
	</div>
	<div id="member_grid" class="ag-theme-alpine" style="height:500px;width:100%;"></div>
	
	<script>
	function searchCargo(){
		var searchValue=$('#search').val();
		
		if(searchValue.trim() !== ""){
			console.log('검색어:', searchValue);

		} else {
			console.log('검색어 미입력');
		}
	}
	
	
	$(function(){	
		//AG-Gird 코드
		//let gridData=[];
		const gridOptions = {
			  columnDefs: [
			    { headerName: "회사코드", field: "compCd" },
			    { headerName: "창고이동ID", field: "warehouseMoveid" },
			  ],
			  rowData: null, // 데이터를 데이터베이스에서 가져온 이후에 넣는다 
		};
		
		loadGrid();
		
		function loadGrid(e){	//이벤트가 발생했을때 e 이긴한데 지금은 발생하지 않고있음.
			
			if (gridOptions && gridOptions.api) { // 기본 값 초기화
	            gridOptions.api.destroy();
	        }
		
			$.ajax({
				url:"/srchCargo",
				//data : 필요x
				type:"get",
				success:function(res){
					//gridData = res;
		            gridOptions.rowData = res;
		            console.log("서버 응답:", res);

		            agGrid.createGrid(document.getElementById('member_grid'), gridOptions);

				},
				error:function(err){
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