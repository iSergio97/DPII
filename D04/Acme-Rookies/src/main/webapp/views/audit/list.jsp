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

<display:table name="audits" id="row" class="displaytag">

	<display:column titleKey="moment">
		<jstl:out value="${row.moment}" />
	</display:column>

	<display:column titleKey="text">
		<jstl:out value="${row.text}" />
	</display:column>

	<display:column titleKey="score">
		<jstl:out value="${row.score}" />
	</display:column>

	<display:column titleKey="isDraft">
		<jstl:if test="${row.isDraft}">
			<spring:message code="isDraft.draft" />
		</jstl:if>
		<jstl:if test="${not row.isDraft}">
			<spring:message code="isDraft.final" />
		</jstl:if>
	</display:column>

	<display:column titleKey="position">
		<jstl:out value="${row.position.title}" />
	</display:column>

	<security:authorize access="hasRole('AUDITOR')">
		<display:column titleKey="options">
			<a href="audit/auditor/show.do?auditId=${row.id}"><spring:message code="show" /></a>
			<jstl:if test="${row.isDraft}">
				<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="edit" /></a>
				<form action="audit/auditor/saveAsFinal.do" method="POST">
					<input type="hidden" name="auditId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="saveAsFinal" value="<spring:message code='saveAsFinal' />" />
				</form>
				<form action="audit/auditor/delete.do" method="POST">
					<input type="hidden" name="auditId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="delete" value="<spring:message code='delete' />" />
				</form>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>
