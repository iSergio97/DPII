<%--
 * position-data/edit.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="position-record" method="POST"
	action="position-record/hacker/edit.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<form:label path="title">
		<strong><spring:message code="positionData.title" /></strong>
	</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
	<br>

	<form:label path="description">
		<strong><spring:message code="positionData.description" /></strong>
	</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
	<br>
	
	<form:label path="startDate">
		<strong><spring:message code="positionData.startDate" /></strong>
	</form:label>
		<form:input path="startDate" />
		<form:errors cssClass="error" path="startDate" />
	<br>

	<form:label path="endDate">
		<strong><spring:message code="positionData.endDate" /></strong>
	</form:label>
		<form:input path="endDate" />
		<form:errors cssClass="error" path="endDate" />
	<br>

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
