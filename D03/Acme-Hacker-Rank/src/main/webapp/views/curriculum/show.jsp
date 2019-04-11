<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<strong> <jstl:out value="${crName}"/> </strong>
<br>
<br>


<spring:message code="fullName"/>: <jstl:out value="${personalData.fullName}"/>
<br>
<spring:message code="phoneNumber"/>: <jstl:out value="${personalData.phoneNumber}"/>
<br>
<spring:message code="gitHubProfile"/>: <a href="${personalData.gitHubProfile}"> <jstl:out value="${personalData.gitHubProfile}"/> </a>
<br>
<spring:message code="linkedIn"/>: <a href="${personalData.linkedInProfile}"> <jstl:out value="${personalData.linkedInProfile}"/> </a>
<br>
<spring:message code="statement"/>: <jstl:out value="${personalData.statement}"/>
<br>
<br>

<a href="personal-data/hacker/edit.do?personalDataID=${personalData.id}">
<spring:message code="editPD"/>
</a>

<br>
<br>

<display:table name="misData" id="row">

	<display:column titleKey="freeText">
		<jstl:out value="${row.freeText}" />
	</display:column>
	
	<display:column titleKey="attachments">
		<jstl:out value="${row.attachments}" />
	</display:column>

</display:table>

<br>
<br>

<display:table name="eDatas" id="row">

	<display:column titleKey="freeText">
		<jstl:out value="${row.degree}" />
	</display:column>
	
	<display:column titleKey="institution">
		<jstl:out value="${row.institution}" />
	</display:column>
	
	<display:column titleKey="mark">
		<jstl:out value="${row.mark}" />
	</display:column>
	
	<display:column titleKey="startDate">
		<jstl:out value="${row.startDate}" />
	</display:column>
	
	<display:column titleKey="endDate">
		<jstl:out value="${row.endDate}" />
	</display:column>

</display:table>
<br>
<br>

<display:table name="personalDatas" id="row">

	<display:column titleKey="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="startDate">
		<jstl:out value="${row.mark}" />
	</display:column>
	
	<display:column titleKey="endDate">
		<jstl:out value="${row.endDate}" />
	</display:column>

</display:table>

<br>
<br>
