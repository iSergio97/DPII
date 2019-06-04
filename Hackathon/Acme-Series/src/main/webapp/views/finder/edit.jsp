<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="finder" method="POST" action="finder/user/edit.do">

<!-- Hidden fields -->

	<form:hidden path="id" />

<!-- Input fields -->

<acme:register code="keyword" path="keyword"/>
<acme:register code="minDate" path="minDate"/>
<acme:register code="maxDate" path="maxDate"/>
	
	<!-- Form options -->
	
	<input type="submit" name="save" value="<spring:message code="send" />" />

</form:form>