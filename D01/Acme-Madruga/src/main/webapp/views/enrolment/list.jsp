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


<display:table name="enrolments"
	requestURI="enrolment/member/list.do" pagesize="5" >

	<display:column  title="brotherhood.title" >
	<jstl:out value="${brotherhood.title}"/>
	</display:column>
	<display:column title="enrolement.moment">
	<jstl:out value="${dates}"/>
	</display:column>

</display:table>