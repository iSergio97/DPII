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

<form method="POST">

	<!-- Hidden fields -->

	<form:hidden path="position" />

	<!-- Input fields -->

	<p>
		<spring:message code="curriculum" />
		<jstl:set var = "checkedValue" value = "true"/>
		<jstl:forEach items="${curriculaMap}" var="entry">
			<input type="checkbox" name="curriculumId" value="${entry.key}" checked="${checkedValue}" /><jstl:out value="${entry.value}" /><br>
			<jstl:set var = "checkedValue" value = "false"/>
		</jstl:forEach>
	</p>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form>
