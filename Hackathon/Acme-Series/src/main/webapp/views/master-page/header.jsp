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
					<a href="#">
						<img src="<jstl:out value="${systemConfiguration.banner}" />" alt="Acme Series Co., Inc." />
					</a>
				</jstl:when>
				<jstl:otherwise>
					<a href="#">
						<img src="images/logo.png" alt="Acme Series Co., Inc." />
					</a>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:when>
		<jstl:otherwise>
			<a href="#">
				<img src="images/logo.png" alt="Acme Series Co., Inc." />
			</a>
		</jstl:otherwise>
	</jstl:choose>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:messagecode="master.page.login" /></a></li>
			<li><a class="fNiv" href="register/user/create.do"> <spring:message code="master.page.register.user" /></a></li>
			<li><a class="fNiv" href="register/publisher/create.do"> <spring:message code="master.page.register.publisher" /></a></li>
			<li><a class="fNiv" href="serie/public/list.do"> <spring:message code="master.page.serie.public" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMINISTRATOR')">
						<li><a href="register/administrator/edit.do"><spring:message code="action.edit" /> </a></li>
						<li><a href="register/administrator/create.do"><spring:message code="master.page.create.admin" /> </a></li>
						<li><a href="register/critic/create.do"><spring:message code="master.page.create.auditor" /> </a></li>
						<li><a href="profile/administrator/edit.do"><spring:message code="master.page.edit.profile" /> </a></li>
						<li><a href="profile/administrator/show.do"><spring:message code="master.page.show.profile" /> </a></li>
					</security:authorize>
					<security:authorize access="hasRole('USER')">
						<li><a href="profile/user/show.do"><spring:message code="master.page.show.profile" /> </a></li>
						<li><a href="profile/user/edit.do"><spring:message code="master.page.edit.profile" /> </a></li>
					</security:authorize>
					<security:authorize access="hasRole('CRITIC')">
						<li><a href="profile/critic/show.do"><spring:message code="master.page.show.profile" /> </a></li>
						<li><a href="profile/critic/edit.do"><spring:message code="master.page.edit.profile" /> </a></li>
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
					<li><a href="message/all/broadcast.do"><spring:message code="master.page.message.broadcast" /></a></li>
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
					<li><a href="administrator/actor/list.do"><spring:message code="master.page.administrator.listActor" /></a></li>
					<li><a href="register/administrator/create.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="register/critic/create.do"><spring:message code="master.page.register.critic" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/administrator/list.do"><spring:message code="action.list.application" /></a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PUBLISHER')">
			<li><a class="fNiv"><spring:message	code="master.page.application" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/publisher/list.do"><spring:message code="action.list.application" /></a></li>
					<li><a href="serie/publisher/list.do"><spring:message code="master.page.serie.publisher" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="serie/user/sortedByFavoritesList.do"><spring:message code="master.page.serie.user.sortedByFavoritesList" /> </a></li>
					<li><a href="serie/user/favouriteList.do"><spring:message code="master.page.serie.user.favouriteList" /> </a></li>
					<li><a href="serie/user/pendingList.do"><spring:message code="master.page.serie.user.pendingList" /> </a></li>
					<li><a href="serie/user/watchingList.do"><spring:message code="master.page.serie.user.watchingList" /> </a></li>
					<li><a href="serie/user/watchedList.do"><spring:message code="master.page.serie.user.watchedList" /> </a></li>
				</ul>
			</li>
		<security:authorize access="hasRole('CRITIC')">
			<li><a class="fNiv"><spring:message	code="master.page.critique" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="critique/critic/list.do"><spring:message code="action.list.critique" /></a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en"><jstl:out value="en"></a>
	<a href="?language=es"><jstl:out value="es"></a>
</div>
