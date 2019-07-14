<%--
 * message-box/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<spring:message code="messageBox.name" />
	: <em><jstl:out value="${messageBox.name}"></jstl:out></em>
</h2>

<h2>
	<spring:message code="messageBox.messages" />
	:
</h2>

<display:table name="messages" id="message" pagesize="10"
	class="displaytag">
	<display:column titleKey="message.subject">
		<jstl:out value="${message.subject}" />
	</display:column>
	<display:column titleKey="message.sender">
		<jstl:out value="${message.sender.name}" />
		<jstl:forEach items="${message.sender.surnames}" var="surname">
			<jstl:out value=" " />
			<jstl:out value="${surname}" />
		</jstl:forEach>
	</display:column>
	<display:column titleKey="message.priority">
		<jstl:choose>
			<jstl:when test="${message.priority == 'LOW'}">
				<spring:message code="message.priority.LOW" />
			</jstl:when>
			<jstl:when test="${message.priority == 'NEUTRAL'}">
				<spring:message code="message.priority.NEUTRAL" />
			</jstl:when>
			<jstl:when test="${message.priority == 'HIGH'}">
				<spring:message code="message.priority.HIGH" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	<display:column titleKey="options.options">
		<a href="message/all/show.do?id=<jstl:out value="${message.id}" />">
			<spring:message code="options.open" />
		</a>
	</display:column>

	<display:column>
		<jstl:if test="${messageBox.name eq 'TrashBox' }">
			<a href="message/all/delete.do?messageId=${message.id}"> <spring:message
					code="options.delete" />
			</a>
		</jstl:if>
	</display:column>
	<acme:cancel url="message-box/all/list.do" code="action.cancel" />
</display:table>