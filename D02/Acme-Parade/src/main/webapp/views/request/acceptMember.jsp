<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="request.parade" />
	<jstl:out value=": ${request.parade.title}" />
</p>

<p>
<spring:message code="request.status" />
	<jstl:choose>
			<jstl:when test = "${request.status == 'PENDING'}">
				<spring:message code="request.status.pending" />
			</jstl:when>
			<jstl:when test = "${request.status == 'APPROVED'}">
				<spring:message code="request.status.approved" />
			</jstl:when>
			<jstl:when test = "${request.status == 'REJECTED'}">
				<spring:message code="request.status.rejected" />
			</jstl:when>
			<jstl:otherwise>
			</jstl:otherwise>
		</jstl:choose>
</p>


<!-- Form options -->

<form action="request/brotherhood/accept.do" method="POST">
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
</form>

<acme:cancel url="welcome/index.do" code="cancel" />