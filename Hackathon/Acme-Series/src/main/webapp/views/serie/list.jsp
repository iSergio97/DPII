<%--
 * item/list.jsp
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

<display:table name="series" id="row" pagesize="5" class="displaytag">

<display:column titleKey = "title">
<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey = "description">
<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey = "status">
<jstl:out value="${row.status}" />
	</display:column>
	
	<display:column titleKey="show">
		<a href="serie/publisher/show.do?serieId=${row.id}"><spring:message code="show" /></a>
	</display:column>
	
	
	<jstl:if test="${row.isDraft}">
	<display:column titleKey="edit">
		<a href="serie/publisher/edit.do?serieId=${row.id}"><spring:message code="edit"/></a>
	</display:column>
	</jstl:if>

</display:table>


<p>
	<a href="serie/publisher/create.do"><spring:message code="create" /></a>
</p>
