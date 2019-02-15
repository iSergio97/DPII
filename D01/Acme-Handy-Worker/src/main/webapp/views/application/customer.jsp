<%--
 * action-1.jsp
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
	<spring:message code="application.received" />
</p>

<display:table name="applications" id="row">
	<display:column property="moment" titleKey="application.moment" />
	<display:column titleKey="application.status">
		<jstl:choose>
			<jstl:when test = "${row.status == 'PENDING' && row.moment <= currentDate}">
				<spring:message code="application.status.pending" />
			</jstl:when>
			<jstl:when test = "${row.status == 'PENDING' && row.moment > currentDate}">
				<font color="gray"><spring:message code="application.status.pending" /></font>
			</jstl:when>
			<jstl:when test = "${row.status == 'ACCEPTED'}">
				<font color="green"><spring:message code="application.status.accepted" /></font>
			</jstl:when>
			<jstl:when test = "${row.status == 'REJECTED'}">
				<font color="orange"><spring:message code="application.status.rejected" /></font>
			</jstl:when>
			<jstl:otherwise>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	<display:column property="offeredPrice" titleKey="application.offeredprice" />
	<display:column titleKey="application.handyworker">
		<div>
			<jstl:out value="${row.handyWorker.name}" />
		</div>
	</display:column>
	<display:column titleKey="application.fixuptask">
		<div>
			<jstl:out value="${row.fixUpTask.description}" />
		</div>
	</display:column>
	<display:column titleKey="application.options">
		<div>
			<a href="application/display.do?id=${row.id}">
				<spring:message code="application.open" />
			</a>
			<jstl:if test = "${row.status == 'PENDING'}">
				<jstl:if test = "${row.fixUpTask.timeLimit < currentDate}">
					<form action="application/customer.do" method="POST">
						<input type="hidden" name="id" value="<jstl:out value="${row.id}" />">
						<input type="submit" name="accept" value='<spring:message code="application.accept" />' />
						<input type="submit" name="reject" value='<spring:message code="application.reject" />' />
					</form>
				</jstl:if>
			</jstl:if>
		</div>
	</display:column>
</display:table>
