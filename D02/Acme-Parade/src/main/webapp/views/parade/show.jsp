<%--
 * parade/show.jsp
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

	<!-- Output fields -->
	
<strong>
	<spring:message code="parade.title"/>:
</strong>
	<jstl:out value="${parade.title}"></jstl:out>
<br>

<strong>
	<spring:message code="parade.description"/>:
</strong>
	<jstl:out value="${parade.description}"/>
<br>

<strong>
	<spring:message code="parade.moment"/>:
</strong>
	<jstl:out value="${parade.moment}"/>
<br>
<jstl:if test="${parade.isDraft eq true}">
	<spring:message code="parade.draft"/>
	<br>
</jstl:if>

<h2>
	<spring:message code="parade.acmeFloats"/>:
</h2>
<display:table name="parade.acmeFloats" id="row" pagesize="5" class="displaytag">

	<display:column titleKey="float.title">
		<jstl:out value="${row.title}"/>
	</display:column>
	<display:column titleKey="float.description">
		<jstl:out value="${row.description}"/>
	</display:column>

	<display:column titleKey="master.page.blank">
		<a href="float/public/show.do?floatId=<jstl:out value="${row.id}" />">
			<spring:message code="master.page.action.show"/>
		</a>
	</display:column>

</display:table>

	<!-- Buttons -->
	
<acme:cancel url="welcome/index.do" code="master.page.action.cancel"/>
