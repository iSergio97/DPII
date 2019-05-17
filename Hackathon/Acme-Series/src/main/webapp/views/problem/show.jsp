<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<strong><spring:message code="title" />:</strong>
	<br>
	<jstl:out value="${problem.title}" />
</p>

<p>
	<strong><spring:message code="statement" />:</strong>
	<br>
	<jstl:out value="${problem.statement}" />
</p>

<p>
	<strong><spring:message code="hint" />:</strong>
	<br>
	<jstl:out value="${problem.hint}" />
</p>

<p>
	<strong><spring:message code="attachments" />:</strong>
	<br>
	<jstl:out value="${problem.attachments}" />
</p>

<jstl:if test="${problem.isDraft}">
<input type="button" name="edit" value="<spring:message code='edit' />" onclick="javascript: relativeRedir('problem/edit.do?problemId=${problem.id}');" />
</jstl:if>