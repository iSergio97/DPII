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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="messageBoxes" id="messageBox" pagesize="5" requestURI="message-box/edit.do" class="displaytag">
	<display:column titleKey="messageBox.name">
		<jstl:out value="${messageBox.name}" />
	</display:column>
	<display:column titleKey="options.blank">
		<a href="message-box/edit.do?id=<jstl:out value="${messageBox.id}" />">
			<spring:message code="options.edit"/>
		</a>
	</display:column>
</display:table>

<a href="message-box/create.do">
	<spring:message code="options.create"/>
</a>
