<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="message" action="message/all/edit.do">

	<acme:register code="subject" path="subject"/>
	<br>

	<form:label path="priority">
		<strong><spring:message code='priority' />:</strong>
	</form:label>
	<form:select path="priority">
		<form:option value="NEUTRAL" >  <spring:message code='neutral' /></form:option>
		<form:option value="LOW" > <spring:message code='low' /> </form:option>
		<form:option value="HIGH" > <spring:message code='high' /></form:option>
	</form:select>
	<br>
	
	<acme:register code="tags" path="tags"/>
	<br>
	
	<acme:textarea code="body" path="body"/>
	<br>
	
	<form:label path="recipients">
		<strong><spring:message code='recipients' />:</strong>
	</form:label>
	<form:select path="recipients" multiple="true">
		<jstl:forEach items="${recipients}" var="recipient">
			<form:option value="${recipient.id}">
					<jstl:out value="${recipient.userAccount.username}" />
					<jstl:out value="--- (" />
					<jstl:out value="${recipient.name}" />
				<jstl:forEach items="${recipient.surnames}" var="surname">
					<jstl:out value="${surname}" />
				</jstl:forEach>
					<jstl:out value=")" />
			</form:option>
		</jstl:forEach>
	</form:select>
	
	<acme:submit name="save" code="message.action.send"/>
	<acme:cancel url="welcome/index.do" code="action.cancel" />
</form:form>