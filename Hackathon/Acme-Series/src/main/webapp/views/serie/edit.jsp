<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="serie" method="POST" action="serie/publisher/edit.do">

<!-- Hidden Fields -->

<form:hidden path="id" />
<form:hidden path="isDraft" />

<!-- Input Fields -->

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
	
	<div id="banner" class="banner">
		<form:label path="banner">
			<spring:message code="banner" />
		</form:label>
		<form:input path="banner" />
		<form:errors path="banner" />
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
	
	<div id="status" class="status">
		<form:label path="status">
			<spring:message code="status" />
		</form:label>
		<form:input path="status" />
		<form:errors path="status" />
	</div>
	
	<div id="director" class="director">
		<form:label path="director">
			<spring:message code="director" />
		</form:label>
		<form:input path="director" />
		<form:errors path="director" />
	</div>
	
	<div id="cast" class="cast">
		<form:label path="cast">
			<spring:message code="cast" />
		</form:label>
		<form:input path="cast" />
		<form:errors path="cast" />
	</div>
	

<!-- Form options -->

<input type="submit" name="save" value="<spring:message code="serie.send" />" />
<form action="serie/publisher/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${serie.id}' />" />
	<p>
	</p>
	<jstl:if test="${serie.isDraft}">
<input type="submit" name="delete" value="<spring:message code='serie.delete' />" />	
</jstl:if>
</form>

</form:form>
