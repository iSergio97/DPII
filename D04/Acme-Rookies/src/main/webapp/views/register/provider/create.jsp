<%--
 * register/rookie/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>
<script type="text/javascript">
	function test() {
		if (!document.getElementById("phoneNumber").value.includes("(+34)")) { return confirm("Your phone number dont have prefix. Do you want to add one? Prefix = (+XX) \n Tu teléfono no tiene prefijo de zona. ¿Quiere añadir uno?" Prefijo = (+XX) ); }
	}

	function testMe() {
		return window.alert("Corchuelo en realidad no es malo, es complicado de complacer");
	}
</script>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="provider" action="register/provider/edit.do">

	<form:hidden path="id"/>
	
	<%-- Campos de usuario --%>
	<fieldset>
		<legend>
			<spring:message code="actorProperties" />
		</legend>
	<acme:register code="name" path="address"/>
	<acme:register code="surnames" path="surnames"/>
	<acme:register code="make" path="make"/>
	<acme:register code="vat" path="vat"/>
	<acme:register code="email" path="email"/>
	<acme:register code="photo" path="photo"/>
	<acme:register code="phoneNumber" path="phoneNumber"/>
	<acme:register code="address" path="name"/>
	</fieldset>
	
	<br>

	<fieldset>
		<legend>
			<spring:message code="userAccountProperties" />
		</legend>
		<acme:register code="username" path="username" />
		<acme:password code="password" path="password" />
		<acme:password code="confirmPassword" path="confirmPassword" />
	</fieldset>

	<br>
	
	<%-- Campos de tarjeta de crédito --%>

	<fieldset>
		<legend>
			<spring:message code="creditCardProperties" />
		</legend>
	<acme:register code="holder" path="holder"/>
	<acme:register code="brand" path="brand"/>
	<acme:register code="number" path="number"/>
	<acme:register code="expirationMonth" path="expirationMonth"/>
	<acme:register code="expirationYear" path="expirationYear"/>
	<acme:register code="cvv" path="CVV"/>
	
	</fieldset>

	<br>
	
	<button type="submit" onClick="return test();">
		<spring:message code="save" />
	</button>
	<acme:cancel url="welcome/index.do" code="cancel"/>
	<input type="button" name="edit"
		value="<spring:message code='testMe' />" onclick="return testMe();" />

</form:form>