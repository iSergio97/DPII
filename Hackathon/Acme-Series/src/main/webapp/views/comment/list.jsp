<%--
comment/list.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="comments" id="row" pagesize="5" class="displaytag">
	<acme:column value="${row.moment}" code="comment.moment" />
	<acme:column value="${row.text}" code="comment.text" />
	<acme:column value="${row.score}" code="comment.score" />
	<acme:column value="${row.user.userAccount.username}" code="comment.user" />
	<acme:column value="${row.serie.title}" code="comment.series" />
	<acme:actioncolumn url="comment/user/edit.do?commentId" value="${row.id}" code="action.edit"/>
</display:table>
