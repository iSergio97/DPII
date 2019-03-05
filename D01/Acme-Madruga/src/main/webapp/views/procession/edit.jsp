<%--
 * edit.jsp
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

<form:form modelAttribute="procession" method="POST">

	<!-- Hidden fields -->
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="moment" />
	<form:hidden path="ticker" />
	
	<form:hidden path="brotherhood"/>

	<!-- Input fields -->
	<form:label path="title">
		<spring:message code="procession.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br>

	<form:label path="description">
		<spring:message code="procession.description" />
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br>
	
	<form:label path="acmeFloats">
		<spring:message code="procession.acmeFloats" />
	</form:label>
	<form:select multiple="true" id="acmeFloats" path="acmeFloats">
		<form:options items="${acmeFloats}" itemLabel="title"
			itemValue="id" />
		<form:option value="" label="----" />
	</form:select>
	<form:errors cssClass="error" path="acmeFloats" />
	<br>

	<input type="submit" name="edit"
		value="<spring:message code='security.send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='security.cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />


</form:form>