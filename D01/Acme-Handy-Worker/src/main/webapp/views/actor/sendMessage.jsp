<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:label path="subject">
		<spring:message code="actor.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />
	
	
	<form:label path="messageBody">
		<spring:message code="actor.messageBody" />:
	</form:label>
	<form:textarea path="messageBody" />
	<form:errors cssClass="error" path="messageBody" />
	<br />