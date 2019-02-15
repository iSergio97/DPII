<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<form:form modelAttribute="actor" method="POST">

		<!-- Campos ocultos -->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isBanned" />
		<form:hidden path="userAccount.authorities" />
		<form:hidden path="userAccount.id" />
		<form:hidden path="userAccount.version" />
		<form:hidden path="messageBoxes" />
		<form:hidden path="socialProfiles" />
		<form:hidden path="endorsedBy" />
		<form:hidden path="endorses" />
		<form:hidden path="messagesSent" />
		<form:hidden path="messagesReceived" />
		<form:hidden path="notes" />
		

		<!-- Campos de entrada -->

		<div>
			<form:label path="userAccount.username">
				<spring:message code="security.username" />
			</form:label>
			<form:input path="userAccount.username" />
			<form:errors path="userAccount.username" />

		</div>
		<br>

		<div>
			<form:label path="userAccount.password">
				<spring:message code="security.password" />
			</form:label>
			<form:password path="userAccount.password" />
			<form:errors path="userAccount.password" />
		</div>
		<br>

		<div>
			<form:label path="Name">
				<spring:message code="security.name" />
			</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
		</div>
		<br>

		<div>
			<form:label path="MiddleName">
				<spring:message code="security.middleName" /> (*)
			</form:label>
			<form:input path="middleName" />
			<form:errors cssClass="error" path="middleName" />
		</div>
		<br>

		<div>
			<form:label path="Surname">
				<spring:message code="security.surname" />
			</form:label>
			<form:input path="surname" />
			<form:errors cssClass="error" path="surname" />
		</div>
		<br>

		<div>
			<form:label path="Email">
				<spring:message code="security.email" />
			</form:label>
			<form:input path="email" />
			<form:errors cssClass="error" path="email" />
		</div>
		<br>

		<div>
			<form:label path="phoneNumber">
				<spring:message code="security.phoneNumber" />
			</form:label>
			<form:input path="phoneNumber" />
			<form:errors cssClass="error" path="phoneNumber" />
		</div>
		<br>

		<div>
			<form:label path="address">
				<spring:message code="security.address" />
			</form:label>
			<form:input path="address" />
			<form:errors cssClass="error" path="address" />
		</div>
		<br>

		<div>
			<form:label path="photo">
				<spring:message code="security.photo" /> (*)
			</form:label>
			<form:input path="photo" />
			<form:errors cssClass="error" path="photo" />
		</div>
		<br>

		<input type="submit" name="save"
			value="<spring:message code='security.send'/>" />
		<input type="button" name="cancel"
			value="<spring:message code='security.cancel' />"
			onclick="javascript: relativeRedir('welcome/index.do');" />


	</form:form>
</div>