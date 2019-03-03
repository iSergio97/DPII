
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="positions" id="row" class="displaytag">

	<display:column titleKey="position.value">
		<form action="administrator/editposition.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="text" name="position" value="<jstl:forEach items="${row.strings}" var="entry"><jstl:out value="${entry.key}" />:<jstl:out value="${entry.value}" />;</jstl:forEach>" />
			<input type="submit" name="editposition" value="<spring:message code='position.edit' />" />
		</form>
	</display:column>

	<display:column titleKey="position.delete">
		<form action="administrator/deleteposition.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="submit" name="deleteposition" value="<spring:message code='position.delete' />" />
		</form>
	</display:column>

</display:table>

<form action="administrator/addposition.do" method="POST">
	<input type="text" name="position" />
	<input type="submit" name="addposition" value="<spring:message code='position.add' />" />
</form>
