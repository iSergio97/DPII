<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<strong><spring:message code="member.name" />:</strong>
<jstl:out value="${member.name}" />
<strong><spring:message code="member.surname" />:</strong>
<jstl:out value="${member.surname}" />

<form:form modelAttribute="enrolment" method="POST"
	action="enrolment/brotherhood/edit.do">

	<form:hidden path="id" />
	<form:hidden path="moment" />
	<form:hidden path="version" />
	<form:hidden path="exitMoment" />
	<form:hidden path="brotherhood" />
	<form:hidden path="member" />



	<jstl:if test="${locale == 'es' }">
		<form:label path="position">
			<spring:message code="enrolment.position" />
		</form:label>
		<form:select multiple="false" id="position" path="position">
			<form:options items="${positions}" itemLabel="strings.value" itemValue="id" />
		</form:select>
	</jstl:if>

	<!-- Posible eliminación pues muestra ambos valores a la hora de enviar el formulario  -->
	<jstl:if test="${locale == 'en' }">
		<form:label path="position">
			<spring:message code="enrolment.position" />
		</form:label>
		<form:select multiple="false" id="position" path="position">
			<form:options items="${positions}" itemLabel="strings.value" itemValue="id" />
		</form:select>
	</jstl:if>

	<div id="soc" class="soc">
		<acme:submit name="save" code="save" />
		<acme:cancel url="welcome/index.do" code="cancel" />
	</div>
</form:form>