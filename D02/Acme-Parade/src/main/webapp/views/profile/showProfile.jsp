<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="profile.show" />
</p>

<strong><spring:message code="actor.name" />:</strong>
<jstl:out value="${actor.name}" />
<br>
<strong><spring:message code="actor.middleName" /></strong>
:
<jstl:out value="${actor.middleName}" />
<br>
<strong><spring:message code="actor.surname" /></strong>
:
<jstl:out value="${actor.surname}" />
<br>
<!-- PONER COMO FOTO -->
<strong><spring:message code="actor.photo" />:</strong>
<jstl:out value="${actor.photo}" />
<br>
<strong><spring:message code="actor.email" />:</strong>
<jstl:out value="${actor.email}" />
<br>
<strong><spring:message code="actor.phoneNumber" />:</strong>
<jstl:out value="${actor.phoneNumber}" />
<br>
<strong><spring:message code="actor.address" />:</strong>
<jstl:out value="${actor.address}" />
<br>
<jstl:if test="${reqURI eq 'brotherhood'}">
<strong><spring:message code="actor.title" />:</strong>
	<jstl:out value="${actor.title}" />
	<br>
<strong> <spring:message code="actor.stablishmentDate" />: </strong>
	<!-- TODO: Revisar porqué no muestra la fecha -->
	<jstl:out value="${actor.establishmentDate}" />
</jstl:if>
<br>
<br>
<jstl:if test="${reqURI eq 'admin'}">
<a href="profile/admin/export.do"><spring:message code="exportData" /></a>
</jstl:if>

<jstl:if test="${reqURI eq 'member'}">
<a href="profile/member/export.do"><spring:message code="exportData" /></a>
</jstl:if>

<jstl:if test="${reqURI eq 'brotherhood'}">
<a href="profile/brotherhood/export.do"><spring:message code="exportData" /></a>
</jstl:if>