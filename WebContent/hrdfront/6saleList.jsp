<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="hrd.vo.SaleVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HRD Example</title>
    <link rel="stylesheet" href="table.css" />
    <link rel="stylesheet" href="layout.css">
</head>
<body>
<%@include file="header.jsp" %>
 <%
 @SuppressWarnings("unchecked")
 	List<SaleVO> list = (List<SaleVO>)request.getAttribute("list");
 
   DecimalFormat df = new DecimalFormat("#,### 원");  //"#,### 원" 은 처리조건에 따라 설정함.
 %>
<main>
    <h3>회원매출조회</h3>
    <table>
        <tr class="title">
            <th>회원번호</th>
            <th>회원성명</th>
            <th>고객등급</th>
            <th>매출</th>
        </tr>
<%
	for(SaleVO vo : list) {
%>        
        <tr class="center">
            <td><%= vo.getCustno() %></td>
            <td><%= vo.getCustname() %></td>
            <td><%= vo.getAgrade() %></td>
          <%--   <td><%= vo.getAsum() %></td> --%>
            <td  style="text-align: right; padding-right:15px;"><%= df.format(vo.getAsum()) %></td>  
            <!-- 일반적으로 금액 등 자릿수 맞춤은 오른쪽 정렬 -->
        </tr>
<%
	}
%>        
    </table>
</main>
<%@include file="footer.jsp" %>
</body>
</html>