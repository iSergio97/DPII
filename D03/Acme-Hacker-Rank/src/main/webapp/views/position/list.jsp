<%--
 * position/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="positions" id="row">

	<display:column property="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column property="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column property="info">
		<a href="position/company/show.do?positionId=${row.id}" ><spring:message code="show"/></a>
	</display:column>

</display:table>