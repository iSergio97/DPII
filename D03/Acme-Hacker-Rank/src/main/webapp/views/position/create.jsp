<%--
 * position/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="position" action="position/company/edit.do">

	<form:hidden path="id" />

	<acme:register code="title" path="title" />
	<acme:register code="description" path="description" />
	<acme:register code="profile" path="profile" />
	<acme:register code="skills" path="skills" />
	<acme:register code="salary" path="salary" />
	<acme:register code="technologies" path="technologies" />
	<acme:register code="deadline" path="deadline" />
	<jstl:out value="Problems"/>
	<form:select path="problems">
		<form:options items="${problems}" itemLabel="title" itemValue="id"/>
	</form:select>
	<br>
	<br>

	<acme:submit name="save" code="save" />
	<acme:cancel url="welcome/index.do" code="cancel" />

</form:form>