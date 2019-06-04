<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<strong><spring:message code="name" /></strong>: <jstl:out value="${critic.name}" />
	<br>
	<strong><spring:message code="surnames" /></strong>: <jstl:out value="${critic.surnames}" />
	<br>
	<strong><spring:message code="email" /></strong>: <jstl:out value="${critic.email}" />
	<br>
	<strong><spring:message code="photo" /></strong>: <a href="${critic.photo }" target="_blank"> <spring:message code="photo"/></a>
	<br>
	<strong><spring:message code="phoneNumber" /></strong>: <jstl:out value="${critic.phoneNumber}" />
	<br>
	<strong><spring:message code="address" /></strong>: <jstl:out value="${critic.address}" />
	<br>
	<a href="profile/actor/export.do" ><spring:message code="profile.export" /></a>