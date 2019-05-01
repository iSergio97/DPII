<%--
 * personal-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
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


<form:form modelAttribute="personalData"
	action="personal-data/rookie/edit.do">

	<form:hidden path="id" />

	<h2>
		<spring:message code="curriculum.fields" />
	</h2>
		<acme:register code="curriculum.name" path="curriculumName" />

	<h2>
		<spring:message code="personalData.fields" />
	</h2>
		<acme:register path="fullName" code="personalData.fullName" />
		<acme:register path="gitHubProfile" code="personalData.gitHubProfile" />
		<acme:register path="linkedInProfile" code="personalData.linkedInProfile" />
		<acme:register path="phoneNumber" code="personalData.phoneNumber" />
		<acme:register path="statement" code="personalData.statement" />

	<acme:submit name="save" code="action.save" />
	<jstl:if test="${personalData.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel" />

</form:form>