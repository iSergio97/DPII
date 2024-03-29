<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<h2>
	<jstl:out value="${curriculumName}"/>: <spring:message code="personalData"/>
</h2>
	<acme:field value="${personalData.fullName}" code="personalData.fullName"/>
	<acme:field value="${personalData.phoneNumber}" code="personalData.phoneNumber"/>
	<acme:link value="${personalData.gitHubProfile}" code="personalData.gitHubProfile"/>
	<acme:link value="${personalData.linkedInProfile}" code="personalData.linkedInProfile"/>
	<acme:field value="${personalData.statement}" code="personalData.statement"/>

<a href="personal-data/rookie/edit.do?personalDataID=${personalData.id}">
<spring:message code="editPD"/>
</a>
<br><br>

<h2>
	<spring:message code="positionData"/>
</h2>
<display:table name="positionDataList" id="row">

	<display:column titleKey="positionData.title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column titleKey="positionData.description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column titleKey="positionData.startDate">
		<jstl:out value="${row.startDate}" />
	</display:column>
	
	<display:column titleKey="positionData.endDate">
		<jstl:out value="${row.endDate}" />
	</display:column>
	
	<display:column titleKey="blank">
		<a href="position-data/rookie/edit.do?positionDataId=${row.id}"><spring:message code="action.edit"/></a>
	</display:column>

</display:table>

<a href="position-data/rookie/create.do?curriculumId=${curriculumId}">
	<spring:message code="action.add"/>
</a>
<br><br>

<h2>
	<spring:message code="educationData"/>
</h2>
<display:table name="educationDataList" id="row">

	<display:column titleKey="educationData.degree">
		<jstl:out value="${row.degree}" />
	</display:column>
	
	<display:column titleKey="educationData.institution">
		<jstl:out value="${row.institution}" />
	</display:column>
	
	<display:column titleKey="educationData.mark">
		<jstl:out value="${row.mark}" />
	</display:column>
	
	<display:column titleKey="educationData.startDate">
		<jstl:out value="${row.startDate}" />
	</display:column>
	
	<display:column titleKey="educationData.endDate">
		<jstl:out value="${row.endDate}" />
	</display:column>
	
	<display:column titleKey="blank">
		<a href="education-data/rookie/edit.do?educationDataId=${row.id}"><spring:message code="action.edit"/></a>
	</display:column>

</display:table>

<a href="education-data/rookie/create.do?curriculumId=${curriculumId}">
	<spring:message code="action.add"/>
</a>
<br><br>

<h2>
	<spring:message code="miscellaneousData"/>
</h2>
<display:table name="miscellaneousDataList" id="row">

	<display:column titleKey="miscellaneousData.freeText">
		<jstl:out value="${row.freeText}" />
	</display:column>
	
	<display:column titleKey="miscellaneousData.attachments">
		<jstl:out value="${row.attachments}" />
	</display:column>
	
	<display:column titleKey="blank">
		<a href="miscellaneous-data/rookie/edit.do?miscellaneousDataId=${row.id}"><spring:message code="action.edit"/></a>
	</display:column>

</display:table>

<a href="miscellaneous-data/rookie/create.do?curriculumId=${curriculumId}">
	<spring:message code="action.add"/>
</a>
<br><br>
