<%--
 * miscellaneousRecord/edit.jsp
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

<form:form modelAttribute="miscellaneousRecord" method="POST" action="miscellaneousRecord/edit.do">

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
	
	
	<!-- Form options -->

	<input type="submit" name="save" value="<spring:message code="send" />" />
	
	<form action="miscellaneousRecord/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${miscellaneousRecord.id}' />" />
	<p>
	</p>
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>
	
	<input type="button" name="cancel" value="<spring:message code="cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />
	
	
</form:form>
