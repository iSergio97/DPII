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


<form:form modelAttribute="miscellaneousData" action="miscellaneous-data/hacker/edit.do">

<acme:register code="freeText" path="miscellaneousData.freeText"/>
<acme:register code="attachments" path="miscellaneousData.attachments"/>

<acme:submit name="save" code="save"/>
<acme:cancel url="welcome/index.do" code="cancel"/>
</form:form>