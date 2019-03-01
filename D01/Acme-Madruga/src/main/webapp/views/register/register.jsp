
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="register" />
</p>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${requestURI == 'member'}">

<div>
	<form:form modelAttribute="member" method="POST">

		<!-- Campos ocultos -->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isBanned" />
		<form:hidden path="userAccount"/>
		<form:hidden path="userAccount.authorities" value="MEMBER"/>
		<form:hidden path="userAccount.id"/>
		<form:hidden path="userAccount.version"/>
		<form:hidden path="messageBoxes" />
		<form:hidden path="socialProfiles" />
		<form:hidden path="endorsedBy" />
		<form:hidden path="endorses" />
		<form:hidden path="messagesSent" />
		<form:hidden path="messagesReceived" />

		<!-- Campos de entrada -->
		<spring:message code="security.un"/>
		<acme:register code="userAccount.username" path="userAccount.username"/>
		<br>
		<spring:message code="security.pass"/>
		<acme:password code="userAccount.password" path="userAccount.password"/>
		<br>
		<spring:message code="security.name"/>
		<acme:register code="name" path="name"/>
		<br>
		<spring:message code="security.mn"/>
		<acme:register code="middleName" path="middleName"/>
		<br>
		<spring:message code="security.sn"/>
		<acme:register code="surname" path="surname"/>
		<br>
		<spring:message code="security.photo"/>
		<acme:register code="photo" path="photo"/>
		<br>
		<spring:message code="security.email"/>
		<acme:register code="email" path="email"/>
		<br>
		<spring:message code="security.pn"/>
		<acme:register code="phoneNumber" path="phoneNumber"/>
		<br>
		<spring:message code="security.address"/>
		<acme:register code="address" path="address"/>
		<br>
		
		<input type="submit" name="save"
			value="<spring:message code='register.send'/>" />
		<br>
		<input type="button" name="cancel"
			value="<spring:message code='register.cancel' />"
			onclick="javascript: relativeRedir('security/login.do');" />

	</form:form>
</div>
</jstl:if>

<jstl:if test="${requestURI == 'brotherhood'}">

<div>
	<form:form modelAttribute="member" method="POST">

		<!-- Campos ocultos -->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isBanned" />
		<form:hidden path="userAccount"/>
		<form:hidden path="userAccount.authorities" value="BROTHERHOOD"/>
		<form:hidden path="userAccount.id"/>
		<form:hidden path="userAccount.version"/>
		<form:hidden path="messageBoxes" />
		<form:hidden path="socialProfiles" />
		<form:hidden path="endorsedBy" />
		<form:hidden path="endorses" />
		<form:hidden path="messagesSent" />
		<form:hidden path="messagesReceived" />

		<!-- Campos de entrada -->
		<spring:message code="security.un"/>
		<acme:register code="userAccount.username" path="userAccount.username"/>
		<br>
		<spring:message code="security.pass"/>
		<acme:password code="userAccount.password" path="userAccount.password"/>
		<br>
		<spring:message code="security.name"/>
		<acme:register code="name" path="name"/>
		<br>
		<spring:message code="security.mn"/>
		<acme:register code="middleName" path="middleName"/>
		<br>
		<spring:message code="security.sn"/>
		<acme:register code="surname" path="surname"/>
		<br>
		<spring:message code="security.photo"/>
		<acme:register code="photo" path="photo"/>
		<br>
		<spring:message code="security.email"/>
		<acme:register code="email" path="email"/>
		<br>
		<spring:message code="security.pn"/>
		<acme:register code="phoneNumber" path="phoneNumber"/>
		<br>
		<spring:message code="security.address"/>
		<acme:register code="address" path="address"/>
		<br>
		<spring:message code="security.tittle"/>
		<acme:register code="title" path="title"/>
		<br>
		
		<input type="submit" name="save"
			value="<spring:message code='register.send'/>" />
		<br>
		<input type="button" name="cancel"
			value="<spring:message code='register.cancel' />"
			onclick="javascript: relativeRedir('security/login.do');" />

	</form:form>
</div>
</jstl:if>


