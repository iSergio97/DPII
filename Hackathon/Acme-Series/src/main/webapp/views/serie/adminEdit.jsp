<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="serie" method="POST" action="serie/administrator/edit.do">

	<!-- Hidden Fields -->

	<form:hidden path="id" />
	<form:hidden path="isDraft" />

	<!-- Input Fields -->

	<acme:register path="title" code="series.title" />
	<acme:register path="description" code="series.description" />
	<acme:register path="banner" code="series.banner" />
	<acme:registerDate path="startDate" code="series.startDate" />
	<acme:registerDate path="endDate" code="series.endDate" />
	<div id="status" class="status">
	<spring:message code="series.status" />
		<form:select path="status">
		<form:option label = "ON EMISSION" value="ON EMISSION"></form:option>
		<form:option label = "FINALIZED" value="FINALIZED"></form:option>
		</form:select>
		<form:errors path="status" />
	</div>
	<acme:register path="director" code="series.director" />
	<acme:register path="cast" code="series.cast" />
	

	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<jstl:if test="${serie.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
