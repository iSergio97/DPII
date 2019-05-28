<%--
 * serie/show.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<img src="${serie.banner}" />
<br>
<strong><spring:message code="title" /></strong>
:
<jstl:out value="${serie.title}" />
<br>

<strong><spring:message code="description" /></strong>
:
<jstl:out value="${serie.description}" />
<br>

<strong><spring:message code="status" /></strong>
:
<jstl:out value="${serie.status}" />
<br>

<strong><spring:message code="startDate" /></strong>
:
<jstl:out value="${serie.startDate}" />
<br>

<jstl:if test="${serie.status == 'FINALIZED'}">

<strong><spring:message code="endDate" /></strong>

<jstl:out value="${serie.endDate}" />
</jstl:if>
<br>

<strong><spring:message code="director" /></strong>
:
<jstl:out value="${serie.director}" />
<br>

<strong><spring:message code="cast" /></strong>
:
<jstl:out value="${serie.cast}" />
<br>

<strong><a href="season/all/list.do"><spring:message code="seasons" /></a></strong>
:





<!-- 

<security:authorize access="hasRole('USER')">
<p>
	<a href="comment/user/create.do"><spring:message code="comment" /></a>
</p>
</security:authorize>

<security:authorize access="hasRole('CRITIC')">
<p>
	<a href="critique/critic/create.do"><spring:message code="critique" /></a>
</p>
</security:authorize>


 -->