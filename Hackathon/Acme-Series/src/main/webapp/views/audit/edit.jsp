<%--
application/create.jsp

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

<form:form modelAttribute="auditForm" method="POST" action="audit/auditor/save.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />
	<form:hidden path="positionId" />

	<!-- Input fields -->

	<div id="text" class="text">
		<form:label path="text">
			<spring:message code="text" />
		</form:label>
		<form:input path="text" />
		<form:errors path="text" />
	</div>

	<div id="score" class="score">
		<form:label path="score">
			<spring:message code="score" />
		</form:label>
		<form:input path="score" />
		<form:errors path="score" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='save'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<jstl:if test="${auditForm.id != 0}">
	<form action="audit/auditor/delete.do" method="POST">
		<input type="hidden" name="id" value="<jstl:out value='${auditForm.id}' />" />
		<input type="submit" name="delete" value="<spring:message code='delete' />" />
	</form>
</jstl:if>
