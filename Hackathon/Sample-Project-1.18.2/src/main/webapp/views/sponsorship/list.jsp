<%--
 * item/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="sponsorships" id="row" pagesize="5" class="displaytag">

<display:column titleKey = "targetPage">
<jstl:out value="${row.targetPage}" />
	</display:column>
	
	<display:column titleKey = "position">
<jstl:out value="${row.position.title}" />
	</display:column>
	
	<display:column titleKey = "provider">
<jstl:out value="${row.provider.make}" />
	</display:column>

<display:column titleKey="show">
		<a href="sponsorship/provider/show.do?sponsorshipId=${row.id}"><spring:message code="show" /></a>
	</display:column>

	<display:column titleKey="edit">
		<a href="sponsorship/provider/edit.do?sponsorshipId=${row.id}"><spring:message code="edit" /></a>
	</display:column>

</display:table>

<p>
	<a href="sponsorship/provider/create.do"><spring:message code="create" /></a>
</p>