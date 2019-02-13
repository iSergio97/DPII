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

<p>
	<strong><spring:message code="application.moment" />:</strong>
		<jstl:out value="${application.moment}" />
</p>
<p>
	<strong><spring:message code="application.status" />:</strong>
	<spring:message code="application.status.pending" />
</p>
<p>
	<strong><spring:message code="application.offeredprice" />:</strong>
	<jstl:out value="${application.offeredPrice}" />
			<spring:message code="system.euro"/> +
			<spring:message code="system.vat"/>
			<jstl:out value="(${application.offeredPrice*(1+vat)}"/>
			<spring:message code="system.euro.closedBracket"/>
</p>
<p>
	<strong><spring:message code="application.customer" />:</strong>
	<jstl:out value="${application.fixUpTask.customer.name}" />
</p>
<p>
	<strong><spring:message code="application.handyworker" />:</strong>
	<jstl:out value="${application.handyWorker.name}" />
</p>
<p>
	<strong><spring:message code="application.fixuptask" />:</strong>
	<jstl:out value="${application.fixUpTask.description}" />
</p>
<p>
	<strong><spring:message code="application.comments" />:</strong>
	<jstl:forEach items="${application.comments}" var="comment">
		<br/><jstl:out value="${comment}" />
	</jstl:forEach>
</p>

<h2><spring:message code="application.addcomment" />:</h2>
<form action="application/display.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${application.id}' />" />
	<input type="text" name="text" />
	<input type="submit" name="addcomment" value="<spring:message code='application.save' />" />
</form>
