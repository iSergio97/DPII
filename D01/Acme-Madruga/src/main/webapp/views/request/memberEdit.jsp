<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="request" method="POST" action="request/member/edit.do">

<!-- Hidden Fields -->

	<form:hidden path="id" />
	<form:hidden path="status" />
	<form:hidden path="reason" />
	
<!-- Input Fields -->

<div id="processions" class="processions">
		<form:select id="processions" path="procession" >
		<form:options items="${processions}" itemLabel = "title" itemValue="id"/>
		<form:option label="----" value="0" />
		</form:select>
	</div>

<div id="hLine" class="hLine">
		<form:label path="hLine">
			<spring:message code="request.hLine" />
		</form:label>
		<form:input path="hLine" />
		<form:errors path="hLine" />
	</div>
	
	<div id="vLine" class="vLine">
		<form:label path="vLine">
			<spring:message code="request.vLine" />
		</form:label>
		<form:input path="vLine" />
		<form:errors path="vLine" />
	</div>
	
<!-- Form options -->

	<div id="soc" class="soc">
		<acme:submit name="save" code="save" />
		<acme:cancel url="welcome/index.do" code="cancel" />
	</div>
</form:form>