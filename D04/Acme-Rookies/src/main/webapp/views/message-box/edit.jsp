<%--
 * message-box/edit.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<spring:message code="options.editData"/>:
</h2>

<form:form modelAttribute="messageBox" action="message-box/all/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<strong><spring:message code="messageBox.name"/>:</strong>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name" />
	<br/><br/>

	<form:hidden path="actor"/>

	<acme:submit name="save" code="action.save"/>
	<acme:submit name="delete" code="action.delete"/>
	<acme:cancel url="message-box/all/list.do" code="action.cancel"/>
</form:form>