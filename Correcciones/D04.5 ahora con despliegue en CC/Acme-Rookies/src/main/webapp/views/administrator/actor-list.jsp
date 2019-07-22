<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="listActors" id="row">

	<display:column property="name">
		<jstl:out value="${row.name}" />
	</display:column>
	
	<display:column property="surnames">
		<jstl:out value="${row.surnames}" />
	</display:column>
	
	<display:column property="isFlagged">
		<jstl:out value="${row.isFlagged}" />
	</display:column>
	
	<display:column>
	<jstl:if test="${row.isBanned ne true}">
		<a href="administrator/actor/ban.do?actorId=${row.id}"> <spring:message code="ban"/> </a>
	</jstl:if>
	
	<jstl:if test="${row.isBanned eq true}">
		<a href="administrator/actor/unban.do?actorId=${row.id}"> <spring:message code="unban"/> </a>
	</jstl:if>
	
	</display:column>

</display:table>