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
			<li><a class="fNiv" href="register/brotherhood/create.do"><spring:message code="master.page.regBrotherhood" /></a></li>
			<li><a class="fNiv" href="register/member/create.do"><spring:message code="master.page.regMember" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('MEMBER')">
					<li><a href="profile/member/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/member/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('BROTHERHOOD')">
					<li><a href="profile/brotherhood/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/brotherhood/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="profile/admin/show.do"><spring:message code="master.page.profile.showProfile" /></a></li>
					<li><a href="profile/admin/edit.do"><spring:message code="master.page.profile.editProfile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/systemconfiguration.do"><spring:message code="master.page.administrator.systemconfiguration" /></a></li>
					<li><a href="register/administrator/create.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="administrator/viewareas.do"><spring:message code="master.page.administrator.viewareas" /></a></li>
					<li><a href="administrator/viewpositions.do"><spring:message code="master.page.administrator.viewpositions" /></a></li>
					<li><a href="administrator/viewpriorities.do"><spring:message code="master.page.administrator.viewpriorities" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/member/create.do"><spring:message code="master.page.member.enrolment.create" /></a></li>
					<li><a href="enrolment/member/list.do"><spring:message code="master.page.member.enrolment.list" /></a></li>
					<li><a href="request/member/list.do"><spring:message code="master.page.member.request.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.floats" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="float/brotherhood/list.do"><spring:message code="master.page.brotherhood.float.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.parades" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="parade/brotherhood/create.do"><spring:message code="master.page.action.create" /></a></li>
					<li><a href="parade/brotherhood/list.do"><spring:message code="master.page.action.list" /></a></li>
					<li><a href="request/brotherhood/list.do"><spring:message code="master.page.brotherhood.request.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
	
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.enrolment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/brotherhood/list.do"><spring:message code="master.page.brotherhood.enrolment.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood.history" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="history/show.do"><spring:message code="master.page.brotherhood.history.show" /></a></li>
					<li><a href="periodRecord/list.do"><spring:message code="master.page.brotherhood.periodRecord" /></a></li>
					<li><a href="legalRecord/list.do"><spring:message code="master.page.brotherhood.legalRecord" /></a></li>
					<li><a href="linkRecord/list.do"><spring:message code="master.page.brotherhood.linkRecord" /></a></li>
					<li><a href="miscellaneousRecord/list.do"><spring:message code="master.page.brotherhood.miscellaneousRecord" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
			<li><a href="brotherhood/public/list.do"><spring:message code="master.page.brotherhood.list" /></a></li>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
