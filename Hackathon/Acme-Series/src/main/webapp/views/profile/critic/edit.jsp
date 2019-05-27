<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" >
function showPass() {
	var showPass = document.getElementById("password");
	var showPassC = document.getElementById("confirmPassword");
	if(showPass.type == 'text') {
		showPass.type = 'password';
		showPassC.type = 'password';
	} else {
		showPass.type = 'text';
		showPassC.type = 'text';
	}
	return false;
}

function test() {
	//let patron = "[(][+][0-9]{2}[)] [0-9]{3} [0-9]{6,}";
	if (!document.getElementById("phoneNumber").value.includes("+")) { return !confirm("Your phone number dont have prefix. Do you want to add one? \n Tu teléfono no tiene prefijo de zona. ¿Quiere añadir uno?"); }
}
</script>


<form:form modelAttribute="critic" action="profile/critic/edit.do" method="POST">

	<form:hidden path="id" />

	<%-- Campos de usuario --%>
	<fieldset>
		<legend>
			<spring:message code="actorProperties" />
		</legend>
		<acme:register code="name" path="name" />
		<acme:register code="surnames" path="surnames" />
		<acme:register code="email" path="email" />
		<acme:registerWithPlaceholder code="photo" path="photo"
			placeholder="https://imgur.com/r/panda/vhGv9Z0" />
		<acme:registerWithPlaceholder code="phoneNumber" path="phoneNumber"
			placeholder="+XX (YYY) ZZZZZZ" />
		<acme:register code="address" path="address" />
	</fieldset>

	<br>

	<fieldset>
		<legend>
			<spring:message code="userAccountProperties" />
		</legend>
		<acme:register code="username" path="username" />
		<acme:password code="password" path="password" />
		<acme:password code="confirmPassword" path="confirmPassword" />
		<button onclick="return showPass();"> <spring:message code="showOrHidePass" /></button>
	</fieldset>

	<br>

	<%-- <acme:submit name="save" code="save"/> --%>
	<button type="submit" onClick="return test();">
		<spring:message code="save" />
	</button>
	<acme:cancel url="welcome/index.do" code="cancel" />

</form:form>