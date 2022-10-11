<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HRD Example</title>
<link rel="stylesheet" href="table.css">
<link rel="stylesheet" href="layout.css">
</head>
<body>
	<%@include file="header.jsp"%>
	<main>
		<h4 style="text-align: center;">쇼핑몰 회원관리 프로그램</h4>
		<p>
			쇼핑몰 회원정보와 회원매출정보 데이터베이스를 구축하고 회원관리 프로그램을 작성합니다.<br>
			프로그램 작성 순서
		</p>
		<ol>
			<li>회원정보 테이블을 생성한다.</li>
			<li>매출정보 테이블을 생성한다.</li>
			<li>회원정보, 매출정보 테이블에 제시된 문제지의 참조 데이터를 추가 생성한다.</li>
			<li>회원정보 입력 화면 프로그램을 작성한다.</li>
			<li>회원정보 조회 프로그램을 작성한다.</li>
			<li>회원 매출 정보 조회 프로그램을 작성한다.</li>
		</ol>
	</main>
	<%@include file="footer.jsp"%>
</body>
</html>