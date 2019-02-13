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
	<spring:message code="complaint.edit" />
</p>

<form:form modelAttribute="complaint" action="complaint/edit.do">
    <form:hidden path="id"/>
    <form:hidden path="version" />
	
    <security:authorize access="hasRole('customer')" >

    <form:label path="description" placeholder= "<spring:message code='complaint.description'/>"/>
        <form:input path="${complaint.description}"/>
    

            <input type="submit" name="editComplaint" value="<spring:message code='complaint.save'/>" />

    </security:authorize>

</form:form>