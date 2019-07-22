<%--
 * legalRecord/show.jsp
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

<jstl:if test="${inceptionRecord == null}">
<a href="inceptionRecord/create.do"><spring:message code="create" /></a>
</jstl:if>

<display:table name="inceptionRecord" id="row" requestURI="history/show.do" class="displaytag">

<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="inceptionRecord/show.do?id=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="inceptionRecord/edit.do?id=${row.id}"><spring:message code="edit" /></a>
	</display:column>
	
</display:table>

<display:table name="legalRecords" id="row" requestURI="history/show.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="legalName">
		<jstl:out value="${row.legalName}" />
	</display:column>
	
	<display:column titleKey="VAT">
		<jstl:out value="${row.VAT}" />
	</display:column>
	
	<display:column titleKey="applicableLaws">
		<jstl:out value="${row.applicableLaws}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="legalRecord/show.do?id=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="legalRecord/edit.do?id=${row.id}"><spring:message code="edit" /></a>
	</display:column>
	
</display:table>

<display:table name="linkRecords" id="row" requestURI="history/show.do" pagesize="5" class="displaytag">
	
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

<display:table name="miscellaneousRecords" id="row" requestURI="history/show.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	
	<display:column titleKey="show">
		<a href="miscellaneousRecord/show.do?id=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="miscellaneousRecord/edit.do?id=${row.id}"><spring:message code="edit" /></a>
	</display:column>
	
</display:table>

<display:table name="periodRecords" id="row" requestURI="history/show.do" pagesize="5" class="displaytag">
	
	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="startYear">
		<jstl:out value="${row.startYear}" />
	</display:column>
	
	<display:column titleKey="endYear">
		<jstl:out value="${row.endYear}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="periodRecord/show.do?id=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="periodRecord/edit.do?id=${row.id}"><spring:message code="edit" /></a>
	</display:column>
</display:table>


