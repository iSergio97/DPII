<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="item" method="POST" action="item/provider/edit.do">

<!-- Hidden Fields -->

<form:hidden path="id" />


<!-- Input Fields -->

<div id="name" class="name">
		<form:label path="name">
			<spring:message code="name" />
		</form:label>
		<form:input path="name" />
		<form:errors path="name" />
	</div>
	
	<div id="description" class="description">
		<form:label path="description">
			<spring:message code="description" />
		</form:label>
		<form:input path="description" />
		<form:errors path="description" />
	</div>
	
	<div id="link" class="link">
		<form:label path="link">
			<spring:message code="link" />
		</form:label>
		<form:input path="link" />
		<form:errors path="link" />
	</div>
	
	<div id="pictures" class="pictures">
		<form:label path="pictures">
			<spring:message code="pictures" />
		</form:label>
		<form:input path="pictures" />
		<form:errors path="pictures" />
	</div>
	

<!-- Form options -->

<input type="submit" name="save" value="<spring:message code="send" />" />
<input type="submit" name="delete" value="<spring:message code='delete' />" />

</form:form>