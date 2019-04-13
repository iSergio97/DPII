<%--
 * miscellaneous-data/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Jos� Antonio Dom�nguez G�mez
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
	<spring:message code="miscellaneousData.freeText"/>:
</strong>
	<jstl:out value="${miscellaneousData.freeText}"></jstl:out>
<br>

<strong>
	<spring:message code="miscellaneousData.attachments"/>:
</strong>
	<jstl:out value="${miscellaneousData.attachments}"></jstl:out>
<br>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="action.cancel"/>
