<%--
 * request/brotherhoodList.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="request.list" />
</p>

<display:table name="requests" id="row"
	requestURI="/request/brotherhood/list.do" pagesize="5"
	class="displaytag">

	<display:column titleKey="request.parade">
		<jstl:out value="${row.parade.title}" />
	</display:column>
	<display:column titleKey="request.hLine">
		<jstl:out value="${row.HLine}" />
	</display:column>
	<display:column titleKey="request.vLine">
		<jstl:out value="${row.VLine}" />
	</display:column>
	<display:column titleKey="request.status">
		<jstl:out value="${row.status}" />
	</display:column>
	<display:column>
		<a href="request/brotherhood/show.do?requestId=${row.id}" > <spring:message code="request.show"/></a>
	</display:column>

</display:table>
