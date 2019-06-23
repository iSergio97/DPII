<%--
 * xxxxx/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
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

<display:table name="xxxxx" id="row" pagesize="5" class="displaytag">

	<acme:column value="${row.ticker}" code="series.ticker"/>
	<acme:column value="${row.description}" code="series.description"/>
	<acme:column value="${row.moment}" code="series.moment"/>
	<acme:column value="${row.isDraft}" code="series.isDraft"/>
	<acme:actioncolumn url="xxxxx/company/show.do?xxxxxId" value="${row.id}" code="show" />

	<jstl:if test="${logged }">
	<acme:actioncolumn url="xxxxx/company/show.do?xxxxxId" value="${row.id}" code="show" />
	<security:authorize access="hasRole('XXXXX')">
		<jstl:if test="${row.isDraft}">
			<acme:actioncolumn url="xxxxx/company/edit.do?xxxxxId" value="${row.id}" code="edit" />
		</jstl:if>		
	</security:authorize>
	<security:authorize access="hasRole('XXXXX')">
	<p>
		<a href="xxxxx/company/create.do"><spring:message code="create" /></a>
	</p>
	</security:authorize>
	</jstl:if>
	
</display:table>
