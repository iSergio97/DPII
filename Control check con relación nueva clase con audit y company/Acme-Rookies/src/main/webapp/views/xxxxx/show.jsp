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
	
	<acme:field value="${xxxxx.ticker}" code="ticker" />
	<acme:field value="${xxxxx.description}" code="description" />
	<acme:field value="${serie.moment}" code="moment" />
		
</div>

<jstl:if test="${logged}">
<security:authorize access="hasRole('XXXXX')">
		<jstl:if test="${row.isDraft}">
			<acme:actioncolumn url="xxxxx/company/edit.do?xxxxxId" value="${row.id}" code="edit" />
		</jstl:if>		
	</security:authorize>
</jstl:if>