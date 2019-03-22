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

<display:table name="parades" id="row"
	requestURI="parade/brotherhood/list.do" pagesize="5" class="displaytag">

	<display:column property="title" titleKey="parade.title" />
	<display:column property="moment" titleKey="parade.moment" />
	<display:column property="description" titleKey="parade.description" />

	<display:column titleKey="master.page.blank">
		<jstl:if test="${row.isDraft == true}">
			<a href="parade/brotherhood/edit.do?paradeId=<jstl:out value="${row.id}" />">
				<spring:message code="master.page.action.edit"/>
			</a>
		</jstl:if>
	</display:column>
	
	<display:column titleKey="master.page.blank">
		<a href="parade/public/show.do?paradeId=<jstl:out value="${row.id}" />">
			<spring:message code="master.page.action.show"/>
		</a>
	</display:column>

</display:table>