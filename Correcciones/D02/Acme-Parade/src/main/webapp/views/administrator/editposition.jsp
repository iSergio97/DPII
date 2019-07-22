
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="positionForm" method="POST" action="administrator/saveposition.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="strings" class="strings">
		<form:label path="strings">
			<spring:message code="position.value" />
		</form:label>
		<form:input path="strings" />
		<form:errors path="strings" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='position.send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='position.cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
