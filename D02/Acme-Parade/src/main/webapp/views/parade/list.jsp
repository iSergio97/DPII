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

	<display:column property="title">
		<jstl:out value="${parade.title}"/>
	</display:column>
	<display:column property="moment">
		<jstl:out value="${parade.moment}"/>
	</display:column>
	<display:column property="description">
		<jstl:out value="${parade.description}"/>
	</display:column>

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