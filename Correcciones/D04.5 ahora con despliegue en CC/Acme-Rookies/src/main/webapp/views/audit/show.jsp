<%--
application/show.jsp

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
	<spring:message code="moment" />
	<br>
	<jstl:out value="${audit.moment}" />
</p>
<p>
	<spring:message code="text" />
	<br>
	<jstl:out value="${audit.text}" />
</p>
<p>
	<spring:message code="score" />
	<br>
	<jstl:out value="${audit.score}" />
</p>
<p>
	<spring:message code="isDraft" />
	<br>
	<jstl:if test="${audit.isDraft}">
		<spring:message code="isDraft.draft" />
	</jstl:if>
	<jstl:if test="${not audit.isDraft}">
		<spring:message code="isDraft.final" />
	</jstl:if>
</p>

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
