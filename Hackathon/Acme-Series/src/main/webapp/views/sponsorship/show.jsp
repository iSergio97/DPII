<%--
 * item/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
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

<strong><spring:message code="banner" /></strong>
:
<jstl:out value="${sponsorship.banner}" />
<br>

<strong><spring:message code="targetPage" /></strong>
:
<jstl:out value="${sponsorship.targetPage}" />
<br>

<strong><spring:message code="position" /></strong>
:
<jstl:out value="${sponsorship.position.title}" />
<br>

<strong><spring:message code="provider" /></strong>
:
<jstl:out value="${sponsorship.provider.make}" />
<br>

<strong><a href="sponsorship/provider/edit.do?sponsorshipId=${sponsorship.id}"><spring:message code="edit" /></a></strong>