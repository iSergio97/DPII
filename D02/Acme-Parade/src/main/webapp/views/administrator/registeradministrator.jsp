<%--
 * administrator/registeradministrator.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="register" />
</p>

<div>
	<form:form modelAttribute="administrator" method="POST">

		<!-- Campos ocultos -->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isBanned" />
		<form:hidden path="userAccount"/>
		<form:hidden path="userAccount.authorities" value="ADMIN"/>
		<form:hidden path="userAccount.id"/>
		<form:hidden path="userAccount.version"/>
		<form:hidden path="messageBoxes" />
		<form:hidden path="socialProfiles" />
		<form:hidden path="endorsedBy" />
		<form:hidden path="endorses" />
		<form:hidden path="messagesSent" />
		<form:hidden path="messagesReceived" />

		<!-- Campos de entrada -->

		<div>
			<form:label path="userAccount.username">
				<spring:message code="register.username" />
			</form:label>
			<form:input path="userAccount.username" />
			<form:errors path="userAccount.username" />
		</div>
		<br>

		<div>
			<form:label path="userAccount.password">
				<spring:message code="register.password" />
			</form:label>
			<form:input path="userAccount.password" />
			<form:errors path="userAccount.password" />
		</div>
		<br>

		<div>
			<form:label path="Name">
				<spring:message code="register.name" />
			</form:label>
			<form:input path="name" />
			<form:errors path="name" />
		</div>
		<br>

		<div>
			<form:label path="MiddleName">
				<spring:message code="register.middleName" />
			</form:label>
			<form:input path="middleName" />
			<form:errors path="middleName" />
		</div>
		<br>

		<div>
			<form:label path="Surname">
				<spring:message code="register.surname" />
			</form:label>
			<form:input path="surname" />
			<form:errors path="surname" />
		</div>
		<br>

		<div>
			<form:label path="photo">
				<spring:message code="register.photo" />
			</form:label>
			<form:input path="photo" />
			<form:errors path="photo" />
		</div>
		<br>

		<div>
			<form:label path="email">
				<spring:message code="register.email" />
			</form:label>
			<form:input path="email" />
			<form:errors path="email" />
		</div>
		<br>

		<div>
			<form:label path="phoneNumber">
				<spring:message code="register.phoneNumber" />
			</form:label>
			<form:input path="phoneNumber" />
			<form:errors path="phoneNumber" />
		</div>
		<br>

		<div>
			<form:label path="address">
				<spring:message code="register.address" />
			</form:label>
			<form:input path="address" />
			<form:errors path="address" />
		</div>
		<br>

		<input type="submit" name="save"
			value="<spring:message code='register.send'/>" />
		<br>
		<input type="button" name="cancel"
			value="<spring:message code='register.cancel' />"
			onclick="javascript: relativeRedir('security/login.do');" />

	</form:form>
</div>
