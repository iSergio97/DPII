<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="security.name" />
<jstl:out value=": ${customer.name}"/><br/>
<spring:message code="security.middleName" />
<jstl:out value=": ${customer.middleName}"/><br/>
<spring:message code="security.surname" />
<jstl:out value=": ${customer.surname}"/><br/>
<img src="${customer.photo}" alt="customer photo"><br/>
<spring:message code="security.email" />
<jstl:out value=": ${customer.email}"/><br/>
<spring:message code="security.phoneNumber" />
<jstl:out value=": ${customer.phoneNumber}"/><br/>
<spring:message code="security.address" />
<jstl:out value=": ${customer.address}"/><br/>

<display:table name="customer.fixUpTasks" id="row"
	requestURI="/browse.do" pagesize="10"
	class="displaytag">
	
	<display:column property="maximumPrice" titleKey="fixUpTask.maximumPrice" />
	<display:column property="timeLimit" titleKey="fixUpTask.timeLimit" />
	<display:column property="description" titleKey="fixUpTask.description" />
	<display:column> <a href="fixUpTask/handyWorker/show.do?fixUpTaskId=${row.id}"> <jstl:out value="Show"/></a> </display:column>

</display:table>