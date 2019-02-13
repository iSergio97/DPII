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

<display:table name="messageBox.messages" id="message">
	<display:column titleKey="message.subject">
		<a href="message/displaymessage.do?id=<jstl:out value="${message.id}" />"><jstl:out value="${message.subject}" /></a>
	</display:column>
	<display:column titleKey="message.sender">
		<jstl:out value="${message.sender.name}" />
		<jstl:out value=" " />
		<jstl:out value="${message.sender.middleName}" />
		<jstl:out value=" " />
		<jstl:out value="${message.sender.surname}" />
	</display:column>
	<display:column titleKey="message.priority">
		<jstl:choose>
			<jstl:when test = "${message.priority == 'LOW'}">
				<spring:message code="message.priority.LOW" />
			</jstl:when>
			<jstl:when test = "${message.priority == 'NEUTRAL'}">
				<spring:message code="message.priority.NEUTRAL" />
			</jstl:when>
			<jstl:when test = "${message.priority == 'HIGH'}">
				<spring:message code="message.priority.HIGH" />
			</jstl:when>
		</jstl:choose>
	</display:column>
</display:table>
