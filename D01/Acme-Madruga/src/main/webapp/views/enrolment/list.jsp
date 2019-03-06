<%--
 * list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="enrolment" requestURI="enrolment/member/list.do"
	pagesize="5" id="row">

	<display:column property="brotherhood.title" titleKey="title" />
	<display:column property="moment" titleKey="Moment" />
	<display:column titleKey="enrolment.options">
	<jstl:choose>
			<jstl:when test="${row.exitMoment eq null}">
				<form action="enrolment/member/leave.do" method="POST">
					<input type="hidden" name="id"
						value="<jstl:out value='${row.id}' />" /> <input type="submit"
						name="leave" value="<spring:message code='enrolment.leave' />" />
				</form>
			</jstl:when>
			
			<jstl:otherwise >
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

</display:table>
