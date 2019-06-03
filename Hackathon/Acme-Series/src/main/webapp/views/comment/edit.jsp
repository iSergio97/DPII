<%--
comment/create.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="comment/user/edit.do" modelAttribute="comment">

	<!-- Hidden fields -->
	
	<form:hidden path="id" />
	<input type="hidden" id="serieId" name="serieId" value="${serieId}">
	
	<!-- Input fields -->
	
	<acme:textarea path="text" code="comment.text" />
	<acme:register  path="score" code="comment.score" />
	
	<!-- Buttons -->

	<acme:submit name="save" code="action.save"/>
	<jstl:if test="${comment.id ne 0}">
		<acme:submit name="delete" code="action.delete"/>
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="action.cancel"/>

</form:form>
