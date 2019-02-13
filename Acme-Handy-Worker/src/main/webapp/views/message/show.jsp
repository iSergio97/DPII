<%--
 * box.jsp
 *
 * Copyright (C) 2018 Nozotro
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<strong><spring:message code="message.date"/>:</strong>
<jstl:out value="${message.date}"/><br/>
<strong><spring:message code="message.sender"/>:</strong>
<jstl:out value="${message.sender.name} " />
<jstl:out value="${message.sender.surname}" />
<br/>
<strong><spring:message code="message.recipients"/>:</strong>
<jstl:forEach items="${message.recipients}" var="recipient">
		<br>
		<jstl:out value="${recipient.name}" />
		<jstl:out value="${recipient.surname}" />
		<br/>
		</jstl:forEach>
<strong><spring:message code="message.priority"/>:</strong>
<jstl:out value="${message.priority}" /><br/>
<strong><spring:message code="message.subject"/>:</strong>
<jstl:out value="${message.subject}" /><br/>
<strong><spring:message code="message.tags"/>:</strong>
<jstl:forEach items="${message.tags}" var="tag">
	<br/>
	<jstl:out value="${tag}" />
	<br/>
</jstl:forEach>
	<br/>
<h3><spring:message code="message.body"/>:</h3>
<p>
<jstl:out value="${message.body}" /><br/>
</p>
<br/>
