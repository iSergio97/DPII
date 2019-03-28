<%--
 * periodRecord/edit.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

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
	
	<p>
	<spring:message code="photos" />
	<jstl:set var="photoIndex" value="${0}" />
	<jstl:forEach items="${periodRecord.photos}" var="photo">
		<br>
		<jstl:out value="${photo}" />
		<form action="periodRecord/edit.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${periodRecord.id}' />" />
			<input type="hidden" name="photoIndex" value="<jstl:out value='${photoIndex}' />" />
			<input type="submit" name="deletePhoto" value="<spring:message code='deletePhoto' />" />
		</form>
		<jstl:set var="photoIndex" value="${photIndex + 1}" />
	</jstl:forEach>
</p>

<form action="periodRecord/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${periodRecord.id}' />" />
	<p>
		<spring:message code="addPhoto" />
		<br><input type="text" name="photo" />
	</p>
	<input type="submit" name="addPhoto" value="<spring:message code='addPhoto' />" />
</form>
	
	<!-- Form options -->

	<input type="submit" name="save" value="<spring:message code="send" />" />
	<form action="periodRecord/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${periodRecord.id}' />" />
	<p>
	</p>
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>
	<input type="button" name="cancel" value="<spring:message code="cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>
