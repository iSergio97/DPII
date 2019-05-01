<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="message" action="message/actor/edit.do">

	<acme:register code="subject" path="subject"/>
	<br>
	<spring:message code='priority' />
	<form:select path="priority">
			<form:option value="HIGH" > <spring:message code='high' /></form:option>
			<form:option value="NEUTRAL" >  <spring:message code='neutral' /></form:option>
			<form:option value="LOW" > <spring:message code='low' /> </form:option>
	</form:select>
	
	<acme:register code="tags" path="tags"/>
	<br>
	<acme:register code="body" path="body"/>
	<br>
	<form:select path="recipients" multiple="true">
		<form:options items="${ recipients}" itemLabel="Name" itemValue="id"/>
	</form:select>
	
	<acme:submit name="save" code="save" />
	<acme:cancel url="welcome/index.do" code="cancel" />
</form:form>