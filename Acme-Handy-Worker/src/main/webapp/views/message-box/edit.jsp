<%--
 * edit.jsp
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

<jstl:if test="${name != 'InBox' && name != 'OutBox' && name != 'TrashBox' && name != 'SpamBox'}">
	<h2><spring:message code="options.editData"/>:</h2>
	<form:form modelAttribute="messageBox" action="message-box/edit.do">
		<form:hidden path="id" />
		<form:hidden path="version" />
		
		<form:label path="name">
			<strong><spring:message code="messageBox.name"/>:</strong>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name" />
		<br/><br/>
		
		<form:hidden path="actor"/>
		<form:hidden path="messages"/>
		
		<input type="submit" name="save" value="<spring:message code="options.save" />" />
		<input type="submit" name="delete" value="<spring:message code="options.delete" />" />
		<input type="button" name="cancel" value="<spring:message code="options.cancel" />"
				onclick="javascript:relativeRedir('message-box/list.do');" />
	</form:form>
</jstl:if>

<jstl:if test="${!(name != 'InBox' && name != 'OutBox' && name != 'TrashBox' && name != 'SpamBox')}">
	<h2><spring:message code="messageBox.name"/>: <em><jstl:out value="${name}"></jstl:out></em></h2>
</jstl:if>
<br/>

<h2><spring:message code="messageBox.messages" />:</h2>
<display:table name="messageBox.messages" id="message" pagesize="10" class="displaytag">
	<display:column titleKey="message.subject">
		<jstl:out value="${message.subject}" />
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
	<display:column titleKey="options.options">
		<a href="message/show.do?id=<jstl:out value="${message.id}" />">
			<spring:message code="options.open" />
		</a>
	</display:column>
</display:table>