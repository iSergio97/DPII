<%--
 * info.jsp
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


<spring:message code="enrolment.moment"/>: <jstl:out value="${enrolment.moment}"/>
<!-- Checkear con un if el null -->
<jstl:if test="${empty enrolment.exitMoment}">
<spring:message code="enrolment.exitMoment.null"/>: <jstl:out value=/>
</jstl:if>
<jstl:if test="${not empty enrolment.exitMoment}">
<spring:message code="enrolment.exitMoment.notNull"/>: <jstl:out value=/>
</jstl:if>
<spring:message code="enrolment.brotherhood"/>: <jstl:out value="${enrolment.brotherhood}"/>
<spring:message code="enrolment.position"/>: <jstl:out value="${enrolment.position}"/>