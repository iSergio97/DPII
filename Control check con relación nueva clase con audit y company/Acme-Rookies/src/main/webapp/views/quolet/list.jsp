<%--
 * quolet/list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<display:table name="quolets" id="row" requestURI="quolet/company/list.do" pagesize="2">
	<jstl:set var="localeCode" value="${pageContext.response.locale}" />
	<fmt:formatDate value="${row.publicationMoment}" pattern="dd" var="day" />
	<fmt:formatDate value="${row.publicationMoment}" pattern="MM"
		var="month" />
	<fmt:formatDate value="${row.publicationMoment}" pattern="yyyy"
		var="year" />
	<fmt:formatDate value="${row.publicationMoment}" pattern="HH"
		var="hours" />
	<fmt:formatDate value="${row.publicationMoment}" pattern="mm"
		var="minutes" />
	<jstl:choose>
		<jstl:when
			test="${(year eq currentYear) and ((month eq currentMonth and day <=currentDay) or (month eq currentMonth-1 and day >= currentDay))}">
			<jstl:set var="color" value="DarkOliveGreen" />
		</jstl:when>
		<jstl:when
			test="${(year eq currentYear) and ((month eq currentMonth-1 and day < currentDay) or (month eq currentMonth-2 and day >= currentDay))}">
			<jstl:set var="color" value="Moccasin" />
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="color" value="Grey" />
		</jstl:otherwise>
	</jstl:choose>
	<display:column property="body">
		<jstl:out value="${row.body}"></jstl:out>
	</display:column>

	<jstl:if test="${localeCode eq 'en'}">
		<display:column property="publicationMoment" style="color: ${color}" format="{0,date,yy/MM/dd HH:mm}"/>
	</jstl:if>
	<jstl:if test="${localeCode eq 'es'}">
		<display:column property="publicationMoment" style="color: ${color}" format="{0,date,dd-MM-yy HH:mm}"/>
	</jstl:if>
	<display:column>
		<a href="quolet/company/show.do?quoletId=${row.id}"> <spring:message
				code="show" />
		</a>
	</display:column>


	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${row.draftMode eq true}">
			<display:column>
				<a href="quolet/company/edit.do?quoletId=${row.id}"> <spring:message
						code="edit" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>