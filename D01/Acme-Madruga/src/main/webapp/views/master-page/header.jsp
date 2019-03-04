<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="images/logo.png" alt="Acme Madruga Co., Inc." /></a>
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
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message code="master.page.profile.action.3" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/systemconfiguration.do"><spring:message code="master.page.administrator.systemconfiguration" /></a></li>
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
					<li><a href="enrolment/member-brotherhood/list.do"><spring:message code="master.page.member.enrolment.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood.processions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="procession/create.do"><spring:message code="master.page.brotherhood.procession.create" /></a></li>
					<li><a href="procession/list.do"><spring:message code="master.page.brotherhood.procession.list" /></a></li>
					<li><a href="acmefloat/brotherhood/list.do"><spring:message code="master.page.brotherhood.acmefloat.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
	
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
