<%--
 * periodRecord/show.jsp
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

<p>
	<spring:message code="title" />
	<br>
	<jstl:out value="${periodRecord.title}" />
</p>

<p>
	<spring:message code="description" />
	<br>
	<jstl:out value="${periodRecord.title}" />
</p>

<p>
	<spring:message code="startYear" />
	<br>
	<jstl:out value="${periodRecord.startYear}" />
</p>

<p>
	<spring:message code="endYear" />
	<br>
	<jstl:out value="${periodRecord.endYear}" />
</p>

<p>
	<spring:message code="photos" />
	<jstl:set var="photoIndex" value="${0}" />
	<jstl:forEach items="${periodRecord.photos}" var="photo">
		<br>
		<jstl:out value="${photo}" />
		<form action="periodRecord/show.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${acmeFloat.id}' />" />
			<input type="hidden" name="photoIndex" value="<jstl:out value='${photoIndex}' />" />
			<input type="submit" name="deletePhoto" value="<spring:message code='deletePhoto' />" />
		</form>
		<jstl:set var="photoIndex" value="${photIndex + 1}" />
	</jstl:forEach>
</p>

<form action="acmefloat/brotherhood/show.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${acmeFloat.id}' />" />
	<p>
		<spring:message code="addPhoto" />
		<br><input type="text" name="photo" />
	</p>
	<input type="submit" name="addPhoto" value="<spring:message code='addPhoto' />" />
</form>

<form action="periodRecord/delete.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${periodRecord.id}' />" />
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>

<input type="button" name="edit" value="<spring:message code='edit' />" onclick="javascript: relativeRedir('periodRecord/edit.do?id=${periodRecord.id}');" />
