<%--
 * brotherhood/show.jsp
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<!-- Output fields -->
	
<strong>
	<spring:message code="brotherhood.title"/>:
</strong>
	<jstl:out value="${brotherhood.title}"/>
<br>

<strong>
	<spring:message code="brotherhood.establishmentDate"/>:
</strong>
	<jstl:out value="${establishmentDate}"/>
<br>

<strong>
	<spring:message code="brotherhood.area"/>:
</strong>
	<jstl:out value="${brotherhood.area.name}"/>
<br>

<jstl:if test="${fn:length(brotherhood.pictures) eq 0}">
	<strong>
		<spring:message code="brotherhood.pictures" />:
	</strong>
		<spring:message code="brotherhood.noPictures" />
</jstl:if>
<jstl:if test="${fn:length(brotherhood.pictures) ne 0}">
	<h2>
		<spring:message code="brotherhood.pictures"/>
	</h2>
		<jstl:forEach items="${brotherhood.pictures}" var="picture">
			<img alt="picture" src='<jstl:out value="${picture}"></jstl:out>'>
		</jstl:forEach>
</jstl:if>
<br>

<jstl:if test="${fn:length(parades) eq 0}">
	<strong>
		<spring:message code="brotherhood.parades"/>
	</strong>
		<spring:message code="brotherhood.noParades"/>
</jstl:if>

<jstl:if test="${fn:length(acmeFloats) eq 0}">
	<strong>
		<spring:message code="brotherhood.floats"/>
	</strong>
		<spring:message code="brotherhood.noFloats"/>
</jstl:if>

<jstl:if test="${fn:length(members) eq 0}">
	<strong>
		<spring:message code="brotherhood.members"/>
	</strong>
		<spring:message code="brotherhood.noMembers"/>
</jstl:if>

<jstl:if test="${fn:length(parades) ne 0}">
	<h2>
		<spring:message code="brotherhood.parades"/>
	</h2>
	<display:table name="parades" id="row" pagesize="5" class="displaytag">

		<display:column titleKey="parade.title">
			<jstl:out value="${row.title}"/>
		</display:column>
		<display:column titleKey="parade.moment">
			<jstl:out value="${row.moment}"/>
		</display:column>
		<display:column titleKey="parade.description">
			<jstl:out value="${row.description}"/>
		</display:column>

		<display:column titleKey="master.page.blank">
			<a href="parade/public/show.do?paradeId=<jstl:out value="${row.id}" />"><spring:message code="master.page.action.show"/></a>
		</display:column>

	</display:table>
</jstl:if>

<jstl:if test="${fn:length(acmeFloats) ne 0}">
	<h2>
		<spring:message code="brotherhood.floats"/>
	</h2>
	<display:table name="acmeFloats" id="row" pagesize="5" class="displaytag">

		<display:column property="title" titleKey="float.title">
			<jstl:out value="${row.title}" />
		</display:column>
		<display:column titleKey="float.description">
			<jstl:out value="${row.description}" />
		</display:column>

		<display:column titleKey="master.page.blank">
			<a href="float/public/show.do?floatId=<jstl:out value="${row.id}" />"><spring:message code="master.page.action.show"/></a>
		</display:column>

	</display:table>
</jstl:if>

<jstl:if test="${fn:length(members) ne 0}">
	<h2>
		<spring:message code="brotherhood.members"/>
	</h2>
	<display:table name="members" id="row" pagesize="5" class="displaytag">

		<display:column titleKey="actor.name">
			<jstl:out value="${row.name}" />
		</display:column>
		<display:column titleKey="actor.middleName">
			<jstl:if test="${not empty row.middleName}">
				<jstl:out value="${row.middleName}" />
			</jstl:if>
			<jstl:if test="${empty row.middleName}">
				<jstl:out value="-"></jstl:out>
			</jstl:if>
		</display:column>
		<display:column titleKey="actor.surname">
			<jstl:out value="${row.surname}" />
		</display:column>

	</display:table>
</jstl:if>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>
