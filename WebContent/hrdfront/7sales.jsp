<%@page import="hrd.vo.SaleVO"%>
<%@page import="hrd.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="hrd.dao.HrdDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HrdDao dao = HrdDao.getInstance();
	
	List<SaleVO> list = dao.getSales();
	request.setAttribute("list", list);
	pageContext.forward("6saleList.jsp");
%>