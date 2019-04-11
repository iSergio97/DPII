<%--
 * education-data/show.jsp
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
	<spring:message code="educationData.degree"/>:
</strong>
	<jstl:out value="${educationData.degree}"></jstl:out>
<br>

<strong>
	<spring:message code="educationData.institution"/>:
</strong>
	<jstl:out value="${educationData.institution}"></jstl:out>
<br>

<strong>
	<spring:message code="educationData.mark"/>:
</strong>
	<jstl:out value="${educationData.mark}"></jstl:out>
<br>

<strong>
	<spring:message code="educationData.startDate"/>:
</strong>
	<jstl:out value="${educationData.startDate}"></jstl:out>
<br>

<strong>
	<spring:message code="educationData.endDate"/>:
</strong>
	<jstl:out value="${educationData.endDate}"></jstl:out>
<br>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="action.cancel"/>
