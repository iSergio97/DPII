<%--
 * message-box/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="messageBoxes" id="messageBox" pagesize="5" requestURI="message-box/edit.do" class="displaytag">
	<display:column titleKey="messageBox.name">
		<jstl:out value="${messageBox.name}" />
	</display:column>
	<display:column titleKey="blank">
		<a href="message-box/edit.do?id=<jstl:out value="${messageBox.id}" />">
			<spring:message code="action.edit"/>
		</a>
	</display:column>
</display:table>

<a href="message-box/create.do">
	<spring:message code="action.create.messageBox"/>
</a>
