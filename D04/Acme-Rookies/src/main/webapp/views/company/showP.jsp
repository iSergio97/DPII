<%--
 * position/showP.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Dom�nguez
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

<jstl:if test="${fn:length(positions) gt 0 }">
	<spring:message code="position.hidden.problem" />
<display:table name="positions" id="row" pagesize="5" sort="list" requestURI="company/all/show.do">

	<display:column property="title" sortable="true"> <jstl:out value="${row.title}" /> </display:column>
	<display:column property="description" sortable="true"> <jstl:out value="${row.description}" /> </display:column>
	<display:column property="deadline" sortable="true"> <jstl:out value="${row.deadline }" /> </display:column>
	<display:column property="profile" sortable="true"> <jstl:out value="${row.profile}" /> </display:column>
	<display:column property="skills" sortable="true"> <jstl:out value="${row.skills}" /> </display:column>
	<display:column property="technologies" sortable="true"> <jstl:out value="${row.technologies}" /> </display:column>
	<display:column property="salary" sortable="true"> <jstl:out value="${row.salary}" /> </display:column>
	
	<security:authorize access="hasRole('ROOKIE')">
		<display:column>
			<a href="application/rookie/create.do?positionId=${row.id}"><spring:message code="apply" /></a>
		</display:column>
	</security:authorize>

</display:table>
</jstl:if>

<jstl:if test="${fn:length(positions) eq 0 }">
	<spring:message code="positions.empty.list" />
</jstl:if>