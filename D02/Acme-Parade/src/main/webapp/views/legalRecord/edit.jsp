<%--
 * legalRecord/edit.jsp
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

<form:form modelAttribute="legalRecord" method="POST" action="legalRecord/edit.do">

	<!-- Hidden fields -->

	<form:hidden path="id" />
	
	<!-- Input fields -->

	<div id="title" class="title">
		<form:label path="title">
			<spring:message code="title" />
		</form:label>
		<form:input path="title" />
		<form:errors path="title" />
	</div>
	
	
	<div id="description" class="description">
		<form:label path="description">
			<spring:message code="description" />
		</form:label>
		<form:input path="description" />
		<form:errors path="description" />
	</div>
	
	<div id="legalName" class="legalName">
		<form:label path="legalName">
			<spring:message code="legalName" />
		</form:label>
		<form:input path="legalName" />
		<form:errors path="legalName" />
	</div>
	
	<div id="VAT" class="VAT">
		<form:label path="VAT">
			<spring:message code="VAT" />
		</form:label>
		<form:input path="VAT" />
		<form:errors path="VAT" />
	</div>
	
	<div id="applicableLaws" class="applicableLaws">
		<form:label path="applicableLaws">
			<spring:message code="applicableLaws" />
		</form:label>
		<form:input path="applicableLaws" />
		<form:errors path="applicableLaws" />
	</div>
	
	<!-- Form options -->

	<input type="submit" name="save" value="<spring:message code="send" />" />
	<input type="button" name="cancel" value="<spring:message code="cancel" />" onclick="javascript: relativeRedir('welcome/index.do');" />	
	
</form:form>
