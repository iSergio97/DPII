<%--
 * miscellaneousRecord/show.jsp
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
	<jstl:out value="${miscellaneousRecord.title}" />
</p>

<p>
	<spring:message code="description" />
	<br>
	<jstl:out value="${miscellaneousRecord.title}" />
</p>




<form action="miscellaneousRecord/delete.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${miscellaneousRecord.id}' />" />
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>

<input type="button" name="edit" value="<spring:message code='edit' />" onclick="javascript: relativeRedir('miscellaneousRecord/edit.do?id=${miscellaneousRecord.id}');" />


