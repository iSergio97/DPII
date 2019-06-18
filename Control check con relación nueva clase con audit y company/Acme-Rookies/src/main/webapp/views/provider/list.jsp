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

<display:table name="providers" id="row" pagesize="5" class="displaytag">

<display:column titleKey = "make">
<jstl:out value="${row.make}" />
	</display:column>
	
<display:column titleKey="show">
		<a href="provider/public/show.do?providerId=${row.id}"><spring:message code="show" /></a>
	</display:column>
	
	<display:column titleKey="items">
		<a href="item/public/listP.do?providerId=${row.id}"><spring:message code="items" /></a>
	</display:column>



</display:table>