<%--
 * position-data/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- Output fields -->
	
<strong>
	<spring:message code="positionData.title"/>:
</strong>
	<jstl:out value="${positionData.title}"></jstl:out>
<br>

<strong>
	<spring:message code="positionData.description"/>:
</strong>
	<jstl:out value="${positionData.description}"></jstl:out>
<br>

<strong>
	<spring:message code="positionData.startDate"/>:
</strong>
	<jstl:out value="${positionData.startDate}"></jstl:out>
<br>

<strong>
	<spring:message code="positionData.endDate"/>:
</strong>
	<jstl:out value="${positionData.endDate}"></jstl:out>
<br>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="action.cancel"/>
