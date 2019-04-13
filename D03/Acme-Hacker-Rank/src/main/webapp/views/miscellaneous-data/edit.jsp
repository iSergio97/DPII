<%--
 * personal-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="miscellaneousData" method="POST" action="miscellaneous-data/hacker/edit.do" id="form">

	<!-- Input fields -->
	
	<form:hidden path="id" />
	<form:hidden path="curriculumId" />

	<acme:register path="freeText" code="miscellaneousData.freeText" />
	<acme:register path="attachments" code="miscellaneousData.attachments"/>

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<!--
	<jstl:if test="${miscellaneousData.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	-->
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>