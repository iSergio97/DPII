<%--
 * administrator/viewareas.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="areas" id="row" class="displaytag">

	<display:column titleKey="area.name">
		<jstl:out value="${row.name}" />
	</display:column>

	<display:column titleKey="area.pictures">
		<jstl:forEach items="${row.pictures}" var="picture">
			<jstl:out value="${picture}" />
			<br />
		</jstl:forEach>
	</display:column>

	<display:column titleKey="area.edit">
		<a href="administrator/editarea.do?id=${row.id}"><spring:message code="area.edit" /></a>
	</display:column>

	<display:column titleKey="area.delete">
		<form action="administrator/deletearea.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="submit" name="deletearea" value="<spring:message code='area.delete' />" />
		</form>
	</display:column>

</display:table>

<p>
	<a href="administrator/createarea.do"><spring:message code="area.create" /></a>
</p>
