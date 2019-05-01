<%--
application/submit.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="applicationForm" method="POST" action="application/rookie/submit.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="explanations" class="explanations">
		<form:label path="explanations">
			<spring:message code="explanations" />
		</form:label>
		<form:input path="explanations" />
		<form:errors path="explanations" />
	</div>

	<div id="codeLink" class="codeLink">
		<form:label path="codeLink">
			<spring:message code="codeLink" />
		</form:label>
		<form:input path="codeLink" />
		<form:errors path="codeLink" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
