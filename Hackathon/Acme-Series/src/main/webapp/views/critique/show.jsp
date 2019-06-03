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
	<acme:field value="${critique.moment}" code="critique.moment" />
	<acme:field value="${critique.text}" code="critique.text" />
	<acme:field value="${critique.score}" code="critique.score" />
	<acme:field value="${critique.serie.title}" code="critique.series" />
	<acme:field value="${critique.critic.userAccount.username}" code="critique.critic" />
</div>
