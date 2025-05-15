<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>화물 일괄 등록</title>


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


<style>

.center-div{
	margin-left:30px;
	margin-top:30px;
}


</style>

</head>
<body>

<div class="wrap">
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<main class="content d-flex">
		<jsp:include page="/WEB-INF/views/common/leftSideBar.jsp" />
		
		    <div class="center-div">
			      <form action="/cargo/cargoBatchReg" method="post" enctype="multipart/form-data">
			          <h3 class="text-center mb-4">화물 등록</h3>
			
			          <div class="btn-group w-100 mb-3">
			              <button class="btn btn-outline-primary w-50">단건 등록</button>
			              <button class="btn btn-primary w-50">일괄 등록</button>
			          </div>
			
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
	
</body>
</html>