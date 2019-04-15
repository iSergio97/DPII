<%--
application/show.jsp

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

<p>
	<spring:message code="moment" />
	<br>
	<jstl:out value="${application.moment}" />
</p>
<p>
	<spring:message code="submitMoment" />
	<br>
	<jstl:out value="${application.submitMoment}" />
</p>
<p>
	<spring:message code="explanations" />
	<br>
	<jstl:out value="${application.explanations}" />
</p>
<p>
	<spring:message code="codeLink" />
	<br>
	<a href="<jstl:out value="${application.codeLink}" />"><spring:message code="codeLink" /></a>
</p>
<p>
	<spring:message code="status" />
	<br>
	<jstl:choose>
		<jstl:when test="${application.status eq 'PENDING'}">
			<spring:message code="status.pending" />
		</jstl:when>
		<jstl:when test="${application.status eq 'SUBMITTED'}">
			<spring:message code="status.submitted" />
		</jstl:when>
		<jstl:when test="${application.status eq 'REJECTED'}">
			<spring:message code="status.rejected" />
		</jstl:when>
		<jstl:when test="${application.status eq 'ACCEPTED'}">
			<spring:message code="status.accepted" />
		</jstl:when>
	</jstl:choose>
</p>
<p>
	<spring:message code="position" />
	<br>
	<jstl:out value="${application.position.title}" />
</p>
<p>
	<spring:message code="hacker" />
	<br>
	<jstl:out value="${application.hacker.name}" /> <jstl:out value="${application.hacker.surnames}" />
</p>
