<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<nav class="side-navbar bg-sidebar-bg text-white">
		<div
			class="sidebar-header d-flex align-items-center p-3 border-bottom border-secondary">
			<h4 class="text-white mb-0">물류관리</h4>
		</div>

		<ul class="list-unstyled">
			<li class="sidebar-item">
				<a class="sidebar-link d-flex align-items-center"data-bs-toggle="collapse" href="#bondAir" role="button" aria-expanded="false"> 
					<span class="material-icons me-2">local_airport</span>
					화물관리
				</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu" id="bondAir">
					<li><a class="sidebar-link" href="#">1</a></li>
					<li><a class="sidebar-link" href="#">2</a></li>
					<li><a class="sidebar-link" href="#">3</a></li>
				</ul></li>
			<li class="sidebar-item">
				<a class="sidebar-link d-flex align-items-center" data-bs-toggle="collapse" href="#bondSea" role="button" aria-expanded="false"> 
					<span class="material-icons me-2">directions_boat</span>
					청구서 관리
				</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu" id="bondSea">
					<li><a class="sidebar-link" href="#">あ</a></li>
					<li><a class="sidebar-link" href="#">い</a></li>
					<li><a class="sidebar-link" href="#">う</a></li>
				</ul>
			</li>
			<li class="sidebar-item">
				<a class="sidebar-link d-flex align-items-center" data-bs-toggle="collapse" href="#userControll" role="button" aria-expanded="false"> 
					<span class="material-icons me-2">directions_boat</span>
					유저 관리
				</a>
				<ul class="collapse list-unstyled ps-4 sidebar-submenu" id="userControll">
					<li><a class="sidebar-link" href="#">あ</a></li>
					<li><a class="sidebar-link" href="#">い</a></li>
					<li><a class="sidebar-link" href="#">う</a></li>
				</ul>
			</li>
		</ul>
	</nav>
</body>
</html>