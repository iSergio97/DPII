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
<p>
	<spring:message code="curriculum" />
	<br>
	<spring:message code="curriculum.fullName" /><jstl:out value="${curriculum.personalData.fullName}" /><br>
	<spring:message code="curriculum.statement" /><jstl:out value="${curriculum.personalData.statement}" /><br>
	<spring:message code="curriculum.phoneNumber" /><jstl:out value="${curriculum.personalData.phoneNumber}" /><br>
	<spring:message code="curriculum.gitHubProfile" /><jstl:out value="${curriculum.personalData.gitHubProfile}" /><br>
	<spring:message code="curriculum.linkedInProfile" /><jstl:out value="${curriculum.personalData.linkedInProfile}" /><br>
	<jstl:forEach items="${application.curriculum.positionData}" var="positionDatum">
		<spring:message code="curriculum.title" /><jstl:out value="${positionDatum.title}" /><br>
		<spring:message code="curriculum.description" /><jstl:out value="${positionDatum.description}" /><br>
		<spring:message code="curriculum.startDate" /><jstl:out value="${positionDatum.startDate}" /><br>
		<spring:message code="curriculum.endDate" /><jstl:out value="${positionDatum.endDate}" /><br>
	</jstl:forEach>
	<jstl:forEach items="${application.curriculum.educationData}" var="educationDatum">
		<spring:message code="curriculum.degree" /><jstl:out value="${educationDatum.degree}" /><br>
		<spring:message code="curriculum.institution" /><jstl:out value="${educationDatum.institution}" /><br>
		<spring:message code="curriculum.mark" /><jstl:out value="${educationDatum.mark}" /><br>
		<spring:message code="curriculum.startDate" /><jstl:out value="${educationDatum.startDate}" /><br>
		<spring:message code="curriculum.endDate" /><jstl:out value="${educationDatum.endDate}" /><br>
	</jstl:forEach>
	<jstl:forEach items="${application.curriculum.miscellaneousData}" var="miscellaneousDatum">
		<spring:message code="curriculum.freeText" /><jstl:out value="${miscellaneousDatum.freeText}" /><br>
		<spring:message code="curriculum.attachments" /><jstl:out value="${miscellaneousDatum.attachments}" /><br>
	</jstl:forEach>
</p>
