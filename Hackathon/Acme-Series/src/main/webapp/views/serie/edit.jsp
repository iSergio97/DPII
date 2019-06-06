<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="serie" method="POST" action="serie/publisher/edit.do">

	<!-- Hidden Fields -->

	<form:hidden path="id" />
	<form:hidden path="isDraft" />

	<!-- Input Fields -->

	<acme:register path="title" code="series.title" />
	<form:errors path="title" />
	<acme:register path="description" code="series.description" />
	<form:errors path="description" />
	<acme:register path="banner" code="series.banner" />
	<form:errors path="banner" />
	<acme:registerDate path="startDate" code="series.startDate" />
	<form:errors path="startDate" />
	<acme:registerDate path="endDate" code="series.endDate" />
	<form:errors path="endDate" />
	<div id="status" class="status">
	<spring:message code="series.status" />
		<form:select path="status">
		<form:option label = "ON EMISSION" value="ON EMISSION"></form:option>
		<form:option label = "FINALIZED" value="FINALIZED"></form:option>
		</form:select>
		<form:errors path="status" />
	</div>
	<acme:register path="director" code="series.director" />
	<form:errors path="director" />
	<acme:register path="cast" code="series.cast" />
	<form:errors path="cast" />
	
	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<jstl:if test="${serie.id ne 0 and serie.isDraft}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
