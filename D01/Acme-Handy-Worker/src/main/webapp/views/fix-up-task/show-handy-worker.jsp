<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="fixUpTask/handyWorker/list.do"><spring:message code="fixUpTask.return" /></a><br/>

<spring:message code="fixUpTask.address" />
<jstl:out value=": ${fixUpTask.address}"/><br/>
<spring:message code="fixUpTask.maximumPrice" />
<jstl:out value=": ${fixUpTask.maximumPrice}"/><br/>
<spring:message code="fixUpTask.timeLimit" />
<jstl:out value=": ${fixUpTask.timeLimit}"/><br/>
<spring:message code="fixUpTask.description" />
<jstl:out value=": ${fixUpTask.description}"/><br/>

<display:table name="applications" id="row">
	<display:column property="moment" titleKey="application.moment" />
	<display:column titleKey="application.status">
		<jstl:choose>
			<jstl:when test = "${row.status == 'PENDING'}">
				<spring:message code="application.status.pending" />
			</jstl:when>
			<jstl:when test = "${row.status == 'ACCEPTED'}">
				<spring:message code="application.status.accepted" />
			</jstl:when>
			<jstl:when test = "${row.status == 'REJECTED'}">
				<spring:message code="application.status.rejected" />
			</jstl:when>
			<jstl:otherwise>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	<display:column property="offeredPrice" titleKey="application.offeredprice" />
	<display:column titleKey="application.handyworker">
		<div>
			<jstl:out value="${row.handyWorker.name}" />
		</div>
	</display:column>
	<display:column titleKey="application.fixuptask">
		<div>
			<jstl:out value="${row.fixUpTask.description}" />
		</div>
	</display:column>
	
</display:table>

<jstl:if test="${!accepted}">
<a href="application/create.do?fixuptaskid=${fixUpTask.id}"><spring:message code="fixUpTask.application" /></a>
</jstl:if>
