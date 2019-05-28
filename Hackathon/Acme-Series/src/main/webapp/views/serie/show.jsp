<%--
serie/show.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<img src="${serie.banner}" />

<p>
	<strong><spring:message code="serie.title" /></strong>:<jstl:out value="${serie.title}" />
</p>

<p>
	<strong><spring:message code="serie.description" /></strong>:<jstl:out value="${serie.description}" />
</p>

<p>
	<strong><spring:message code="serie.status" /></strong>:<jstl:out value="${serie.status}" />
</p>

<p>
	<strong><spring:message code="serie.startDate" /></strong>:<jstl:out value="${serie.startDate}" />
</p>

<p>
	<jstl:if test="${serie.status == 'FINALIZED'}">
		<strong><spring:message code="serie.endDate" /></strong>:<jstl:out value="${serie.endDate}" />
	</jstl:if>
</p>

<p>
	<strong><spring:message code="serie.director" /></strong>:<jstl:out value="${serie.director}" />
</p>

<p>
	<strong><spring:message code="serie.cast" /></strong>:<jstl:out value="${serie.cast}" />
</p>

<security:authorize access="hasRole('PUBLISHER')">
	<p>
		<a href="season/publisher/list.do?serieId=${serie.id}"><spring:message code="serie.seasons" /></a>
	</p>
</security:authorize>

<security:authorize access="!hasRole('PUBLISHER')">
	<p>
		<a href="season/public/list.do"><spring:message code="serie.seasons" /></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('CRITIC')">
	<p>
		<a href="critique/critic/create.do?serieId=${serie.id}"><spring:message code="serie.makeCritique"/></a>
	</p>
</security:authorize>

<security:authorize access="hasRole('USER')">
	<p>
		<a href="comment/user/create.do?serieId=${serie.id}"><spring:message code="serie.makeComment"/></a>
	</p>
	<p>
		<jstl:if test="${seriesFavoritedByPrincipal[serie] ne true}">
			<form action="serie/user/markAsFavorite.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsFavorite" value="<spring:message code='serie.markAsFavorite' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesPendingByPrincipal[serie] ne true}">
			<form action="serie/user/markAsPending.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsPending" value="<spring:message code='serie.markAsPending' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesWatchingByPrincipal[serie] ne true}">
			<form action="serie/user/markAsWatching.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsWatching" value="<spring:message code='serie.markAsWatching' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesWatchedByPrincipal[serie] ne true}">
			<form action="serie/user/markAsWatched.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsWatched" value="<spring:message code='serie.markAsWatched' />" />
			</form>
		</jstl:if>
	</p>
</security:authorize>
