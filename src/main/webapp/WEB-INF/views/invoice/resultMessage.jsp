<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>요금 계산 결과</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="alert alert-success" role="alert">
            <h4 class="alert-heading">요금 계산 결과</h4>
            <p>선택한 지역: ${regionName}</p>
            <p>선택한 무게: ${weight}g</p>
            <p>기본 요금: ${basePrice}원</p>
            <p>추가 금액: ${adPrice}원</p>
            <p><strong>총 요금: ${totalPrice}원</strong></p>
            <hr>
            <a href="/invoice/insertShopping" class="btn btn-primary">돌아가기</a>
        </div>
    </div>
</body>
</html>
