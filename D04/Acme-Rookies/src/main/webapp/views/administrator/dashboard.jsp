<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<strong><spring:message code="minCompany" /></strong>
:
<jstl:out value="${minC}" />
<br>
<strong><spring:message code="maxCompany" /></strong>
:
<jstl:out value="${maxC}" />
<br>
<strong><spring:message code="avgCompany" /></strong>
:
<jstl:out value="${avgC}" />
<br>
<strong><spring:message code="stdDevCompany" /></strong>
:
<jstl:out value="${stdDevC}" />
<br>
<br>
<br>
<strong><spring:message code="minRookie" /></strong>
:
<jstl:out value="${minA}" />
<br>
<strong><spring:message code="maxRookie" /></strong>
:
<jstl:out value="${maxA}" />
<br>
<strong><spring:message code="avgRookie" /></strong>
:
<jstl:out value="${avgA}" />
<br>
<strong><spring:message code="stdDevRookie" /></strong>
:
<jstl:out value="${stdDevA}" />
<br>
<br>
<br>
<strong><spring:message code="minPositionAuditScore" /></strong>
:
<jstl:out value="${minPositionAuditScore}" />
<br>
<strong><spring:message code="maxPositionAuditScore" /></strong>
:
<jstl:out value="${maxPositionAuditScore}" />
<br>
<strong><spring:message code="avgPositionAuditScore" /></strong>
:
<jstl:out value="${avgPositionAuditScore}" />
<br>
<strong><spring:message code="stdDevPositionAuditScore" /></strong>
:
<jstl:out value="${stdDevPositionAuditScore}" />
<br>
<br>
<br>
<strong><spring:message code="minCompanyAuditScore" /></strong>
:
<jstl:out value="${minCompanyAuditScore}" />
<br>
<strong><spring:message code="maxCompanyAuditScore" /></strong>
:
<jstl:out value="${maxCompanyAuditScore}" />
<br>
<strong><spring:message code="avgCompanyAuditScore" /></strong>
:
<jstl:out value="${avgCompanyAuditScore}" />
<br>
<strong><spring:message code="stdDevCompanyAuditScore" /></strong>
:
<jstl:out value="${stdDevCompanyAuditScore}" />
<br>
<br>
<br>
<display:table name="companiesWithTheHighestAuditScore" id="row" class="displaytag">

	<display:column titleKey="company">
		<jstl:out value="${row.commercialName}" />
	</display:column>

	<display:column titleKey="averageSalary">
		<jstl:if test="${companiesWithTheHighestAuditScoreAndTheirAverageSalary[row] eq null}">
			<spring:message code="noResults" />
		</jstl:if>
		<jstl:if test="${companiesWithTheHighestAuditScoreAndTheirAverageSalary[row] ne null}">
			<jstl:out value="${companiesWithTheHighestAuditScoreAndTheirAverageSalary[row]}" />
		</jstl:if>
	</display:column>

</display:table>
<br>
<br>
<br>
<display:table name="companies" id="row" class="displaytag">

	<display:column titleKey="company">
		<jstl:out value="${row.commercialName}" />
	</display:column>

	<display:column titleKey="auditScore">
		<jstl:if test="${companiesAndTheirScore[row] eq null}">
			<spring:message code="noResults" />
		</jstl:if>
		<jstl:if test="${companiesAndTheirScore[row] ne null}">
			<jstl:out value="${companiesAndTheirScore[row]}" />
		</jstl:if>
	</display:column>

</display:table>
<br>
<br>
