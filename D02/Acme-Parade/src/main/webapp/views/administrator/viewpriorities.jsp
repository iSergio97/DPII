
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="priorities" id="row" class="displaytag">

	<display:column titleKey="priority.value">
		<jstl:forEach items="${row.strings}" var="entry">
			<jstl:out value="${entry.key}" />:<jstl:out value="${entry.value}" />
			<br />
		</jstl:forEach>
	</display:column>

	<display:column titleKey="priority.edit">
		<a href="administrator/editpriority.do?id=${row.id}"><spring:message code="priority.edit" /></a>
	</display:column>

	<display:column titleKey="priority.delete">
		<form action="administrator/deletepriority.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="submit" name="deletepriority" value="<spring:message code='priority.delete' />" />
		</form>
	</display:column>

</display:table>

<p>
	<a href="administrator/createpriority.do"><spring:message code="priority.create" /></a>
</p>
