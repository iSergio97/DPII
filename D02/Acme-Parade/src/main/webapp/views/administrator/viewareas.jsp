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
		<form action="administrator/editarea.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="text" name="name" value="<jstl:out value="${row.name}" />" />
			<input type="hidden" name="pictures" value="<jstl:forEach items="${row.pictures}" var="picture"><jstl:out value="${picture}" /><jstl:out value=" " /></jstl:forEach>" />
			<input type="submit" name="editarea" value="<spring:message code='area.edit' />" />
		</form>
	</display:column>

	<display:column titleKey="area.pictures">
		<form action="administrator/editarea.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="hidden" name="name" value="<jstl:out value="${row.name}" />" />
			<input type="text" name="pictures" value="<jstl:forEach items="${row.pictures}" var="picture"><jstl:out value="${picture}" /><jstl:out value=" " /></jstl:forEach>" />
			<input type="submit" name="editarea" value="<spring:message code='area.edit' />" />
		</form>
	</display:column>

	<display:column titleKey="area.delete">
		<form action="administrator/deletearea.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="submit" name="deletearea" value="<spring:message code='area.delete' />" />
		</form>
	</display:column>

</display:table>

<form action="administrator/addarea.do" method="POST">
	<input type="text" name="name" />
	<input type="text" name="pictures" />
	<input type="submit" name="addarea" value="<spring:message code='area.add' />" />
</form>
