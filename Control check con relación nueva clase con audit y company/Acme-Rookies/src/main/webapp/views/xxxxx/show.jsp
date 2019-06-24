<%--
serie/show.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<acme:field value="${xxxxx.body}" code="body" />
	<spring:message code="picture" />: <img src="${xxxxx.picture}" /> 	
</div>

<jstl:if test="${logged}">
<security:authorize access="hasRole('XXXXX')">
		<jstl:if test="${row.isDraft}">
			<a href="xxxxx/company/edit.do?${xxxxxId}" > <spring:message code="edit" /></a>
		</jstl:if>		
	</security:authorize>
</jstl:if>