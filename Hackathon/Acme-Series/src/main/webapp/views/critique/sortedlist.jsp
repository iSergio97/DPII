<%--
application/list.jsp

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

<display:table name="critiques" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag"
		sort="list" defaultsort="3">
	<acme:sortablecolumn value="${row.moment}" code="application.moment" property="moment" />
	<acme:sortablecolumn value="${row.description}" code="application.description" property="description" />
	<acme:sortablecolumn value="${row.score}" code="application.score" property="score" />
	<acme:sortablecolumn value="${row.critic}" code="application.critic" property="critic" />
	<acme:actioncolumn url="critique/critic/show.do?critiqueId" value="${row.id}" code="action.show"/>
</display:table>
