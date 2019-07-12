
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="areaForm" method="POST" action="administrator/savearea.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="name" class="name">
		<form:label path="name">
			<spring:message code="area.name" />
		</form:label>
		<form:input path="name" />
		<form:errors path="name" />
	</div>

	<div id="pictures" class="pictures">
		<form:label path="pictures">
			<spring:message code="area.pictures" />
		</form:label>
		<form:input path="pictures" />
		<form:errors path="pictures" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='area.send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='area.cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
