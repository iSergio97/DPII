<%--
 * education-data/edit.jsp
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

<form:form modelAttribute="education-record" method="POST"
	action="education-record/hacker/edit.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<form:label path="degree">
		<strong><spring:message code="educationData.degree" /></strong>
	</form:label>
		<form:input path="degree" />
		<form:errors cssClass="error" path="degree" />
	<br>

	<form:label path="institution">
		<strong><spring:message code="educationData.institution" /></strong>
	</form:label>
		<form:textarea path="institution" />
		<form:errors cssClass="error" path="institution" />
	<br>
	
	<form:label path="mark">
		<strong><spring:message code="educationData.mark" /></strong>
	</form:label>
		<form:textarea path="mark" />
		<form:errors cssClass="error" path="mark" />
	<br>
	
	<form:label path="startDate">
		<strong><spring:message code="educationData.startDate" /></strong>
	</form:label>
		<form:input path="startDate" />
		<form:errors cssClass="error" path="startDate" />
	<br>

	<form:label path="endDate">
		<strong><spring:message code="educationData.endDate" /></strong>
	</form:label>
		<form:input path="endDate" />
		<form:errors cssClass="error" path="endDate" />
	<br>

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
