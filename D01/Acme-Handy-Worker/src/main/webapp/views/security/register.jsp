<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="security.requiredFields" />

<form:form action="POST">

	<form:label path="name">
		<spring:message code="security.name" />
	</form:label>
	<form:input path="name" />	
	<form:errors class="error" path="name" />
	<br />
	
	<form:label path="middleName">
		<spring:message code="security.middleName" />
	</form:label>
	<form:input path="middleName" />	
	<form:errors class="error" path="middleName" />
	<br />
	
	<form:label path="surname">
		<spring:message code="security.surname" />
	</form:label>
	<form:input path="surname" />	
	<form:errors class="error" path="surname" />
	<br />
	
	<form:label path="photo">
		<spring:message code="security.photo" />
	</form:label>
	<form:input path="photo" />	
	<form:errors class="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="security.email" />
	</form:label>
	<form:input path="email" />	
	<form:errors class="error" path="email" />
	<br />

	<form:label path="phoneNumber">
		<spring:message code="security.phoneNumber" />
	</form:label>
	<form:input path="phoneNumber" />	
	<form:errors class="error" path="phoneNumber" />
	<br />
	
	<form:label path="address">
		<spring:message code="security.address" />
	</form:label>
	<form:input path="address" />	
	<form:errors class="error" path="address" />
	<br />	
	
	<input type="submit" value="<spring:message code="security.register" />" />

</form:form>