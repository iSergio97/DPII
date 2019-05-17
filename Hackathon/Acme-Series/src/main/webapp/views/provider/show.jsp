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

<strong><spring:message code="make" /></strong>
:
<jstl:out value="${provider.make}" />
<br>

<strong><spring:message code="name" /></strong>
:
<jstl:out value="${provider.name}" />
<br>

<strong><spring:message code="surnames" /></strong>
:
<jstl:out value="${provider.surnames}" />
<br>

<strong><spring:message code="address" /></strong>
:
<jstl:out value="${provider.address}" />
<br>

<strong><spring:message code="email" /></strong>
:
<jstl:out value="${provider.email}" />
<br>

<strong><spring:message code="phoneNumber" /></strong>
:
<jstl:out value="${provider.phoneNumber}" />
<br>