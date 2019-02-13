<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="tutorials" id="tutorial">
	<display:column titleKey="tutorial.title">
		<div>
			<a href="tutorial/display.do?tutorialId=${tutorial.id}">
				<spring:message code="tutorial.title" />
			</a>
		</div>
	</display:column>
	<display:column property="lastUpdated" titleKey="tutorial.lastUpdated" />
	<display:column property="summary" titleKey="tutorial.summary" />
	<display:column titleKey="tutorial.handyWorker">
		<div>
			<a href="handy-worker/show.do?handyWorkerId=${tutorial.handyWorker.id}">
				<spring:message code="tutorial.handyWorker" />
			</a>
		</div>
	</display:column>
</display:table>
