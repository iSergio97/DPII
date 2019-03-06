
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${requestURI == 'member'}">

	<p>
		<spring:message code="register.member" />
	</p>

	<div>
		<form:form modelAttribute="member" method="POST"
			action="register/member/edit.do">

			<!-- Campos ocultos -->

			<form:hidden path="id" />

			<!-- Campos de entrada -->
			<acme:register code="name" path="name" />
			<form:errors cssClass="error" path="name" />
			<br>
			<acme:register code="middleName" path="middleName" />
			<form:errors cssClass="error" path="middleName" />
			<br>
			<acme:register code="surname" path="surname" />
			<form:errors cssClass="error" path="surname" />
			<br>
			<acme:register code="photo" path="photo" />
			<form:errors cssClass="error" path="photo" />
			<br>
			<acme:register code="email" path="email" />
			<form:errors cssClass="error" path="email" />
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<form:errors cssClass="error" path="phoneNumber" />
			<br>
			<acme:register code="address" path="address" />
			<form:errors cssClass="error" path="address" />
			<br>

			<!-- Campos de usuario -->
			<acme:register code="username" path="username" />
			<form:errors cssClass="error" path="username" />
			<br>
			<acme:password code="password" path="password" />
			<form:errors cssClass="error" path="password" />
			<br>
			<acme:password code="confirmPassword" path="confirmPassword" />
			<form:errors cssClass="error" path="confirmPassword" />

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

<jstl:if test="${requestURI == 'administrator'}">

	<p>
		<spring:message code="register.administrator" />
	</p>

	<div>
		<form:form modelAttribute="administrator" method="POST"
			action="register/administrator/edit.do">

			<!-- Campos ocultos -->

			<form:hidden path="id" />

			<!-- Campos de entrada -->
			<acme:register code="name" path="name" />
			<br>
			<acme:register code="middleName" path="middleName" />
			<br>
			<acme:register code="surname" path="surname" />
			<br>
			<acme:register code="photo" path="photo" />
			<br>
			<acme:register code="email" path="email" />
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<br>
			<acme:register code="address" path="address" />
			<br>

			<!-- Campos de usuario -->
			<acme:register code="username" path="username" />
			<br>
			<acme:password code="password" path="password" />
			<br>
			<acme:password code="confirmPassword" path="confirmPassword" />

			<spring:message code="termsAndConditions" />
			<input type="checkbox" value="denied">
			<label class="form-check-label" for="accepted"> <spring:message
					code="termsAndConditionsAccepted" /></label>
			<label class="form-check-label" for="denied"> <spring:message
					code="termsAndConditionsDenied" /></label>

			<acme:submit name="save" code="save" />
			<br>
			<acme:cancel url="security/login.do" code="cancel" />

		</form:form>
	</div>
</jstl:if>

<jstl:if test="${requestURI == 'brotherhood'}">

	<p>
		<spring:message code="register.brotherhood" />
	</p>

	<div>
		<form:form modelAttribute="brotherhood" method="POST"
			action="register/brotherhood/edit.do">

			<!-- Campos ocultos -->

			<form:hidden path="id" />

			<!-- Campos de entrada -->
			<acme:register code="name" path="name" />
			<form:errors cssClass="error" path="name" />
			<br>
			<acme:register code="middleName" path="middleName" />
			<form:errors cssClass="error" path="middleName" />
			<br>
			<acme:register code="surname" path="surname" />
			<form:errors cssClass="error" path="surname" />
			<br>
			<acme:register code="photo" path="photo" />
			<form:errors cssClass="error" path="photo" />
			<br>
			<acme:register code="email" path="email" />
			<form:errors cssClass="error" path="email" />
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<form:errors cssClass="error" path="phoneNumber" />
			<br>
			<acme:register code="address" path="address" />
			<form:errors cssClass="error" path="address" />
			<br>
			<acme:register code="title" path="title" />
			<form:errors cssClass="error" path="title" />
			<br>

			<!-- Campos de usuario -->

			<acme:register code="username" path="username" />
			<br>
			<acme:password code="password" path="password" />
			<br>
			<acme:password code="confirmPassword" path="confirmPassword" />


			<acme:submit name="save" code="save" />
			<br>
			<acme:cancel url="security/login.do" code="cancel" />

		</form:form>
	</div>
</jstl:if>


