<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="request.list" />
</p>

<display:table name="requests" id="row"
	requestURI="/request/brotherhood/list.do" pagesize="5"
	class="displaytag">

	<display:column property="procession.title" titleKey="request.procession"/>
	<display:column property="HLine" titleKey="request.hLine"/>
	<display:column property="VLine" titleKey="request.vLine" />
	<display:column property="status" titleKey="request.status" />
	<display:column> <a href="request/brotherhood/show.do?requestId=${row.id}" > <spring:message code="request.show"/></a></display:column>

</display:table>