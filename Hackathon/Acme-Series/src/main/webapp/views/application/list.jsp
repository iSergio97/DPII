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

<display:table name="applications" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag"
		sort="list" defaultsort="3">
	<acme:sortablecolumn value="${row.moment}" code="application.moment" />
	<acme:sortablecolumn value="${row.description}" code="application.description" />
	<acme:sortablecolumn value="${row.status}" code="application.status" />
	<acme:sortablecolumn value="${row.serie.title}" code="application.series" />
	<acme:sortablecolumn value="${row.administrator.userAccount.username}" code="application.administrator" />
	<acme:actioncolumn url="application/administrator,publisher/show.do?applicationId" value="${row.id}" code="action.show"/>
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:if test="${row.status eq 'PENDING'}">
			<acme:actioncolumn url="application/administrator/accept.do?applicationId" value="${row.id}" code="action.accept"/>
			<acme:actioncolumn url="application/administrator/reject.do?applicationId" value="${row.id}" code="action.reject"/>
		</jstl:if>
	</security:authorize>
</display:table>
