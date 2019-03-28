<%--
 * request/rejectMember.jsp
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




	
	



<form:form modelAttribute="request" action="request/brotherhood/reject.do" method="POST">

<!-- Hidden Fields -->
<form:hidden path="status" />
	<form:hidden path="parade"/>
	<form:hidden path="HLine"/>
	<form:hidden path="VLine"/>


<!-- Form options -->

	<input type="hidden" name="id" value="<jstl:out value='${request.id}' />" />
	<p>
		<spring:message code="request.reason" />
		<br><input type="text" name="reason" />
	</p>
	<input type="submit" name="reject" value="<spring:message code='request.reject' />" />
</form:form>

<acme:cancel url="welcome/index.do" code="cancel" />
