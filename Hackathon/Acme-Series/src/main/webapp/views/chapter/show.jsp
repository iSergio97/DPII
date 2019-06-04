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


<strong><spring:message code="number" /></strong>
:
<jstl:out value="${chapter.number}" />
<br>

<strong><spring:message code="title" /></strong>
:
<jstl:out value="${chapter.title}" />
<br>

<strong><spring:message code="description" /></strong>
:
<jstl:out value="${chapter.description}" />
<br>

<strong><spring:message code="duration" /></strong>
:
<jstl:out value="${chapter.duration}" />
<br>

<strong><spring:message code="releaseDate" /></strong>
:
<jstl:out value="${chapter.releaseDate}" />
<br>

<security:authorize access="hasRole('PUBLISHER')">
<p>
	<a href="chapter/publisher/edit.do?chapterId=${chapter.id}"><spring:message code="edit" /></a>
</p>
</security:authorize>



