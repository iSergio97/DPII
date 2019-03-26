<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<strong>
	<spring:message code="title" />:
</strong>
	<jstl:out value="${acmeFloat.title}" />
<br>

<strong>
	<spring:message code="description" />:
</strong>
	<jstl:out value="${acmeFloat.description}" />
<br>

<jstl:if test="${fn:length(acmeFloats.pictures) ne 0}">
	<h2>
		<spring:message code="pictures" />
	</h2>
		<jstl:set var="pictureIndex" value="${0}" />
		<jstl:forEach items="${acmeFloat.pictures}" var="picture">
			<br>
			<img alt="picture" src='<jstl:out value="${picture}"></jstl:out>'>
		</jstl:forEach>
</jstl:if>

<jstl:if test="${fn:length(acmeFloats.pictures) eq 0}">
	<strong>
		<spring:message code="pictures" />:
	</strong>
		<spring:message code="noPictures" />
</jstl:if>
<br>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>

