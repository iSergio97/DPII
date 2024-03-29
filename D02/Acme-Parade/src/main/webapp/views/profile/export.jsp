<!--
 * profile/export.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 -->

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message code="actor.name" />:
<jstl:out value="${actor.name}" />,
<br>
<spring:message code="actor.middleName" />:
<jstl:out value="${actor.middleName}" />,
<br>
<spring:message code="actor.surname" />
:
<jstl:out value="${actor.surname}" /> ,
<br>

<spring:message code="actor.photo" />:
<jstl:out value="${actor.photo}" />
,
<br>

<spring:message code="actor.email" />:
<jstl:out value="${actor.email}" />
,
<br>
<spring:message code="actor.phoneNumber" />:
<jstl:out value="${actor.phoneNumber}" />
,
<br>
<spring:message code="actor.address" />:
<jstl:out value="${actor.address}" />
,
<br>
<jstl:if test="${reqURI eq 'brotherhood'}">
<spring:message code="actor.title" />"
	<jstl:out value="${actor.title}" />
	<br>
<spring:message code="actor.stablishmentDate" />": 
	<jstl:out value="${actor.establishmentDate}" />,
	<br>
</jstl:if>

<jstl:out value="Mensajes:">
	<jstl:forEach var="mb" items="${messageBox}">
		<jstl:out value="${mb.name}" />
	</jstl:forEach>
</jstl:out>
<br>
<jstl:out value="Username" />:
<jstl:out value="${username}" />
<br>
<jstl:out value="Username" />:
<jstl:out value="${password}" />
<br>
