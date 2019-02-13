<%--
 * show.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<strong><spring:message code="profile.name" />:</strong>
	<jstl:out value="${actor.name}" />
	<br>

	<strong><spring:message code="profile.middleName" />:</strong>
	<jstl:out value="${actor.middleName}" />
	<br>

	<strong><spring:message code="profile.surname" />:</strong>
	<jstl:out value="${actor.surname}" />
	<br>

	<strong><spring:message code="profile.photo" />:</strong>
	<jstl:out value="${actor.photo}" />
	<br>

	<strong><spring:message code="profile.email" />:</strong>
	<jstl:out value="${actor.email}" />
	<br>

	<strong><spring:message code="profile.phoneNumber" />:</strong>
	<jstl:out value="${actor.phoneNumber}" />
	<br>

	<strong><spring:message code="profile.address" />:</strong>
	<jstl:out value="${actor.address}" />
	<br>
	
	<strong><spring:message code="profile.isBanned" />:</strong>
	<jstl:if test="${actor.isBanned == false }">
		<spring:message code='profile.isBannedTestNo' />
	</jstl:if>


	<jstl:if test="${actor.isBanned == true}">
		<spring:message code='profile.isBannedTestYes' />
	</jstl:if>
	<br>
	<security:authorize access="hasRole('CUSTOMER')">
		<h3><spring:message code='profile.creditCard' />:</h3>
		<strong><spring:message code='profile.creditCard.holder' />:</strong>
				<jstl:out value="${actor.creditCard.holder}"/>
		<br/>
		<strong><spring:message code='profile.creditCard.brand' />:</strong>
				<jstl:out value="${actor.creditCard.brand}"/>
		<br/>
		<strong><spring:message code='profile.creditCard.number' />:</strong>
				<jstl:out value="${actor.creditCard.number}"/>
		<br/>
		<strong><spring:message code='profile.creditCard.expirationMonth' />:</strong>
				<jstl:out value="${actor.creditCard.expirationMonth}"/>
		<br/>
		<strong><spring:message code='profile.creditCard.expirationYear' />:</strong>
				<jstl:out value="${actor.creditCard.expirationYear}"/>
		<br/>
		<strong><spring:message code='profile.creditCard.CVV' />:</strong>
				<jstl:out value="${actor.creditCard.CVV}"/>
		<br/>
	</security:authorize>
<display:table name="socialProfiles" id="socialProfile" pagesize="5" requestURI="social-profile/edit.do" class="displaytag">
	<display:column titleKey="socialProfile.nickName">
		<jstl:out value="${socialProfile.nickName}" />
	</display:column>
	<display:column titleKey="socialProfile.socialNetworkName">
		<jstl:out value="${socialProfile.socialNetworkName}" />
	</display:column>
	<display:column titleKey="socialProfile.link">
		<a href="${link}">
			<jstl:out value="${socialProfile.link}"></jstl:out>
		</a>
		<jstl:out value="" />
	</display:column>

	<display:column titleKey="options.blank">
		<a href="social-profile/edit.do?id=<jstl:out value="${socialProfile.id}" />">
			<spring:message code="options.edit"/>
		</a>
	</display:column>
</display:table>

<a href="social-profile/create.do">
	<spring:message code="options.create"/>
</a>
</div>
