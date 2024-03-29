<%--
 * administrator/systemconfiguration.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
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
		<spring:message code="systemConfiguration.error" />
	</p>
</jstl:if>

<jstl:if test="${success}">
	<p>
		<spring:message code="systemConfiguration.success" />
	</p>
</jstl:if>

<form:form modelAttribute="systemConfigurationForm" method="POST" action="administrator/systemconfiguration.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />

	<!-- Input fields -->

	<div id="defaultCountryCode" class="defaultCountryCode">
		<form:label path="defaultCountryCode">
			<spring:message code="systemConfiguration.defaultCountryCode" />
		</form:label>
		<form:input path="defaultCountryCode" />
		<form:errors path="defaultCountryCode" />
	</div>

	<div id="systemName" class="systemName">
		<form:label path="systemName">
			<spring:message code="systemConfiguration.systemName" />
		</form:label>
		<form:input path="systemName" />
		<form:errors path="systemName" />
	</div>

	<div id="banner" class="banner">
		<form:label path="banner">
			<spring:message code="systemConfiguration.banner" />
		</form:label>
		<form:input path="banner" />
		<form:errors path="banner" />
	</div>

	<div id="finderDuration" class="finderDuration">
		<form:label path="finderDuration">
			<spring:message code="systemConfiguration.finderDuration" />
		</form:label>
		<form:input path="finderDuration" />
		<form:errors path="finderDuration" />
	</div>

	<div id="maximumFinderResults" class="maximumFinderResults">
		<form:label path="maximumFinderResults">
			<spring:message code="systemConfiguration.maximumFinderResults" />
		</form:label>
		<form:input path="maximumFinderResults" />
		<form:errors path="maximumFinderResults" />
	</div>

	<div id="positiveWords" class="positiveWords">
		<form:label path="positiveWords">
			<spring:message code="systemConfiguration.positiveWords" />
		</form:label>
		<form:input path="positiveWords" />
		<form:errors path="positiveWords" />
	</div>

	<div id="negativeWords" class="negativeWords">
		<form:label path="negativeWords">
			<spring:message code="systemConfiguration.negativeWords" />
		</form:label>
		<form:input path="negativeWords" />
		<form:errors path="negativeWords" />
	</div>

	<div id="spamWords" class="spamWords">
		<form:label path="spamWords">
			<spring:message code="systemConfiguration.spamWords" />
		</form:label>
		<form:input path="spamWords" />
		<form:errors path="spamWords" />
	</div>

	<div id="welcomeMessages" class="welcomeMessages">
		<form:label path="welcomeMessages">
			<spring:message code="systemConfiguration.welcomeMessages" />
		</form:label>
		<form:input path="welcomeMessages" />
		<form:errors path="welcomeMessages" />
	</div>

	<div id="warningMessages" class="warningMessages">
		<form:label path="warningMessages">
			<spring:message code="systemConfiguration.warningMessages" />
		</form:label>
		<form:input path="warningMessages" />
		<form:errors path="warningMessages" />
	</div>

	<div id="lowestPosition" class="lowestPosition">
		<form:label path="lowestPosition">
			<spring:message code="systemConfiguration.lowestPosition" />
		</form:label>
		<form:radiobuttons items="${positionsMap}" id="lowestPosition" path="lowestPosition" />
	</div>

	<!-- Form options -->

	<input type="submit" name="edit" value="<spring:message code='systemConfiguration.save'/>" />
	<input type="button" name="cancel"
		value="<spring:message code='systemConfiguration.cancel' />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
