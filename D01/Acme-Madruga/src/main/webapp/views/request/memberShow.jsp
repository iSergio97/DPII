<%--
 * action-2.jsp
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

<a href="request/member/list.do"><spring:message code="request.return" /></a><br/>

<p>
	<spring:message code="request.procession" />
	
	<jstl:out value=": ${request.procession.title}" />
</p>

<p>
	<spring:message code="request.status" />

	<jstl:choose>
		<jstl:when test = "${request.status == 'PENDING'}">
			<spring:message code="request.status.pending" />
		</jstl:when>
		<jstl:when test = "${request.status == 'ACCEPTED'}">
			<spring:message code="request.status.accepted" />	
		</jstl:when>
		<jstl:when test = "${request.status == 'REJECTED'}">
			<spring:message code="request.status.rejected" />
		</jstl:when>
		<jstl:otherwise>
		</jstl:otherwise>
	</jstl:choose>
</p>

<p>
	<spring:message code="request.hLine" />
	
	<jstl:out value=": ${request.HLine}" />
</p>

<p>
	<spring:message code="request.vLine" />
	
	<jstl:out value=": ${request.VLine}" />
</p>

<p>
	<spring:message code="request.reason" />
	
	<jstl:out value=": ${request.reason}" />
</p>

<!-- <jstl:if test="${request.status == 'PENDING'}">
<form action="request/member/delete.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${request.id}' />" />
	<input type="submit" name="delete" value="<spring:message code='delete' />" />
</form>
</jstl:if> -->

