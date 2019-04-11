<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="curriculums" id="row">

<%--
	Añadir propiedad a curricula para poder mostrar su nombre
 --%>
	
	<display:column titleKey="curricula.name" >
		<jstl:out value="${row.name}" />
	</display:column>
	<display:column titleKey="show">
		<a href="curricula/hacker/show.do?curriculumId=${row.id}"> <spring:message code="show"/></a>
	</display:column>

</display:table>