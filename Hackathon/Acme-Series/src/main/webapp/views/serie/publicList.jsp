<%--
 * item/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form action="serie/public/list.do" method="GET">
	<spring:message code="keyword" />
	: <input type="text" value="" id="keyword" name="keyword" />
	<button type="submit">
		<spring:message code="search" />
	</button>
</form>

<display:table name="series" id="row" pagesize="5" class="displaytag">

	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>

	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>

	<display:column titleKey="status">
		<jstl:out value="${row.status}" />
	</display:column>

	<display:column titleKey="options">
		<a href="serie/public/show.do?serieId=${row.id}"><spring:message code="show" /></a>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
		<a href="serie/administrator/edit.do?serieId=${row.id}"><spring:message code="edit"/></a>
		</security:authorize>
		
		<security:authorize access="hasRole('CRITIC')">
			<a href="critique/critic/create.do?serieId=${row.id}"><spring:message code="makeCritique"/></a>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<jstl:if test="${seriesFavoritedByPrincipal[row] ne true}">
				<form action="serie/user/markAsFavorite.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsFavorite" value="<spring:message code='markAsFavorite' />" />
				</form>
			</jstl:if>
			<jstl:if test="${seriesPendingByPrincipal[row] ne true}">
				<form action="serie/user/markAsPending.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsPending" value="<spring:message code='markAsPending' />" />
				</form>
			</jstl:if>
			<jstl:if test="${seriesWatchingByPrincipal[row] ne true}">
				<form action="serie/user/markAsWatching.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsWatching" value="<spring:message code='markAsWatching' />" />
				</form>
			</jstl:if>
			<jstl:if test="${seriesWatchedByPrincipal[row] ne true}">
				<form action="serie/user/markAsWatched.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsWatched" value="<spring:message code='markAsWatched' />" />
				</form>
			</jstl:if>
			<a href="comment/user/create.do?serieId=${row.id}"><spring:message code="makeComment"/></a>
		</security:authorize>
	</display:column>
	


</display:table>

