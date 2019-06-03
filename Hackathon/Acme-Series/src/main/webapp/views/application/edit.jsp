<%--
application/create.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="application/publisher/edit.do" modelAttribute="application">

	<!-- Hidden fields -->
	
	<form:hidden path="id" />
	<input type="hidden" id="serieId" name="serieId" value="${serieId}">
	
	<!-- Input fields -->
	
	<acme:textarea path="description" code="application.description" />
	
	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
