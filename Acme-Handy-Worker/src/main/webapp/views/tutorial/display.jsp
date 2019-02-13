<%--
 *
 * Copyright (C) 2018 Nozotro
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

<a href="${sponsorship.targetPage}">
	<img alt="banner" src="${sponsorship.banner}">
</a>
<p>
	<strong><spring:message code="tutorial.title" />:</strong>
		<jstl:out value="${tutorial.title}" />
</p>
<p>
	<strong><spring:message code="tutorial.lastUpdated" />:</strong>
		<jstl:out value="${tutorial.lastUpdated}" />
</p>
<p>
	<strong><spring:message code="tutorial.summary" />:</strong>
		<jstl:out value="${tutorial.summary}" />
</p>
<h3><spring:message code="tutorial.pictures" />:</h3>
		<jstl:forEach items="${tutorial.pictures}" var="picture">
			<br>
			<img alt="picture" src="${picture}">
		</jstl:forEach>

<display:table name="tutorial.sections" id="section">
	<display:column property="title" titleKey="section.title" />
	<display:column property="text" titleKey="section.text" />
	<display:column titleKey="section.pictures">
		<div>
			<jstl:forEach items="${section.pictures}" var="picture">
				<br>
				<img alt="picture" src="${picture}">
			</jstl:forEach>
		</div>
	</display:column>
	<jstl:if test = "${tutorial.handyWorker.id == principalId}">
		<display:column titleKey="section.options">
			<div>
				<a href="section/edit.do?sectionId=${section.id}">
					<spring:message code="section.edit" />
				</a>
				<form action="section/delete.do" method="POST">
					<input type="hidden" name="sectionid" value="<jstl:out value='${section.id}' />" />
					<input type="submit" name="delete" value="<spring:message code='section.delete' />" />
				</form>
			</div>
		</display:column>
	</jstl:if>
</display:table>