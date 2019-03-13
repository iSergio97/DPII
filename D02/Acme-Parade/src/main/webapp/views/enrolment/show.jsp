<%--
 * list.jsp
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

<p><strong> <spring:message code="member.name"/>:</strong> <jstl:out value="${member.name}"/></p>
<p><strong> <spring:message code="member.surname"/></strong>
	<jstl:out value="${member.surname}"/> </p>
<p><strong> <spring:message code="member.position"/>:</strong>
<jstl:if test="${locale == 'es'}">
	<jstl:out value="${es}"/>
</jstl:if>

<jstl:if test="${locale == 'en'}">
	<jstl:out value="${en}"/>
</jstl:if>
</p>

<a href=""> <spring:message code="enrolment.edit"/> </a>