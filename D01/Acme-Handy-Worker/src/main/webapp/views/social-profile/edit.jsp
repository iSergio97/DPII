<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Nozotro
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="socialProfile" action="social-profile/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="actor" />
		
	<form:label path="nickName">
		<strong><spring:message code="socialProfile.nickName"/>:</strong>
	</form:label>
	<form:input path="nickName"/>
	<form:errors cssClass="error" path="nickName" />
	<br/>
	
	<form:label path="socialNetworkName">
		<strong><spring:message code="socialProfile.socialNetworkName"/>:</strong>
	</form:label>
	<form:input path="socialNetworkName"/>
	<form:errors cssClass="error" path="socialNetworkName" />
	<br/>
	
	<form:label path="link">
		<strong><spring:message code="socialProfile.link"/>:</strong>
	</form:label>
	<form:input path="link"/>
	<form:errors cssClass="error" path="link" />
	<br/>
		
	<input type="submit" name="save" value="<spring:message code="options.save" />" />
	<input type="submit" name="delete" value="<spring:message code="options.delete" />" />
	<input type="button" name="cancel" value="<spring:message code="options.cancel" />"
			onclick="javascript:relativeRedir('profile/show.do');" />
</form:form>
