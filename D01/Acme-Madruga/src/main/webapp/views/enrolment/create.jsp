<%--
 * create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="enrolment" method="POST"
	action="enrolment/member/edit.do">

	<form:hidden path="id" />
	<form:hidden path="moment"/>
	<!-- Hidden fields 
-->
	<!-- Input fields -->

	<div id="bro" class="bro">
		<form:label path="bro">
			<spring:message code="enrolment.brotherhood" />
		</form:label>
		<form:select multiple="false" id="bro" path="bro">
			<form:options items="${brotherhoods}" itemLabel="title"
				itemValue="id" />
		</form:select>
	</div>


	<div id="soc" class="soc">
		<acme:submit name="save" code="save" />
		<acme:cancel url="welcome/index.do" code="cancel" />
	</div>

</form:form>