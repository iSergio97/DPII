<%--
 * legalRecord/show.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="title" />
	<br>
	<jstl:out value="${legalRecord.title}" />
</p>

<p>
	<spring:message code="description" />
	<br>
	<jstl:out value="${legalRecord.title}" />
</p>

<p>
	<spring:message code="legalName" />
	<br>
	<jstl:out value="${legalRecord.legalName}" />
</p>

<p>
	<spring:message code="VAT" />
	<br>
	<jstl:out value="${legalRecord.VAT}" />
</p>

<p>
	<spring:message code="applicableLaws" />
	<br>
	<jstl:out value="${legalRecord.applicableLaws}"/>
</p>

<input type="button" name="edit" value="<spring:message code='edit' />" onclick="javascript: relativeRedir('legalRecord/edit.do?id=${legalRecord.id}');" />
