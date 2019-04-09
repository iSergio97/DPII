<%--
 * personal-data/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Sergio Garrido Domínguez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form modelAttribute="personalData" action="personal-data/hacker/edit.do">

<acme:register code="curriculumName" path="curriculumName"/>
<acme:register code="fullName" path="fullName"/>
<acme:register code="ghProf" path="gitHubProfile"/>
<acme:register code="liProf" path="linkedInProfile"/>
<acme:register code="phoneNumber" path="phoneNumber"/>
<acme:register code="statement" path="statement"/>

<acme:submit name="save" code="save"/>
<acme:cancel url="welcome/index.do" code="cancel"/>
</form:form>