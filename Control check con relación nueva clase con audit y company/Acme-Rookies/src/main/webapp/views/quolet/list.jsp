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


<display:table name="quolets" id="row" pagesize="5" class="displaytag">
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

<p>Fecha formateada al inglés</p>
<fmt:formatDate pattern="yy/MM/dd HH:mm" value="${currentDate}" />
<br>
<p>Fecha formateada al Español</p>
<fmt:formatDate pattern="dd-MM-yy HH:mm" value="${currentDate}" />

<p style="color: Red">Color rojo</p>
<br>
<p>Locale code</p>
<jstl:if test="${localeCode eq 'es' }">
	<jstl:out value="${day}-${month}-${year%100} ${hours}:${minutes}" />
</jstl:if>
<jstl:if test="${localeCode eq 'en' }">
	<jstl:out value="${year%100}/${month}/${day} ${hours}:${minutes}" />
</jstl:if>

<fmt:formatDate value="${row.publicationMoment}" pattern="dd" var="day" />
<fmt:formatDate value="${row.publicationMoment}" pattern="MM"
	var="month" />
<fmt:formatDate value="${row.publicationMoment}" pattern="yyyy"
	var="year" />

<br>
Día:
<jstl:out value="${day}"></jstl:out>
<br>
Mes:
<jstl:out value="${month}"></jstl:out>
<br>
Año:
<jstl:out value="${year}"></jstl:out>
<br>
<br>
currentDay:
<jstl:out value="${currentDay}"></jstl:out>
<br>
currentMonth:
<jstl:out value="${currentMonth}"></jstl:out>
<br>
currentYear:
<jstl:out value="${currentYear}"></jstl:out>

<br>
<br>
Prueba de conversión de fecha a mano
<jstl:out value="${day}-${month}-${year%100} ${hours}:${minutes}" />
