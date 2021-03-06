<%--
 * position/listP.jsp
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<jstl:if test="${fn:length(companies) eq 0 }">
	<spring:message code="companies.empty.list" />
</jstl:if>

<jstl:if test="${fn:length(companies) gt 0 }">
	<display:table name="companies" id="row">

		<display:column property="commercialName">
			<jstl:out value="${row.commercialName}" />
		</display:column>
	
		<security:authorize access="hasRole('AUDITOR')">
			<jstl:if test="${row.status eq 'ACCEPTED'}">
				<display:column>
					<a href="audit/auditor/create.do?positionId=${row.id}"><spring:message code="audit" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>
	
		<security:authorize access="hasRole('ROOKIE')">
			<jstl:if test="${row.status eq 'ACCEPTED'}">
				<display:column>
					<a href="application/rookie/create.do?positionId=${row.id}"><spring:message code="apply" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>

		<display:column>
			<a href="position/all/show.do?companyId=${row.id}"><spring:message code="show" /></a>
		</display:column>

	</display:table>
</jstl:if>
