<%--
 * item/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="chapters" id="row" pagesize="5" class="displaytag">

<display:column titleKey = "number">
<jstl:out value="${row.number}" />
	</display:column>

<display:column titleKey = "title">
<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey = "duration">
<jstl:out value="${row.duration}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="chapter/public/show.do?chapterId=${row.id}"><spring:message code="show" /></a>
	</display:column>
	
	<security:authorize access="hasRole('PUBLISHER')">
	<display:column titleKey="edit">
		<a href="chapter/publisher/edit.do?chapterId=${row.id}"><spring:message code="edit"/></a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column titleKey="edit">
		<a href="chapter/administrator/edit.do?chapterId=${row.id}"><spring:message code="edit"/></a>
	</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('PUBLISHER')">
<p>
	<a href="chapter/publisher/create.do?seasonId=${seasonId}"><spring:message code="create" /></a>
</p>
</security:authorize>
