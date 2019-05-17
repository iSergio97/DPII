<%--
socialprofile/list.jsp

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

<display:table name="socialProfiles" id="row" class="displaytag">

	<display:column titleKey="nick">
		<jstl:out value="${row.nick}" />
	</display:column>

	<display:column titleKey="socialNetworkName">
		<jstl:out value="${row.socialNetworkName}" />
	</display:column>

	<display:column titleKey="profileLink">
		<a href="<jstl:out value="${row.profileLink}" />"><spring:message code="profileLink" /></a>
	</display:column>

	<display:column titleKey="show">
		<a href="social-profile/all/show.do?id=${row.id}"><spring:message code="action.show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="social-profile/all/edit.do?id=${row.id}"><spring:message code="action.edit" /></a>
	</display:column>

	<display:column titleKey="delete">
		<form action="social-profile/all/delete.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value='${row.id}' />" />
			<input type="submit" name="delete" value="<spring:message code="action.delete" />" />
		</form>
	</display:column>

</display:table>

<p>
	<a href="social-profile/all/create.do"><spring:message code="action.create.socialProfile" /></a>
</p>
