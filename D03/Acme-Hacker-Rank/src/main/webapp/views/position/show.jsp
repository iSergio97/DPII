<%--
 * position/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<strong><spring:message code="title"/></strong>: <jstl:out value="${position.title }"/>
<br>
<strong><spring:message code="description"/></strong>: <jstl:out value="${position.description }"/>
<br>
<strong><spring:message code="deadline"/></strong>: <jstl:out value="${position.deadline }"/>
<br>
<strong><spring:message code="profile"/></strong>: <jstl:out value="${position.profile }"/>
<br>
<strong><spring:message code="skills"/></strong>: <jstl:out value="${position.skills }"/>
<br>
<strong><spring:message code="technologies"/></strong>: <jstl:out value="${position.technologies}"/>
<br>
<strong><spring:message code="salary"/></strong>: <jstl:out value="${position.salary }"/>
<br>
<strong><spring:message code="problems"/></strong>: 
<jstl:forEach var="i" items="${problems}">
	<jstl:out value="${i.title}"/>
	<br>
</jstl:forEach>

<jstl:out value="Sacar un link por cada problem para una lista más cómoda de información"/>