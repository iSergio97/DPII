<%--
 * float/edit.jsp
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

<form:form modelAttribute="socialProfileForm" method="POST" action="socialprofile/actor/save.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="nick" class="nick">
		<form:label path="nick">
			<spring:message code="nick" />
		</form:label>
		<form:input path="nick" />
		<form:errors path="nick" />
	</div>

	<div id="socialNetworkName" class="socialNetworkName">
		<form:label path="socialNetworkName">
			<spring:message code="socialNetworkName" />
		</form:label>
		<form:input path="socialNetworkName" />
		<form:errors path="socialNetworkName" />
	</div>

	<div id="profileLink" class="profileLink">
		<form:label path="profileLink">
			<spring:message code="profileLink" />
		</form:label>
		<form:input path="profileLink" />
		<form:errors path="profileLink" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='send'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<jstl:if test="${socialProfileForm.id != 0}">
	<form action="socialprofile/actor/delete.do" method="POST">
		<input type="hidden" name="id" value="<jstl:out value='${socialProfileForm.id}' />" />
		<input type="submit" name="delete" value="<spring:message code='delete' />" />
	</form>
</jstl:if>
