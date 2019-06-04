<%--
serie/show.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<img src="${serie.banner}" />

<p>
	<strong><spring:message code="title" /></strong>:<jstl:out value="${serie.title}" />
</p>

<p>
	<strong><spring:message code="description" /></strong>:<jstl:out value="${serie.description}" />
</p>

<p>
	<strong><spring:message code="status" /></strong>:<jstl:out value="${serie.status}" />
</p>

<p>
	<strong><spring:message code="startDate" /></strong>:<jstl:out value="${serie.startDate}" />
</p>

<p>
	<jstl:if test="${serie.status == 'FINALIZED'}">
		<strong><spring:message code="endDate" /></strong>:<jstl:out value="${serie.endDate}" />
	</jstl:if>
</p>

<p>
	<strong><spring:message code="director" /></strong>:<jstl:out value="${serie.director}" />
</p>

<p>
	<strong><spring:message code="cast" /></strong>:<jstl:out value="${serie.cast}" />
</p>

<security:authorize access="hasRole('PUBLISHER')">
	<p>
		<a href="season/publisher/list.do?serieId=${serie.id}"><spring:message code="seasons" /></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('PUBLISHER')">
			<jstl:if test="${row.isDraft}">
				<a href="serie/publisher/edit.do?serieId=${row.id}"><spring:message code="edit"/></a>
			</jstl:if>
		</security:authorize>
	


