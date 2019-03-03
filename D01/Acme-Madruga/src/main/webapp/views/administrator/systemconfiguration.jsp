
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

<form method="POST">
	<p>
		<spring:message code="systemConfiguration.defaultCountryCode" />
		<br><input type="text" name="defaultCountryCode" value="<jstl:out value="${defaultCountryCode}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.systemName" />
		<br><input type="text" name="systemName" value="<jstl:out value="${systemName}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.banner" />
		<br><input type="text" name="banner" value="<jstl:out value="${banner}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.finderDuration" />
		<br><input type="text" name="finderDuration" value="<jstl:out value="${finderDuration}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.maximumFinderResults" />
		<br><input type="text" name="maximumFinderResults" value="<jstl:out value="${maximumFinderResults}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.positiveWords" />
		<br><input type="text" name="positiveWords" value="<jstl:out value="${positiveWords}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.negativeWords" />
		<br><input type="text" name="negativeWords" value="<jstl:out value="${negativeWords}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.spamWords" />
		<br><input type="text" name="spamWords" value="<jstl:out value="${spamWords}" />">
	</p>
	<p>
		<spring:message code="systemConfiguration.welcomeMessages" />
		<br><input type="text" name="welcomeMessages" value="<jstl:out value="${welcomeMessages}" />">
	</p>
	<input type="submit" name="save" value="<spring:message code="systemConfiguration.save" />" />
</form>
