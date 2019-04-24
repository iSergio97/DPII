<%--
 * education-data/edit.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Jos� Antonio Dom�nguez G�mez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="educationData" method="POST" action="education-data/hacker/edit.do" id="form">

	<form:hidden path="id" />
	<form:hidden path="curriculumId" />

	<!-- Input fields -->

	<acme:register path="degree" code="educationData.degree"/>
	<acme:register path="institution" code="educationData.institution"/>
	<acme:register path="mark" code="educationData.mark"/>
	<acme:register path="startDate" code="educationData.startDate"/>
	<acme:register path="endDate" code="educationData.endDate"/>

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<jstl:if test="${educationData.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>