<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="chapter" method="POST" action="chapter/publisher/edit.do">

<!-- Hidden Fields -->

<form:hidden path="id" />
<form:hidden path="seasonId" />


<!-- Input Fields -->

<div id="number" class="number">
		<form:label path="number">
			<spring:message code="number" />
		</form:label>
		<form:input path="number" />
		<form:errors path="number" />
	</div>
	
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
	
	<div id="duration" class="duration">
		<form:label path="duration">
			<spring:message code="duration" />
		</form:label>
		<form:input path="duration" />
		<form:errors path="duration" />
	</div>
	
	
	<div id="releaseDate" class="releaseDate">
		<form:label path="releaseDate">
			<spring:message code="releaseDate" />
		</form:label>
		<form:input path="releaseDate" />
		<form:errors path="releaseDate" />
	</div>
	

<!-- Form options -->

<input type="submit" name="save" value="<spring:message code="send" />" />
<jstl:if test="${serie.id ne 0}">
<form action="chapter/publisher/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${chapter.id}' />" />
	
<input type="submit" name="delete" value="<spring:message code='delete' />" />	
</form>
</jstl:if>

</form:form>