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


<strong><spring:message code="name" /></strong>
:
<jstl:out value="${item.name}" />
<br>

<strong><spring:message code="description" /></strong>
:
<jstl:out value="${item.description}" />
<br>

<strong><spring:message code="link" /></strong>
:
<jstl:out value="${item.link}" />
<br>

<strong><spring:message code="pictures" /></strong>
:
<jstl:out value="${item.pictures}" />
<br>

