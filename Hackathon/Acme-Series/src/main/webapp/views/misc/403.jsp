<%--
 * misc/403.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>Oops! You don't have access to this resource. You must be a...</p>

<img width="500px" alt="Hackerman" src="https://3.bp.blogspot.com/-YpVmLl2neak/WjgV8XnJyQI/AAAAAAAACqE/18pcPPwaCOgreBNq6au7BZVqs5c_cu47QCLcBGAs/s1600/GVJ06OK.jpg">

<p><a href="<spring:url value='/' />">Return to the welcome page</a><p>
