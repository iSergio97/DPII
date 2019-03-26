<%--
 * show.jsp
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

<jstl:if test="${fn:length(brotherhood.pictures) ne 0}">
	<h2>
		<spring:message code="brotherhood.pictures"/>
	</h2>
		<jstl:forEach items="${brotherhood.pictures}" var="picture">
			<img alt="picture" src='<jstl:out value="${picture}"></jstl:out>'>
		</jstl:forEach>
</jstl:if>

<jstl:if test="${fn:length(brotherhood.pictures) eq 0}">
	<strong>
		<spring:message code="brotherhood.pictures" />:
	</strong>
		<spring:message code="brotherhood.noPictures" />
</jstl:if>
<br>

<h2>
	<spring:message code="brotherhood.parades"/>
</h2>
<display:table name="parades" id="row" pagesize="5" class="displaytag">

	<display:column property="title" titleKey="parade.title" />
	<display:column property="moment" titleKey="parade.moment" />
	<display:column property="description" titleKey="parade.description" />

	<display:column titleKey="master.page.blank">
		<a href="parade/public/show.do?paradeId=<jstl:out value="${row.id}" />">
			<spring:message code="master.page.action.show"/>
		</a>
	</display:column>

</display:table>

<h2>
	<spring:message code="brotherhood.floats"/>
</h2>
<display:table name="acmeFloats" id="row" pagesize="5" class="displaytag">

	<display:column property="title" titleKey="float.title" />
	<display:column property="description" titleKey="float.description" />

	<display:column titleKey="master.page.blank">
		<a href="float/public/show.do?floatId=<jstl:out value="${row.id}" />">
			<spring:message code="master.page.action.show"/>
		</a>
	</display:column>

</display:table>

<h2>
	<spring:message code="brotherhood.members"/>
</h2>
<display:table name="members" id="row" pagesize="5" class="displaytag">

	<display:column property="name" titleKey="actor.name" />
	<display:column property="surname" titleKey="actor.surname" />

</display:table>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>
