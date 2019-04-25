<%--
 * legalRecord/list.jsp
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

<display:table name="problems" id="row" requestURI="/problem/list.do" pagesize="5" class="displaytag">

<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="statement">
		<jstl:out value="${row.statement}" />
	</display:column>
	
	<display:column titleKey="hint">
		<jstl:out value="${row.hint}" />
	</display:column>
	
	<display:column titleKey="attachments">
		<jstl:out value="${row.attachments}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="problem/show.do?problemId=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="problem/edit.do?problemId=${row.id}"><spring:message code="edit" /></a>
	</display:column>

</display:table>

<p>
	<a href="problem/create.do?positionId=${positionId}"><spring:message code="create" /></a>
</p>