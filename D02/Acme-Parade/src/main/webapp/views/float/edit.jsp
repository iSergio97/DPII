<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="acmeFloatForm" method="POST" action="float/brotherhood/save.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="title" class="title">
		<form:label path="title">
			<spring:message code="title" />
		</form:label>
		<form:input path="title" />
		<form:errors path="title" />
	</div>

	<div id="description" class="description">
		<form:label path="description">
			<spring:message code="description" />
		</form:label>
		<form:input path="description" />
		<form:errors path="description" />
	</div>

	<div id="pictures" class="pictures">
		<form:label path="pictures">
			<spring:message code="pictures" />
		</form:label>
		<form:input path="pictures" />
		<form:errors path="pictures" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<form action="acmefloat/brotherhood/delete.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${acmeFloat.id}' />" />
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>