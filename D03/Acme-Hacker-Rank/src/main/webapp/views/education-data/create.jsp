<%--
 * education-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="positionData" action="position-data/hacker/edit.do">

	<!-- Input fields -->

	<acme:register code="degree" path="educationData.degree"/>
	<acme:register code="institution" path="educationData.institution"/>
	<acme:register code="mark" path="educationData.mark"/>
	<acme:register code="startDate" path="educationData.startDate"/>
	<acme:register code="endDate" path="educationData.endDate"/>

	<!-- Buttons -->

	<acme:submit name="save" code="save"/>
	<acme:cancel url="welcome/index.do" code="cancel"/>

</form:form>