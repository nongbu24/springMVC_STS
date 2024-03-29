<%@page import="com.office.library.admin.member.AdminMemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/resources/css/admin/include/nav.css' />" rel="stylesheet" type="text/css">
<jsp:include page="./nav_js.jsp" />
<nav>
	<div id="nav_wrap">
		<%
		AdminMemberVO loginedAdminMemberVO = (AdminMemberVO) session.getAttribute("loginedAdminMemberVO");
		if (loginedAdminMemberVO != null) {
		%>
		<div class="menu">
			<ul>
				<li><a href="<c:url value='/admin/member/logoutConfirm' />">로그아웃</a></li>
				<li><a href="<c:url value='/admin/member/modifyAccountForm' />">계정 수정</a></li>
				<c:if test="${loginedAdminMemberVO.a_m_id eq 'super admin'}">
					<li><a href="<c:url value='/admin/member/listupAdmin' />">관리자 목록</a></li>
				</c:if>
				<li><a href="<c:url value='/book/admin/getRentalBooks' />">대출 도서</a></li>
				<li><a href="<c:url value='/book/admin/getAllBooks' />">전체 도서</a></li>
				<li><a href="<c:url value='/book/admin/getHopeBooks' />">희망 도서(입고 처리)</a></li>
				<li><a href="<c:url value='/book/admin/registerBookForm' />">도서 등록</a></li>
			</ul>
		</div>
		<%
		} else {
		%>
		<div class="menu">
			<ul>
				<li><a href="<c:url value='/admin/member/loginForm' />">로그인</a></li>
				<li><a href="<c:url value='/admin/member/createAccountForm' />">회원가입</a></li>
			</ul>
		</div>
		<%
		}
		%>
		<div class="search">
			<form action="<c:url value='/book/admin/searchBookConfirm' />" name="search_book_form" method="get">
				<input type="text" name="b_name" placeholder="Enter the name of the book you are looking for.">
				<input type="button" value="search" onclick="searchBookForm();">
			</form>
		</div>
	</div>
</nav>