<%--
 * personal-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Dom�nguez
 * @author Jos� Antonio Dom�nguez G�mez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="personalData"
	action="curriculum/hacker/edit.do">

	<form:hidden path="id" />

	<h2>
		<spring:message code="curriculum.fields" />
	</h2>
	<strong><acme:register code="curriculum.name" path="curriculumName" /></strong>

	<h2>
		<spring:message code="personalData.fields" />
	</h2>
	<strong><acme:register path="fullName" code="personalData.fullName" />:</strong>
	<strong><acme:register path="gitHubProfile" code="personalData.gitHubProfile" />:</strong>
	<strong><acme:register path="linkedInProfile" code="personalData.linkedInProfile" />:</strong>
	<strong><acme:register path="phoneNumber" code="personalData.phoneNumber" />:</strong>
	<strong><acme:register path="statement" code="personalData.statement" />:</strong>

	<acme:submit name="save" code="action.save" />
	<acme:cancel url="welcome/index.do" code="action.cancel" />

</form:form>