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

<display:table name="broterhoods" id="row"
	requestURI="enrolment/list.do" pagesize="5" class="displaytag">

	<display:column titleKey="title">
		<jstl:out value="${brotherhood.title}" />
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column property="edit">
		<jstl:out value="Edit"/>
	</display:column>
	</security:authorize>
	
		

</display:table>