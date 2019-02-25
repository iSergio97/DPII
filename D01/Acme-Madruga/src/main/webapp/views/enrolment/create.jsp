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


<form:form commandName="enrolment">

	<!-- Hidden fields -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="member" />
	<form:hidden path="exitMoment" />
	<form:hidden path="position" />

	<!-- Input fields -->
	<!--
	<div id="brotherhood" class="brotherhood">
		<form:label path="brotherhood">
			<spring:message code="enrolment.brotherhood" />
		</form:label>
		<form:select multiple="false" id="brotherhood" path="brotherhoods">
			<form:options items="${brotherhoods}" itemLabel="title" itemValue="title" />
		</form:select>
	</div>
-->

	<form:label path="brotherhood">
		<spring:message code="enrolment.brotherhood" />
	</form:label>
	<form:select path="brotherhood" multiple="false">
		<form:options items="${brotherhoods}" itemValue="id" itemLabel="name" />
	</form:select>


	<div id="soc" class="soc">
		<input type="submit" name="save"
			value="<spring:message code='security.send'/>" /> <input
			type="submit" name="cancel"
			value="<spring:message code='security.cancel'/>"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</div>

</form:form>