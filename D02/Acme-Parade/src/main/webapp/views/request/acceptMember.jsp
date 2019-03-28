<%--
 * request/acceptMember.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
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


<form:form modelAttribute="request" action="request/brotherhood/accept.do" method="POST">

<!-- Hidden Attributes -->

	
	<form:hidden path="status" />
	<form:hidden path="reason" />
	<form:hidden path="parade" />

<!-- Form options -->

	<input type="hidden" name="id" value="<jstl:out value='${request.id}' />" />
	<p>
	<spring:message code="request.hLine" />
	<br><input type="number" name="HLine" min="1" value="${request.HLine}">
</p>

<p>
	<spring:message code="request.vLine" />
	<br><input type="number" name="VLine" min="1" value="${request.VLine}">
</p>
	<input type="submit" name="accept" value="<spring:message code='request.accept' />" />
</form:form>

<acme:cancel url="welcome/index.do" code="cancel" />
