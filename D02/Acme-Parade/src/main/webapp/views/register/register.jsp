
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

			<br>
			<br>
			<spring:message code="termsAndConditions" />
			<br>
			<acme:submit name="save" code="save" />
			<br>
			<acme:cancel url="security/login.do" code="cancel" />

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
			<br>
			<acme:register code="middleName" path="middleName" />
			<br>
			<acme:register code="surname" path="surname" />
			<br>
			<acme:registerPhoto code="photo" path="photo" />
			<br>
			<acme:register code="email" path="email" />
			<br>
			<acme:register code="phoneNumber" path="phoneNumber" />
			<br>
			<acme:register code="address" path="address" />
			<br>
			<acme:register code="title" path="title" />
			<br>
			<acme:registerDate code="establishmentDate" path="establishmentDate"/>
			<br>

			<!-- Campos de usuario -->

			<acme:register code="username" path="username" />
			<br>
			<acme:password code="password" path="password" />
			<br>
			<acme:password code="confirmPassword" path="confirmPassword" />
			
			<br>
			<br>
			<spring:message code="termsAndConditions" />
			<br>
			<acme:submit name="save" code="save" />
			<br>
			<acme:cancel url="security/login.do" code="cancel" />


		</form:form>
	</div>
</jstl:if>


