<%--
 * action-1.jsp
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
	<spring:message code="complaint.list" />
</p>

<display:table name="complaints" id="row"
	requestURI="complaint/customer/list.do" pagesize="5"
	class="displaytag">
	
	<display:column property="ticker" titleKey="complaint.ticker" />
	<display:column property="moment" titleKey="complaint.moment" />
	<display:column property="description" titleKey="complaint.description" />
	<display:column> <a href="complaint/customer/show.do?complaintId=${row.id}"> <spring:message code="complaint.show" /></a> </display:column>
	
</display:table>

<a href="complaint/customer/create.do"><spring:message code="complaint.new" /></a>