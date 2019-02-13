<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div id="escImg" class="escImg">
	<div>
		<a href="#"><img src="images/logo.png"
			alt="Acme Handy Worker Co., Inc." height="70%" width="50%" /></a>
	</div>

	<div>
		<ul id="jMenu">
			<!-- Do not forget the "fNiv" class for the first level links !! -->
			<security:authorize access="hasRole('ADMIN')">
				<li><a class="fNiv"><spring:message
							code="master.page.administrator" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="administrator/systemconfiguration.do"><spring:message
									code="master.page.administrator.systemconfiguration" /></a></li>
						<li><a href="administrator/dashboard.do"><spring:message
									code="master.page.administrator.dashboard" /></a></li>
						<li><a href="administrator/registeradmin.do"><spring:message
									code="master.page.administrator.register.administrator" /></a></li>
						<li><a href="administrator/registerreferee.do"><spring:message
									code="master.page.administrator.register.referee" /></a></li>
						<li><a href="administrator/suspicious.do"><spring:message
									code="master.page.administrator.suspicious" /></a></li>
						<li><a href="administrator/broadcast.do"><spring:message
									code="master.page.administrator.broadcast" /></a></li>
						<li><a href="warranty/administrator/list.do"><spring:message
									code="master.page.administrator.warranties" /></a></li>
						<li><a href="fixUpTaskCategory/administrator/list.do"><spring:message
									code="master.page.administrator.categories" /></a></li>
					</ul></li>
			</security:authorize>



			<security:authorize access="hasRole('CUSTOMER')">
				<li><a class="fNiv"><spring:message
							code="master.page.customer" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="application/customer.do"><spring:message
									code="master.page.customer.application" /></a></li>
						<li><a href="fixUpTask/customer/list.do"><spring:message
									code="master.page.fixUpTask.customer" /></a></li>
						<li><a href="complaint/customer/list.do"><spring:message
									code="master.page.customer.complaint" /></a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="hasRole('HANDY_WORKER')">
				<li><a class="fNiv"><spring:message
							code="master.page.handyWorker" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="application/handyworker.do"><spring:message
									code="master.page.handyWorker.application" /></a></li>
						<li><a href="fixUpTask/handyWorker/list.do"><spring:message
									code="master.page.fixUpTask.handyWorker" /></a></li>
						<li><a href="tutorial/listhandyworker.do"><spring:message
									code="master.page.tutorial.handyWorker" /></a></li>
						<li><a href="finder/handyWorker/edit.do"><spring:message
									code="master.page.finder.handyWorker" /></a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li><a class="fNiv" href="security/login.do"><spring:message
							code="master.page.login" /></a></li>
			</security:authorize>
			
			<security:authorize access="isAnonymous()">
				<li><a class="fNiv" href="tutorial/list.do"><spring:message
							code="master.page.tutorials" /></a></li>
			</security:authorize>

			<security:authorize access="isAnonymous()">
				<li><a class="fNiv"><spring:message
							code="master.page.register" /></a>
							<ul>
							<li class="arrow"></li>
							<li> <a href="customer/register.do"><spring:message code="master.page.register.customer"/></a></li>
							<li> <a href="handy-worker/register.do"><spring:message code="master.page.register.handyWorker"/></a></li>
							<li> <a href="sponsor/register.do"><spring:message code="master.page.register.sponsor"/></a></li>
							
							</ul>
							</li>
			</security:authorize>

			<security:authorize access="isAuthenticated()">
				<li><a class="fNiv"> <spring:message
							code="master.page.profile" /> (<security:authentication
							property="principal.username" />)
				</a>
					<ul>
						<li class="arrow"></li>
						<li><a href="profile/show.do"><spring:message
									code="master.page.profile.show" /></a></li>
						<li><a
							href="profile/edit.do?id=<security:authentication property="principal.id" />"><spring:message
									code="master.page.profile.edit" /></a></li>
						<li><a href="j_spring_security_logout"><spring:message
									code="master.page.logout" /> </a></li>
					</ul></li>
			</security:authorize>

			<security:authorize access="isAuthenticated()">
				<li><a class="fNiv"> <spring:message
							code="master.page.messages" />
				</a>
					<ul>
						<li class="arrow"></li>
						<li><a href="message/sendMessage.do"><spring:message
									code="master.page.messages.send" /></a></li>
						<li><a href="message-box/show.do?name=InBox"><spring:message
									code="master.page.messagebox.inbox" /></a></li>
						<li><a href="message-box/show.do?name=OutBox"><spring:message
									code="master.page.messagebox.outbox" /></a></li>
						<li><a href="message-box/show.do?name=TrashBox"><spring:message
									code="master.page.messagebox.trashbox" /></a></li>
						<li><a href="message-box/show.do?name=SpamBox"><spring:message
									code="master.page.messagebox.spambox" /></a></li>
						<li><a href="message-box/list.do"><spring:message
									code="master.page.messagebox.list" /></a></li>
					</ul></li>
			</security:authorize>
		</ul>
	</div>

	<div>
		<a href="?language=en">en</a> | <a href="?language=es">es</a>
	</div>

</div>