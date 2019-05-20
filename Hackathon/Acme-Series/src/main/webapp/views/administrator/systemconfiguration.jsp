<%--
administrator/systemconfiguration.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${error}">
	<p>
		<spring:message code="error" />
	</p>
</jstl:if>

<jstl:if test="${success}">
	<p>
		<spring:message code="success" />
	</p>
</jstl:if>

<form:form modelAttribute="systemConfigurationForm" method="POST" action="administrator/systemconfiguration.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="systemName" class="systemName">
		<form:label path="systemName">
			<spring:message code="systemName" />
		</form:label>
		<form:input path="systemName" />
		<form:errors path="systemName" />
	</div>

	<div id="banner" class="banner">
		<form:label path="banner">
			<spring:message code="banner" />
		</form:label>
		<form:input path="banner" />
		<form:errors path="banner" />
	</div>

	<div id="defaultCountryCode" class="defaultCountryCode">
		<form:label path="defaultCountryCode">
			<spring:message code="defaultCountryCode" />
		</form:label>
		<form:input path="defaultCountryCode" />
		<form:errors path="defaultCountryCode" />
	</div>

	<div id="finderCacheTime" class="finderCacheTime">
		<form:label path="finderCacheTime">
			<spring:message code="finderCacheTime" />
		</form:label>
		<form:input path="finderCacheTime" />
		<form:errors path="finderCacheTime" />
	</div>

	<div id="maximumFinderResults" class="maximumFinderResults">
		<form:label path="maximumFinderResults">
			<spring:message code="maximumFinderResults" />
		</form:label>
		<form:input path="maximumFinderResults" />
		<form:errors path="maximumFinderResults" />
	</div>

	<div id="spamWords" class="spamWords">
		<form:label path="spamWords">
			<spring:message code="spamWords" />
		</form:label>
		<form:input path="spamWords" />
		<form:errors path="spamWords" />
	</div>

	<div id="welcomeMessage" class="welcomeMessage">
		<form:label path="welcomeMessage">
			<spring:message code="welcomeMessage" />
		</form:label>
		<form:input path="welcomeMessage" />
		<form:errors path="welcomeMessage" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='save'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
