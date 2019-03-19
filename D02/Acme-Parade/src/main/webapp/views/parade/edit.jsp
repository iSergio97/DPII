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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="parade" method="POST"
	action="parade/brotherhood/edit.do">

	<!-- Hidden fields -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="ticker" />
	
	<form:hidden path="brotherhood"/>

	<!-- Input fields -->
	<form:label path="title">
		<spring:message code="parade.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br>

	<form:label path="description">
		<spring:message code="parade.description" />
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br>
	
	<form:label path="moment">
		<spring:message code="parade.moment" />
	</form:label>
	<form:input path="moment" />
	<form:errors cssClass="error" path="moment" />
	<br>
	
	<form:label path="acmeFloats">
		<spring:message code="parade.acmeFloats" />
	</form:label>
	<form:select id="acmeFloats" path="acmeFloats">
		<form:options items="${acmeFloats}" itemLabel="title"
			itemValue="id" />
		<form:option value="" label="----" />
	</form:select>
	<form:errors cssClass="error" path="acmeFloats" />
	<br>

	<acme:submit name="save" code="save"/>
	<input type="submit" name="finalMode"
		value="<spring:message code='parade.finalMode'/>" />
	<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>


</form:form>