
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p>
	<spring:message code="systemconfiguration" />
</p>

<jstl:if test="${error}">
	<p>
		<spring:message code="systemconfiguration.error" />
	</p>
</jstl:if>

<jstl:if test="${success}">
	<p>
		<spring:message code="systemconfiguration.success" />
	</p>
</jstl:if>

<form method="POST">
	<p>
		<spring:message code="systemconfiguration.name" />
		<br><input type="text" name="systemconfigurationname" value="<jstl:out value="${systemconfigurationname}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.banner" />
		<br><input type="text" name="systemconfigurationbanner" value="<jstl:out value="${systemconfigurationbanner}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.message" />
		<br><input type="text" name="systemconfigurationmessage" value="<jstl:out value="${systemconfigurationmessage}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.spamwords" />
		<br><input type="text" name="systemconfigurationspamwords" value="<jstl:out value="${systemconfigurationspamwords}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.vat" />
		<br><input type="number" name="systemconfigurationvat" value="<jstl:out value="${systemconfigurationvat}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.defaultcountrycode" />
		<br><input type="text" name="systemconfigurationdefaultcountrycode" value="<jstl:out value="${systemconfigurationdefaultcountrycode}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.creditcardmakers" />
		<br><input type="text" name="systemconfigurationcreditcardmakers" value="<jstl:out value="${systemconfigurationcreditcardmakers}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.positivewords" />
		<br><input type="text" name="systemconfigurationpositivewords" value="<jstl:out value="${systemconfigurationpositivewords}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.negativewords" />
		<br><input type="text" name="systemconfigurationnegativewords" value="<jstl:out value="${systemconfigurationnegativewords}" />">
	</p>
	<p>
		<spring:message code="systemconfiguration.maximumfinderresults" />
		<br><input type="number" name="systemconfigurationmaximumfinderresults" value="<jstl:out value="${systemconfigurationmaximumfinderresults}" />">
	</p>
	<input type="submit" name="save" value="<spring:message code="systemconfiguration.save" />" />
</form>
