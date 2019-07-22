<%--
 * item/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form action="serie/public/list.do" method="GET">
	<spring:message code="keyword" />
	: <input type="text" value="" id="keyword" name="keyword" />
	<button type="submit">
		<spring:message code="search" />
	</button>
</form>

<display:table name="series" id="row" pagesize="5" class="displaytag">

	<acme:column value="${row.title}" code="series.title"/>
	<acme:column value="${row.description}" code="series.description"/>
	<acme:column value="${row.status}" code="series.status"/>

	<acme:actioncolumn url="serie/publisher/show.do?serieId" value="${row.id}" code="action.show" />
	<security:authorize access="hasRole('PUBLISHER')">
		<jstl:if test="${row.isDraft}">
			<acme:actioncolumn url="serie/publisher/edit.do?serieId" value="${row.id}" code="action.edit" />
			<acme:actioncolumn url="application/publisher/create.do?serieId" value="${row.id}" code="action.series.apply" />
		</jstl:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('PUBLISHER')">
	<p>
		<a href="serie/publisher/create.do"><spring:message code="create" /></a>
	</p>
</security:authorize>
