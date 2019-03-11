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

<a href="request/brotherhood/list.do"><spring:message code="request.return" /></a><br/>

<p>
	<b><spring:message code="request.parade" /></b>
	<br>
	<jstl:out value=": ${request.parade.title}" />
</p>

<p>
	<jstl:choose>
			<jstl:when test = "${request.status == 'PENDING'}">
				<spring:message code="request.status.pending" />
				<br/>
			</jstl:when>
			<jstl:when test = "${request.status == 'APPROVED'}">
				<spring:message code="request.status.approved" />
				<br/>
			</jstl:when>
			<jstl:when test = "${request.status == 'REJECTED'}">
				<spring:message code="request.status.rejected" />
				<br/>
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

<jstl:if test="${request.status =='REJECTED'}">
	<p>
		<b><spring:message code="request.reason" /></b>
		<jstl:out value=": ${request.reason}" />
	</p>
</jstl:if>


<jstl:if test="${request.status == 'PENDING'}">
<p>
<input type="button" name="accept"
		value="<spring:message code='request.accept' />"
		onclick="javascript: relativeRedir('request/brotherhood/accept.do?id=${request.id}');" />

<input type="button" name="reject"
		value="<spring:message code='request.reject' />"
		onclick="javascript: relativeRedir('/request/brotherhood/reject.do?id=${request.id}');" />
</p>
</jstl:if>


