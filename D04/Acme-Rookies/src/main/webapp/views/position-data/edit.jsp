<%--
 * position-data/edit.jsp
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


<form:form modelAttribute="positionData" method="POST" action="position-data/hacker/edit.do" id="form">

	<form:hidden path="id" />
	<form:hidden path="curriculumId" />

	<!-- Input fields -->

	<acme:register path="title" code="positionData.title" />
	<acme:register path="description" code="positionData.description"/>
	<acme:register path="startDate" code="positionData.startDate"/>
	<acme:register path="endDate" code="positionData.endDate"/>

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<jstl:if test="${positionData.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>