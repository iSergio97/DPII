<%--
 * personal-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="curriculums" id="row">
	
	<display:column titleKey="curriculum.name" >
		<jstl:out value="${row.name}" />
	</display:column>
	
	<display:column>
		<a href="curriculum/hacker/show.do?curriculumId=${row.id}"> <spring:message code="action.show"/></a>
	</display:column>

</display:table>