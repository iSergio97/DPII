<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<strong><spring:message code="minCompany" /></strong>
:
<jstl:out value="${minC}" />
<br>
<strong><spring:message code="maxCompany" /></strong>
:
<jstl:out value="${maxC}" />
<br>
<strong><spring:message code="avgCompany" /></strong>
:
<jstl:out value="${avgC}" />
<br>
<strong><spring:message code="stdDevCompany" /></strong>
:
<jstl:out value="${stdDevC}" />
<br>
<br>
<br>
<strong><spring:message code="minRookie" /></strong>
:
<jstl:out value="${minA}" />
<br>
<strong><spring:message code="maxRookie" /></strong>
:
<jstl:out value="${maxA}" />
<br>
<strong><spring:message code="avgRookie" /></strong>
:
<jstl:out value="${avgA}" />
<br>
<strong><spring:message code="stdDevRookie" /></strong>
:
<jstl:out value="${stdDevA}" />
<br>
<br>

