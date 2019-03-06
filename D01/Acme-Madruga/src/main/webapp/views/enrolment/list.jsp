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


<display:table name="brotherhood" requestURI="enrolment/member/list.do"
	pagesize="5">

	<display:column property="title"
		titleKey="brotherhood.title"  />
	<display:column titleKey="enrolment.info">
		<a href="/enrolment/member/info.do"> <spring:message
				code="enrolment.info?=${brotherhood.enrolment.id}" /></a>
	</display:column>

</display:table>