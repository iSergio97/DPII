<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="sponsorship" method="POST" action="sponsorship/provider/edit.do">

<!-- Hidden Fields -->

<form:hidden path="id" />


<!-- Input Fields -->

<div id="banner" class="banner">
		<form:label path="banner">
			<spring:message code="banner" />
		</form:label>
		<form:input path="banner" />
		<form:errors path="banner" />
	</div>
	
	<div id="targetPage" class="targetPage">
		<form:label path="targetPage">
			<spring:message code="targetPage" />
		</form:label>
		<form:input path="targetPage" />
		<form:errors path="targetPage" />
	</div>
	
	<div id="position" class="position">
		<form:label path="position">
			<spring:message code="position" />
		</form:label>
		<form:select id="positions" path="position">
		<form:options items="${positions}" itemLabel = "title" itemValue="id"/>
		</form:select>
	</div>

<%-- Campos de tarjeta de crédito --%>
	<acme:register code="holder" path="holder"/>
	<acme:register code="brand" path="brand"/>
	<acme:register code="number" path="number"/>
	<acme:register code="expirationMonth" path="expirationMonth"/>
	<acme:register code="expirationYear" path="expirationYear"/>
	<acme:register code="cvv" path="CVV"/>
	
	

<!-- Form options -->

<input type="submit" name="save" value="<spring:message code="send" />" />
<input type="submit" name="delete" value="<spring:message code='delete' />" />


</form:form>