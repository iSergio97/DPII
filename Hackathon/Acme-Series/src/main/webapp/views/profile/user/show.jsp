<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	function test() {
		//let patron = "[(][+][0-9]{2}[)] [0-9]{3} [0-9]{6,}";
		if (!document.getElementById("phoneNumber").value.includes("+34")) { return !confirm("Your phone number dont have prefix. Do you want to add one? \n Tu teléfono no tiene prefijo de zona. ¿Quiere añadir uno?"); }
	}
</script>

	<strong><spring:message code="name" /></strong>: <jstl:out value="${user.name}" />
	<br>
	<strong><spring:message code="surnames" /></strong>: <jstl:out value="${user.surnames}" />
	<br>
	<strong><spring:message code="email" /></strong>: <jstl:out value="${user.email}" />
	<br>
	<strong><spring:message code="photo" /></strong>: <a href="${user.photo }" target="_blank"> <spring:message code="photo"/></a>
	<br>
	<strong><spring:message code="phoneNumber" /></strong>: <jstl:out value="${user.phoneNumber}" />
	<br>
	<strong><spring:message code="address" /></strong>: <jstl:out value="${user.address}" />
	<br>
	<a href="profile/actor/export.do" ><spring:message code="profile.export" /></a>
	<br>
	<br>
	<a href="profile/user/delete.do" ><spring:message code="profile.delete" /></a>