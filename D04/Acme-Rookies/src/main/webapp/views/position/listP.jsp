<%--
 * position/listP.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<jstl:if test="${fn:length(positions) eq 0 }">
	<spring:message code="position.empty.list" />
</jstl:if>


<form name="testing" action="position/all/list.do" method="post" >

	<spring:message code="keyword" />
	: <input type="text" value="" id="keyword" name="keyword"/>
	<button type="submit">
		<spring:message code="save" />
	</button>
</form>

<jstl:if test="${fn:length(positions) gt 0 }">
	<display:table name="positions" id="row" pagesize="5" sort="list"
		defaultsort="1" requestURI="position/all/list.do">

		<display:column property="title" sortable="true" sortName="title">
			<jstl:out value="${row.title}" />
		</display:column>
		<display:column property="description" sortable="true"
			sortName="description">
			<jstl:out value="${row.description}" />
		</display:column>
		<display:column property="deadline" sortable="true"
			sortName="deadline">
			<jstl:out value="${row.deadline }" />
		</display:column>
		<display:column property="profile" sortable="true" sortName="profile">
			<jstl:out value="${row.profile}" />
		</display:column>
		<display:column property="skills" sortable="true" sortName="skills">
			<jstl:out value="${row.skills}" />
		</display:column>
		<display:column property="technologies" sortable="true"
			sortName="technologies">
			<jstl:out value="${row.technologies}" />
		</display:column>
		<display:column property="salary" sortable="true" sortName="salary">
			<jstl:out value="${row.salary}" />
		</display:column>

		<display:column>
			<a href="position/all/show-company.do?positionId=${row.id}"><spring:message
					code="showCompany" /></a>
		</display:column>

	</display:table>
</jstl:if>