<%--
 * master-page/header.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<jstl:choose>
		<jstl:when test="${systemConfiguration != null}">
			<jstl:choose>
				<jstl:when test="${empty systemConfiguration.banner}">
					<a href="#"><img
						src="<jstl:out value="${systemConfiguration.banner}" />"
						alt="Acme Parade Co., Inc." /></a>
				</jstl:when>
				<jstl:otherwise>
					<a href="#"><img src="images/logo.png"
						alt="Acme Rookies Co., Inc." /></a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:when>
		<jstl:otherwise>
			<a href="#"><img src="images/logo.png"
				alt="Acme Rookies Co., Inc." /></a>
		</jstl:otherwise>
	</jstl:choose>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="register/rookie/create.do"><spring:message
						code="master.page.register.rookie" /></a></li>
			<li><a class="fNiv" href="register/company/create.do"><spring:message
						code="master.page.register.company" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					
					<security:authorize access="hasRole('ROOKIE')">
						<li><a href="register/rookie/edit.do"><spring:message code="action.edit" /> </a></li>
					</security:authorize>

					<security:authorize access="hasRole('COMPANY')">
						<li><a href="register/rookie/edit.do"><spring:message code="action.edit" /> </a></li>
					</security:authorize>

					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="register/administrator/create.do"><spring:message code="master.page.create.admin" /> </a></li>
						<li><a href="register/administrator/edit.do"><spring:message code="action.edit" /> </a></li>
					</security:authorize>
					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv" href="social-profile/all/list.do"><spring:message code="master.page.socialProfile" /></a>
			</li>
			<li>
				<a class="fNiv"><spring:message code="master.page.messages" /></a>
				<ul>
					<li><a href="message/all/create.do"><spring:message code="master.page.message.create" /></a></li>
					<li><a href="message-box/all/show.do?name=inBox"><spring:message code="master.page.messageBox.inBox" /></a></li>
					<li><a href="message-box/all/show.do?name=outBox"><spring:message code="master.page.messageBox.outBox" /></a></li>
					<li><a href="message-box/all/show.do?name=trashBox"><spring:message code="master.page.messageBox.trashBox" /></a></li>
					<li><a href="message-box/all/show.do?name=spamBox"><spring:message code="master.page.messageBox.spamBox" /></a></li>
					<li><a href="message-box/all/show.do?name=notificationBox"><spring:message code="master.page.messageBox.notificationBox" /></a></li>
					<li><a href="message-box/all/list.do"><spring:message code="action.list.messageBox" /></a></li>
				</ul>
			</li>
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

		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/company/create.do"><spring:message code="action.create.position" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="action.list.position" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv"><spring:message	code="master.page.rookie" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curriculum/rookie/create.do"><spring:message code="action.create.curriculum" /></a></li>
					<li><a href="curriculum/rookie/list.do"><spring:message code="action.list.curriculum" /></a></li>
					<li><a href="application/rookie/list.do"><spring:message code="action.list.application" /></a></li>
				<!--<li><a href="finder/list.do"><spring:message code="action.list.finder" /></a></li> -->
				</ul>
			</li>
		</security:authorize>

		<li><a class="fNiv" href="company/all/list.do"><spring:message code="master.page.list.company" /></a></li>
		<li><a class="fNiv" href="position/all/list.do"><spring:message code="master.page.list.position" /></a></li>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
