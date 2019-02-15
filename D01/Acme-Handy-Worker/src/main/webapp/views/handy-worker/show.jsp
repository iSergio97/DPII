<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="security.name" />
<jstl:out value=": ${handyWorker.name}"/><br/>
<spring:message code="security.middleName" />
<jstl:out value=": ${handyWorker.middleName}"/><br/>
<spring:message code="security.surname" />
<jstl:out value=": ${handyWorker.surname}"/><br/>
<img src="${handyWorker.photo}" alt="customer photo"><br/>
<spring:message code="security.email" />
<jstl:out value=": ${handyWorker.email}"/><br/>
<spring:message code="security.phoneNumber" />
<jstl:out value=": ${handyWorker.phoneNumber}"/><br/>
<spring:message code="security.address" />
<jstl:out value=": ${handyWorker.address}"/><br/>

<display:table name="handyWorker.tutorials" id="row"
	requestURI="/list.do" pagesize="10"
	class="displaytag">
	
	<display:column property="title" titleKey="tutorial.title" />
	<display:column property="summary" titleKey="tutorial.summary" />
	<display:column property="lastUpdated" titleKey="tutorial.lastUpdated" />
	<display:column> <a href="tutorial/display.do?tutorialId=${row.id}"> <jstl:out value="Show"/></a> </display:column>

</display:table>