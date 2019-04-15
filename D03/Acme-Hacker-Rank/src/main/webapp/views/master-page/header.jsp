<%--
 * master-page/header.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<jstl:choose>
		<jstl:when test="${systemConfiguration != null}" >
			<jstl:choose>
				<jstl:when test="${empty systemConfiguration.banner}" >
					<a href="#"><img src="<jstl:out value="${systemConfiguration.banner}" />" alt="Acme Parade Co., Inc." /></a>
				</jstl:when>
				<jstl:otherwise>
					<a href="#"><img src="images/logo.png" alt="Acme Madruga Co., Inc." /></a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:when>
		<jstl:otherwise>
			<a href="#"><img src="images/logo.png" alt="Acme Madruga Co., Inc." /></a>
		</jstl:otherwise>
	</jstl:choose>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="register/company/create.do"><spring:message code="master.page.register.company" /></a></li>
			<li><a class="fNiv" href="register/hacker/create.do"><spring:message code="master.page.register.hacker" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMINISTRATOR')">
					<li><a href="profile/admin/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/admin/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
					<li><a href="profile/company/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/company/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('HACKER')">
					<li><a href="profile/hacker/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/hacker/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="socialprofile/actor/list.do"><spring:message code="master.page.socialProfile" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/systemconfiguration.do"><spring:message code="master.page.administrator.systemconfiguration" /></a></li>
					<li><a href="register/administrator/create.do"><spring:message code="master.page.administrator.register" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message	code="master.page.hacker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/hacker/list.do"><spring:message code="master.page.hacker.applications" /></a></li>
				</ul>
			</li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
