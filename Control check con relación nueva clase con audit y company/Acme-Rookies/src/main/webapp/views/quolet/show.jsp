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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<acme:field value="${quolet.body}" code="body" />
	<spring:message code="picture" />
	: <a href="${quolet.picture}" target="_blank"> <spring:message code="quoletImage" />
	</a>
</div>

<security:authorize access="hasRole('COMPANY')">
	<jstl:if test="${quolet.draftMode eq true }">
		<a href="quolet/company/edit.do?quoletId=${quolet.id}"> <spring:message
				code="edit" /></a>
		<br>
		<br>
		<a href="quolet/company/saveAsFinal.do?quoletId=${quolet.id}"> <spring:message
				code="saveAsFinal" /></a>
		<br>
		<br>
		<form:form modelAttribute="quolet"
			action="quolet/company/delete.do?quoletId=${quolet.id}" method="POST">
			<acme:submit name="delete" code="delete" />
		</form:form>
	</jstl:if>
</security:authorize>