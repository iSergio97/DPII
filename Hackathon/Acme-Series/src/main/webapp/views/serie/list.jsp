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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form action="serie/public/list.do" method="GET">
	<spring:message code="keyword" />
	: <input type="text" value="" id="keyword" name="keyword" />
	<button type="submit">
		<spring:message code="search" />
	</button>
</form>

<display:table name="series" id="row" pagesize="5" class="displaytag">

	<acme:column value="${row.title}" code="series.title"/>
	<acme:column value="${row.description}" code="series.description"/>
	<acme:column value="${row.status}" code="series.status"/>

	<acme:actioncolumn url="serie/public/show.do?serieId" value="${row.id}" code="action.show" />
	<security:authorize access="hasRole('PUBLISHER')">
		<jstl:if test="${row.isDraft}">
			<acme:actioncolumn url="serie/publisher/edit.do?serieId" value="${row.id}" code="action.edit" />
			<acme:actioncolumn url="application/publisher/create.do?serieId" value="${row.id}" code="action.series.apply" />
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<jstl:if test="${!row.isDraft}">
			<acme:actioncolumn url="serie/administrator/edit.do?serieId" value="${row.id}" code="action.edit" />
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('CRITIC')">
		<acme:actioncolumn url="critique/critic/create.do?serieId" value="${row.id}" code="action.series.critique" />
	</security:authorize>
		
	<security:authorize access="hasRole('USER')">
		<acme:actioncolumn url="comment/user/create.do?serieId" value="${row.id}" code="action.series.comment" />
		<jstl:if test="${seriesFavoritedByPrincipal[row] ne true}">
			<display:column>
				<form action="serie/user/markAsFavorite.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsFavorite" value="<spring:message code='markAsFavorite' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesFavoritedByPrincipal[row] eq true}">
			<display:column>
				<form action="serie/user/unmarkAsFavorite.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="unmarkAsFavorite" value="<spring:message code='unmarkAsFavorite' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesPendingByPrincipal[row] ne true}">
			<display:column>
				<form action="serie/user/markAsPending.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsPending" value="<spring:message code='markAsPending' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesPendingByPrincipal[row] eq true}">
			<display:column>
				<form action="serie/user/unmarkAsPending.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="unmarkAsPending" value="<spring:message code='unmarkAsPending' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesWatchingByPrincipal[row] ne true}">
			<display:column>
				<form action="serie/user/markAsWatching.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsWatching" value="<spring:message code='markAsWatching' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesWatchingByPrincipal[row] eq true}">
			<display:column>
				<form action="serie/user/unmarkAsWatching.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="unmarkAsWatching" value="<spring:message code='unmarkAsWatching' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesWatchedByPrincipal[row] ne true}">
			<display:column>
				<form action="serie/user/markAsWatched.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="markAsWatched" value="<spring:message code='markAsWatched' />" />
				</form>
			</display:column>
		</jstl:if>
		<jstl:if test="${seriesWatchedByPrincipal[row] eq true}">
			<display:column>
				<form action="serie/user/unmarkAsWatched.do" method="POST">
					<input type="hidden" name="serieId" value="<jstl:out value='${row.id}' />" />
					<input type="submit" name="unmarkAsWatched" value="<spring:message code='unmarkAsWatched' />" />
				</form>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('PUBLISHER')">
	<p>
		<a href="serie/publisher/create.do"><spring:message code="create" /></a>
	</p>
</security:authorize>
