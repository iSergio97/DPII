<%--
 * create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="procession" method="POST"
	action="procession/brotherhood/edit.do">

	<!-- Hidden fields -->
	<form:hidden path="id" />

	<!-- Input fields -->
	<form:label path="title">
		<strong><spring:message code="procession.title" /></strong>
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br>

	<form:label path="description">
		<strong><spring:message code="procession.description" /></strong>
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br>
	
	<form:label path="moment">
		<strong><spring:message code="procession.moment" /></strong>
	</form:label>
	<form:input path="moment" placeholder="dd/mm/yyyy hh:mm"/>
	<form:errors cssClass="error" path="moment" />
	<br>

	<form:label path="acmeFloats">
		<strong><spring:message code="procession.acmeFloats" /></strong>
	</form:label>
	<form:select id="acmeFloats" path="acmeFloats">
		<form:options items="${acmeFloats}" itemLabel="title" itemValue="id" />
		<form:option value="0" label="----" />
	</form:select>
	<form:errors cssClass="error" path="acmeFloats" />
	<br>

	<acme:submit name="save" code="save"/>
	<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>


</form:form>