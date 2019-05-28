<%--
 * serie/show.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<strong><spring:message code="number" /></strong>
:
<jstl:out value="${season.number}" />
<br>



<strong><spring:message code="startDate" /></strong>
:
<jstl:out value="${season.startDate}" />
<br>

<strong><spring:message code="endDate" /></strong>
:
<jstl:out value="${season.endDate}" />
<br>


<strong><a href="chapter/publisher/list.do?seasonId=${season.id}"><spring:message code="chapters" /></a></strong>



<p>
	<a href="serie/publisher/edit.do?seasonId=${season.id}"><spring:message code="edit" /></a>
</p>

