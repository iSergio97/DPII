
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="register" />
</p>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jstl:if test="${requestURI == 'member'}">

	<div>
		<form:form modelAttribute="member" method="POST"
			action="register/member/edit.do">

			<!-- Campos ocultos -->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="isBanned" />
			<form:hidden path="userAccount" />
			<form:hidden path="userAccount.authorities" value="MEMBER" />
			<form:hidden path="userAccount.id" />
			<form:hidden path="userAccount.version" />
			<form:hidden path="messageBoxes" />
			<form:hidden path="socialProfiles" />
			<form:hidden path="messagesSent" />
			<form:hidden path="messagesReceived" />

			<!-- Campos de entrada -->
				<acme:register code="userAccount.username"
				path="userAccount.username" />
				<form:errors cssClass="error" path="userAccount.username"/>
			<br>
			<acme:password code="userAccount.password"
				path="userAccount.password" />
				<form:errors cssClass="error" path="userAccount.password"/>
			<br>
			<acme:register code="name" path="name" />
			<form:errors cssClass="error" path="name"/>
			<br>
			<acme:register code="middleName" path="middleName" />
			<form:errors cssClass="error" path="middleName"/>
			<br>
			<acme:register code="surname" path="surname" />
			<form:errors cssClass="error" path="surname"/>
			<br>
			<acme:register code="photo" path="photo" />
			<form:errors cssClass="error" path="photo"/>
			<br>
			<acme:register code="email" path="email" />
			<form:errors cssClass="error" path="email"/>
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<form:errors cssClass="error" path="phoneNumber"/>
			<br>
			<acme:register code="address" path="address" />
			<form:errors cssClass="error" path="address"/>
			<br>

			<!--  <input type="submit" name="save"
			value="<spring:message code='register.send'/>" />-->
			<acme:submit name="save" code="save" />
			<br>
			<acme:cancel url="security/login.do" code="cancel" />
			<!--<input type="button" name="cancel"
			value="<spring:message code='register.cancel' />"
			onclick="javascript: relativeRedir('security/login.do');" />
			-->

		</form:form>
	</div>
</jstl:if>

<jstl:if test="${requestURI == 'brotherhood'}">

	<div>
		<form:form modelAttribute="brotherhood" method="POST"
			action="/brotherhood/edit.do">

			<!-- Campos ocultos -->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="isBanned" />
			<form:hidden path="userAccount" />
			<form:hidden path="userAccount.authorities" value="BROTHERHOOD" />
			<form:hidden path="userAccount.id" />
			<form:hidden path="userAccount.version" />
			<form:hidden path="messageBoxes" />
			<form:hidden path="socialProfiles" />
			<form:hidden path="messagesSent" />
			<form:hidden path="messagesReceived" />

			<!-- Campos de entrada -->
			<acme:register code="userAccount.username"
				path="userAccount.username" />
				<form:errors cssClass="error" path="userAccount.username"/>
			<br>
			<acme:password code="userAccount.password"
				path="userAccount.password" />
				<form:errors cssClass="error" path="userAccount.password"/>
			<br>
			<acme:register code="name" path="name" />
			<form:errors cssClass="error" path="name"/>
			<br>
			<acme:register code="middleName" path="middleName" />
			<form:errors cssClass="error" path="middleName"/>
			<br>
			<acme:register code="surname" path="surname" />
			<form:errors cssClass="error" path="surname"/>
			<br>
			<acme:register code="photo" path="photo" />
			<form:errors cssClass="error" path="photo"/>
			<br>
			<acme:register code="email" path="email" />
			<form:errors cssClass="error" path="email"/>
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<form:errors cssClass="error" path="phoneNumber"/>
			<br>
			<acme:register code="address" path="address" />
			<form:errors cssClass="error" path="address"/>
			<br>
			<acme:register code="title" path="title" />
			<form:errors cssClass="error" path="title"/>
			<br>

			<!--   <input type="save" name="save"
			value="<spring:message code='register.send'/>" />-->
			<acme:submit name="save" code="save"/>
			<br>
			<acme:cancel url="security/login.do" code="cancel" />
			<!--<input type="button" name="cancel"
			value="<spring:message code='register.cancel' />"
			onclick="javascript: relativeRedir('security/login.do');" />
			-->
		</form:form>
	</div>
</jstl:if>


