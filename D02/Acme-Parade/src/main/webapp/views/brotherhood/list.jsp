<%--
 * brotherhood/list.jsp
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

<display:table name="brotherhoods" id="row"
	requestURI="brotherhood/public/list.do" pagesize="5" class="displaytag">

	<display:column titleKey="brotherhood.title">
		<jstl:out value="${row.title}" />
	</display:column>
	<display:column titleKey="brotherhood.establishmentDate">
		<jstl:out value="${row.establishmentDate}" />
	</display:column>

	<display:column titleKey="master.page.blank">
		<a href="brotherhood/public/show.do?brotherhoodId=<jstl:out value="${row.id}" />">
			<spring:message code="master.page.action.show"/>
		</a>
	</display:column>

</display:table>
