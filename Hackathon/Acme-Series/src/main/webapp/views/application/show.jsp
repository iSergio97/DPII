<%--
application/show.jsp

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

<div>
	<acme:field value="${application.moment}" code="application.moment" />
	<acme:field value="${application.description}" code="application.description" />
	<acme:field value="${application.status}" code="application.status" />
	<acme:field value="${application.publisher.userAccount.username}" code="application.publisher" />
	<acme:field value="${application.administrator.userAccount.username}" code="application.administrator" />
</div>
