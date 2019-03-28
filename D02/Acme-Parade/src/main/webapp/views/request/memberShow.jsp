<%--
 * request/memberShow.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="request/member/list.do"><spring:message code="request.return" /></a><br/>

<p>
	<b><spring:message code="request.parade" /></b>
	
	<jstl:out value=": ${request.parade.title}" />
</p>

<p>
	<b><spring:message code="request.status" /></b>

	<jstl:choose>
		<jstl:when test = "${request.status == 'PENDING'}">
			<spring:message code="request.status.pending" />
		</jstl:when>
		<jstl:when test = "${request.status == 'APPROVED'}">
			<spring:message code="request.status.approved" />	
		</jstl:when>
		<jstl:when test = "${request.status == 'REJECTED'}">
			<spring:message code="request.status.rejected" />
		</jstl:when>
		<jstl:otherwise>
		</jstl:otherwise>
	</jstl:choose>
</p>

<p>
	<b><spring:message code="request.hLine" /></b>
	
	<jstl:out value=": ${request.HLine}" />
</p>

<p>
	<b><spring:message code="request.vLine" /></b>
	
	<jstl:out value=": ${request.VLine}" />
</p>

<jstl:if test="${request.status == 'REJECTED'}">
	<p>
		<b><spring:message code="request.reason" /></b>
	
		<jstl:out value=": ${request.reason}" />
	</p>
</jstl:if>

<jstl:if test="${request.status == 'PENDING'}">
	<form action="request/member/delete.do" method="POST">
		<input type="hidden" name="id" value="<jstl:out value='${request.id}' />" />
		<input type="submit" name="delete" value="<spring:message code='delete' />" />
	</form>
</jstl:if>
