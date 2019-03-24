<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="periodRecord" method="POST" action="periodRecord/edit.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />
	
	<!-- Input fields -->

	<div id="title" class="title">
		<form:label path="title">
			<spring:message code="title" />
		</form:label>
		<form:input path="title" />
		<form:errors path="title" />
	</div>
	
	
	<div id="description" class="description">
		<form:label path="description">
			<spring:message code="description" />
		</form:label>
		<form:input path="description" />
		<form:errors path="description" />
	</div>
	
	<div id="startYear" class="startYear">
		<form:label path="startYear">
			<spring:message code="startYear" />
		</form:label>
		<form:input path="startYear" />
		<form:errors path="startYear" />
	</div>
	
	<div id="endYear" class="endYear">
		<form:label path="endYear">
			<spring:message code="endYear" />
		</form:label>
		<form:input path="endYear" />
		<form:errors path="endYear" />
	</div>
	
	<!-- Form options -->

	<input type="submit" name="save" value="<spring:message code="send" />" />
	<input type="button" name="cancel" value="<spring:message code="cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />
	
	
</form:form>