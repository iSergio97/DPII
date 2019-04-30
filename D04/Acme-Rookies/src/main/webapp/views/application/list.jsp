<%--
application/list.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="status.pending" />
</p>

<display:table name="pendingApplications" id="row" class="displaytag">

	<display:column titleKey="submitMoment">
		<jstl:out value="${row.submitMoment}" />
	</display:column>

	<display:column titleKey="position">
		<jstl:out value="${row.position.title}" />
	</display:column>

	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="show">
			<a href="application/rookie/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
		
		<display:column titleKey="options">
			<a href="application/rookie/submit.do?id=${row.id}"><spring:message code="submit" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="rookie">
			<jstl:out value="${row.rookie.name}" /> <jstl:out value="${row.rookie.surnames}" />
		</display:column>
		
		<display:column titleKey="show">
			<a href="application/company/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	</security:authorize>

</display:table>

<p>
	<spring:message code="status.submitted" />
</p>

<display:table name="submittedApplications" id="row" class="displaytag">

	<display:column titleKey="submitMoment">
		<jstl:out value="${row.submitMoment}" />
	</display:column>

	<display:column titleKey="position">
		<jstl:out value="${row.position.title}" />
	</display:column>

	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="show">
			<a href="application/rookie/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	
		<display:column titleKey="options">
			<a href="application/rookie/submit.do?id=${row.id}"><spring:message code="submit" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="rookie">
			<jstl:out value="${row.rookie.name}" /> <jstl:out value="${row.rookie.surnames}" />
		</display:column>
		
		<display:column titleKey="show">
			<a href="application/company/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
		
		<display:column titleKey="options">
			<form action="application/company/accept.do" method="POST">
				<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
				<input type="submit" name="accept" value="<spring:message code='accept' />" />
			</form>
			<form action="application/company/reject.do" method="POST">
				<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
				<input type="submit" name="reject" value="<spring:message code='reject' />" />
			</form>
		</display:column>
	</security:authorize>

</display:table>

<p>
	<spring:message code="status.rejected" />
</p>

<display:table name="rejectedApplications" id="row" class="displaytag">

	<display:column titleKey="submitMoment">
		<jstl:out value="${row.submitMoment}" />
	</display:column>

	<display:column titleKey="position">
		<jstl:out value="${row.position.title}" />
	</display:column>

	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="show">
			<a href="application/rookie/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="rookie">
			<jstl:out value="${row.rookie.name}" /> <jstl:out value="${row.rookie.surnames}" />
		</display:column>
		
		<display:column titleKey="show">
			<a href="application/company/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	</security:authorize>

</display:table>

<p>
	<spring:message code="status.accepted" />
</p>

<display:table name="acceptedApplications" id="row" class="displaytag">

	<display:column titleKey="submitMoment">
		<jstl:out value="${row.submitMoment}" />
	</display:column>

	<display:column titleKey="position">
		<jstl:out value="${row.position.title}" />
	</display:column>

	<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="show">
			<a href="application/rookie/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('COMPANY')">
		<display:column titleKey="rookie">
			<jstl:out value="${row.rookie.name}" /> <jstl:out value="${row.rookie.surnames}" />
		</display:column>
		
		<display:column titleKey="show">
			<a href="application/company/show.do?id=${row.id}"><spring:message code="show" /></a>
		</display:column>
	</security:authorize>

</display:table>
