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
	<spring:message code="complaint.display" />
</p>

<p>
	<spring:message code="complaint.ticker" />
	<br>
	<jstl:out value="${complaint.ticker}" />
</p>

<p>
	<spring:message code="complaint.moment" />
	<br>
	<jstl:out value="${complaint.moment}" />
</p>

<p>
	<spring:message code="complaint.description" />
	<br>
	<jstl:out value="${complaint.description}" />
</p>

<p>
	<spring:message code="complaint.attachments" />
	<jstl:forEach items="${complaint.attachments}" var="attachment">
		<br>
		<a href="<jstl:out value='${attachment}' />">
			<jstl:out value='${attachment}' />
		</a>
	</jstl:forEach>
</p>

<display:table name="complaint.reports" id="report">
	<display:column property="date" titleKey="report.date" />
	<display:column property="description" titleKey="report.description" />
	<display:column titleKey="report.attachments">
		<div>
			<jstl:forEach items="${report.attachments}" var="attachment">
				<br>
				<a href="<jstl:out value='${attachment}' />">
					<jstl:out value='${attachment}' />
				</a>
			</jstl:forEach>
		</div>
	</display:column>
	<display:column titleKey="report.notes">
		<div>
			<jstl:forEach items="${report.notes}" var="note">
				<br>
				<a href="<jstl:out value='${note}' />">
					<jstl:out value='${note}' />
				</a>
			</jstl:forEach>
		</div>
	</display:column>
	<jstl:if test = "${complaint.report.referee.id == principalId}">
		<display:column titleKey="report.options">
			<div>
				<a href="report/edit.do?reportId=${report.id}">
					<spring:message code="report.edit" />
				</a>
				<form action="report/delete.do" method="POST">
					<input type="hidden" name="reportid" value="<jstl:out value='${report.id}' />" />
					<input type="submit" name="delete" value="<spring:message code='report.delete' />" />
				</form>
			</div>
		</display:column>
	</jstl:if>
</display:table>