<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<strong><spring:message code="minCompany" /></strong>:
	<jstl:if test="${minC eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${minC ne null}">
		<jstl:out value="${minC}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="maxCompany" /></strong>:
	<jstl:if test="${maxC eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${maxC ne null}">
		<jstl:out value="${maxC}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="avgCompany" /></strong>:
	<jstl:if test="${avgC eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${avgC ne null}">
		<jstl:out value="${avgC}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="stdDevCompany" /></strong>:
	<jstl:if test="${stdDevC eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${stdDevC ne null}">
		<jstl:out value="${stdDevC}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="minRookie" /></strong>:
	<jstl:if test="${minA eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${minA ne null}">
		<jstl:out value="${minA}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="maxRookie" /></strong>:
	<jstl:if test="${maxA eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${maxA ne null}">
		<jstl:out value="${maxA}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="avgRookie" /></strong>:
	<jstl:if test="${avgA eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${avgA ne null}">
		<jstl:out value="${avgA}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="stdDevRookie" /></strong>:
	<jstl:if test="${stdDevA eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${stdDevA ne null}">
		<jstl:out value="${stdDevA}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="minPositionAuditScore" /></strong>:
	<jstl:if test="${minPositionAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${minPositionAuditScore ne null}">
		<jstl:out value="${minPositionAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="maxPositionAuditScore" /></strong>:
	<jstl:if test="${maxPositionAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${maxPositionAuditScore ne null}">
		<jstl:out value="${maxPositionAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="avgPositionAuditScore" /></strong>:
	<jstl:if test="${avgPositionAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${avgPositionAuditScore ne null}">
		<jstl:out value="${avgPositionAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="stdDevPositionAuditScore" /></strong>:
	<jstl:if test="${stdDevPositionAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${stdDevPositionAuditScore ne null}">
		<jstl:out value="${stdDevPositionAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="minCompanyAuditScore" /></strong>:
	<jstl:if test="${minCompanyAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${minCompanyAuditScore ne null}">
		<jstl:out value="${minCompanyAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="maxCompanyAuditScore" /></strong>:
	<jstl:if test="${maxCompanyAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${maxCompanyAuditScore ne null}">
		<jstl:out value="${maxCompanyAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="avgCompanyAuditScore" /></strong>:
	<jstl:if test="${avgCompanyAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${avgCompanyAuditScore ne null}">
		<jstl:out value="${avgCompanyAuditScore}" />
	</jstl:if>
</p>
<p>
	<strong><spring:message code="stdDevCompanyAuditScore" /></strong>:
	<jstl:if test="${stdDevCompanyAuditScore eq null}">
		<spring:message code="noResults" />
	</jstl:if>
	<jstl:if test="${stdDevCompanyAuditScore ne null}">
		<jstl:out value="${stdDevCompanyAuditScore}" />
	</jstl:if>
</p>
<p>
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
</p>
<p>
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
</p>
