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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<img src="${serie.banner}" />
	<acme:field value="${serie.title}" code="series.title" />
	<acme:field value="${serie.description}" code="series.description" />
	<acme:field value="${serie.status}" code="series.status" />
	<acme:field value="${serie.startDate}" code="series.startDate" />
	<jstl:if test="${serie.status == 'FINALIZED'}">
		<acme:field value="${serie.endDate}" code="series.endDate" />
	</jstl:if>
	<acme:field value="${serie.director}" code="series.director" />
	<acme:field value="${serie.cast}" code="series.cast" />
	<acme:field value="${serie.title}" code="series.title" />
	<security:authorize access="hasRole('PUBLISHER')">
		<acme:actionlink value="season/publisher/list.do?serieId=${serie.id}" code="series.seasons"/>
		<acme:actionlink value="application/publisher/create.do?serieId=${row.id}" code="action.series.apply" />
	</security:authorize>
	<security:authorize access="!hasRole('PUBLISHER')">
		<acme:actionlink value="season/public/list.do?serieId=${serie.id}" code="series.seasons"/>
	</security:authorize>
</div>

<security:authorize access="hasRole('CRITIC')">
	<div>
		<acme:actionlink value="critique/critic/create.do?serieId=${serie.id}" code="action.series.critique"/>
	</div>
</security:authorize>

<security:authorize access="hasRole('USER')">
	<div>
		<acme:actionlink value="comment/user/create.do?serieId=${serie.id}" code="action.series.comment"/>
	</div>
	<div>
		<jstl:if test="${seriesFavoritedByPrincipal[serie] ne true}">
			<form action="serie/user/markAsFavorite.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsFavorite" value="<spring:message code='markAsFavorite' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesPendingByPrincipal[serie] ne true}">
			<form action="serie/user/markAsPending.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsPending" value="<spring:message code='markAsPending' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesWatchingByPrincipal[serie] ne true}">
			<form action="serie/user/markAsWatching.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsWatching" value="<spring:message code='markAsWatching' />" />
			</form>
		</jstl:if>
		<jstl:if test="${seriesWatchedByPrincipal[serie] ne true}">
			<form action="serie/user/markAsWatched.do" method="POST">
				<input type="hidden" name="serieId" value="<jstl:out value='${serie.id}' />" />
				<input type="submit" name="markAsWatched" value="<spring:message code='markAsWatched' />" />
			</form>
		</jstl:if>
	</div>
</security:authorize>

<display:table name="${critiques}" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag"
		sort="list" defaultsort="1">
	<acme:sortablecolumndescending value="${row.moment}" code="critique.moment" />
	<acme:sortablecolumn value="${row.score}" code="critique.score" />
	<acme:actioncolumn url="critique/critic/show.do?critiqueId" value="${row.id}" code="action.show"/>
</display:table>

<display:table name="${comments}" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag"
		sort="list" defaultsort="1">
	<acme:sortablecolumndescending value="${row.moment}" code="comment.moment" />
	<acme:sortablecolumn value="${row.text}" code="comment.text" />
	<acme:sortablecolumn value="${row.score}" code="comment.score" />
</display:table>
