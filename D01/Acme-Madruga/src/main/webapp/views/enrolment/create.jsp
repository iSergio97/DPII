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


<form:form modelAttribute="enrolment" method="POST">
	<!-- Hidden fields -->
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="member" />

	<!-- Input fields -->

	<div id="brotherhood" class="brotherhood">
		<form:label path="brotherhoods">
			<spring:message code="enrolment.brotherhoods" />
		</form:label>

		<form:select path="brotherhoods" multiple="false">
			<form:option value="${brotherhoods.name}" >
			</form:option>
		</form:select>

	</div>

	<div id="positions" class="positions">

		<form:label path="positions">
			<spring:message code="enrolment.positions" />
		</form:label>

		<form:select path="brotherhoods" multiple="false">
			<form:option value="${brotherhoods}" >
			</form:option>
		</form:select>
	</div>

	<div id="soc" class="soc">

		<input type="submit" name="save"
			value="<spring:message code='security.send'/>" /> <input
			type="submit" name="cancel"
			value="<spring:message code='security.cancel'/>"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</div>

</form:form>