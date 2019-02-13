<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="fixUpTask/customer/list.do"><spring:message code="fixUpTask.return" /></a><br/>
<br/>
<strong><spring:message code="fixUpTask.address" />:</strong>
		<jstl:out value="${fixUpTask.address}"/><br/>
<strong><spring:message code="fixUpTask.maximumPrice" />:</strong>
		<jstl:out value="${fixUpTask.maximumPrice}"/>
		<spring:message code="system.euro"/> +
		<spring:message code="system.vat"/>
		<jstl:out value="(${fixUpTask.maximumPrice*(1+vat)}"/>
		<spring:message code="system.euro.closedBracket"/><br/>
			
			
<strong><spring:message code="fixUpTask.timeLimit" />:</strong>
		<jstl:out value="${fixUpTask.timeLimit}"/><br/>
<strong><spring:message code="fixUpTask.description" />:</strong>
		<jstl:out value="${fixUpTask.description}"/><br/>

<display:table name="applications" id="row">
	<display:column property="moment" titleKey="application.moment" />
	<display:column titleKey="application.status">
		<jstl:choose>
			<jstl:when test = "${row.status == 'PENDING'}">
				<font color="gray"><spring:message code="application.status.pending" /></font>
			</jstl:when>
			<jstl:when test = "${row.status == 'ACCEPTED'}">
				<font color="green"><spring:message code="application.status.accepted" /></font>
			</jstl:when>
			<jstl:when test = "${row.status == 'REJECTED'}">
				<font color="orange"><spring:message code="application.status.rejected" /></font>
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
	<display:column titleKey="application.options">
		<div>
			<a href="application/display.do?id=${row.id}">
				<spring:message code="application.open" />
			</a>
			<jstl:if test = "${row.status == 'PENDING'}">
				<form action="application/customer.do" method="POST">
					<input type="hidden" name="id" value="<jstl:out value="${row.id}" />">
					<input type="submit" name="accept" value='<spring:message code="application.accept" />' />
					<input type="submit" name="reject" value='<spring:message code="application.reject" />' />
				</form>
			</jstl:if>
		</div>
	</display:column>
</display:table>

<a href="fixUpTask/customer/edit.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.edit" /></a>
<a href="fixUpTask/customer/delete.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.delete" /></a>