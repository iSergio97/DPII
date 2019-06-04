<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="season" method="POST" action="season/administrator/edit.do">

<!-- Hidden Fields -->

<form:hidden path="id" />
<form:hidden path="serieId" />


<!-- Input Fields -->

<div id="number" class="number">
		<form:label path="number">
			<spring:message code="number" />
		</form:label>
		<form:input path="number" />
		<form:errors path="number" />
	</div>
	
	<div id="startDate" class="startDate">
		<form:label path="startDate">
			<spring:message code="startDate" />
		</form:label>
		<form:input path="startDate" />
		<form:errors path="startDate" />
	</div>
	
	<div id="endDate" class="endDate">
		<form:label path="endDate">
			<spring:message code="endDate" />
		</form:label>
		<form:input path="endDate" />
		<form:errors path="endDate" />
	</div>
	
	

<!-- Form options -->

<input type="submit" name="save" value="<spring:message code="send" />" />
<form action="season/administrator/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${season.id}' />" />
	
<input type="submit" name="delete" value="<spring:message code='delete' />" />	
</form>

</form:form>