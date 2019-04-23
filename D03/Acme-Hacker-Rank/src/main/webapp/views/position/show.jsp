<%--
 * position/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Dom�nguez
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


<strong><spring:message code="title"/></strong>: <jstl:out value="${position.title }"/>
<br>
<strong><spring:message code="description"/></strong>: <jstl:out value="${position.description }"/>
<br>
<strong><spring:message code="deadline"/></strong>: <jstl:out value="${position.deadline }"/>
<br>
<strong><spring:message code="profile"/></strong>: <jstl:out value="${position.profile }"/>
<br>
<strong><spring:message code="skills"/></strong>: <jstl:out value="${position.skills }"/>
<br>
<strong><spring:message code="technologies"/></strong>: <jstl:out value="${position.technologies}"/>
<br>
<strong><spring:message code="salary"/></strong>: <jstl:out value="${position.salary }"/>
<br>
<strong><spring:message code="status"/></strong>: 
<jstl:if test="${locale eq 'es' }">
	<jstl:if test="${position.status eq 'SUBMITTED' }">
		<jstl:out value="ENVIADO" />
	</jstl:if>
	<jstl:if test="${position.status eq 'ACCEPTED' }">
		<jstl:out value="ACEPTADA"/>
	</jstl:if>
	<jstl:if test="${position.status eq 'CANCELLED' }">
		<jstl:out value="CANCELADA"/>
	</jstl:if>
</jstl:if>

<jstl:if test="${locale eq 'en' }">
	<jstl:out value="${position.status }"/>
</jstl:if>
<%-- Corregir con Spring message --%>
<br>
<strong><spring:message code="problems"/></strong>: 
<jstl:forEach var="i" items="${problems}">
	<jstl:out value="${i.title}"/>
	<br>
</jstl:forEach>

<jstl:out value="Sacar un link por cada problem para una lista m�s c�moda de informaci�n"/>

<jstl:if test="${position.status eq 'SUBMITTED'}">
	<a href="position/company/final.do?positionId=${position.id}"> <spring:message code="final" /> </a>
	<br>
	<br>
	<a href="position/company/edit.do?positionId=${position.id}"> <spring:message code="edit" /> </a>
</jstl:if>

<jstl:if test="${position.status eq 'ACCEPTED'}">
	<a href="position/company/cancel.do?positionId=${position.id}"> <spring:message code="canceled" /> </a>	
</jstl:if>