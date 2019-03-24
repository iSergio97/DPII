<%--
 * list.jsp
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

<display:table name="linkRecords" id="row" requestURI="linkRecord/list.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="brotherhood">
		<jstl:out value="${row.brotherhood.title}" />
	</display:column>
	
	
	
	<display:column titleKey="show">
		<a href="linkRecord/show.do?id=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="linkRecord/edit.do?id=${row.id}"><spring:message code="edit" /></a>
	</display:column>
	
</display:table>

<p>
	<a href="linkRecord/create.do"><spring:message code="create" /></a>
</p>