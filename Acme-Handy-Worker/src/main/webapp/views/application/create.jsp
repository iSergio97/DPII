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
	<spring:message code="application.create" />
</p>

<form method="POST">
	<input type="hidden" name="fixuptaskid" value="<jstl:out value="${fixUpTaskId}" />" />
	<p>
		<spring:message code="application.offeredprice" />
		<br><input type="number" name="offeredprice">
		<spring:message code="application.addcomment" />
		<br><input type="text" name="comment">
	</p>
	<input type="submit" name="save" value="<spring:message code='application.save' />" />
</form>
