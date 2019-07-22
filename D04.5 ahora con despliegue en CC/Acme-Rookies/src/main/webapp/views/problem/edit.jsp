<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="problem" method="POST" action="problem/edit.do">

<!-- Hidden fields -->

<form:hidden path="id" />
<form:hidden path="isDraft" />
<form:hidden path="positionId" />

<!-- Input fields -->

<div id="title" class="title">
		<form:label path="title">
			<spring:message code="title" />
		</form:label>
		<form:input path="title" />
		<form:errors path="title" />
	</div>
	
	<div id="statement" class="statement">
		<form:label path="statement">
			<spring:message code="statement" />
		</form:label>
		<form:input path="statement" />
		<form:errors path="statement" />
	</div>
	
	<div id="hint" class="hint">
		<form:label path="hint">
			<spring:message code="hint" />
		</form:label>
		<form:input path="hint" />
		<form:errors path="hint" />
	</div>
	
	<div id="attachments" class="attachments">
		<form:label path="attachments">
			<spring:message code="attachments" />
		</form:label>
		<form:input path="attachments" />
		<form:errors path="attachments" />
	</div>
	
	<!-- Form options -->
	
	<input type="submit" name="save" value="<spring:message code="send" />" />
	<input type="submit" name="finalMode" value="<spring:message code="finalMode"/>"/>
	
	<form action="problem/edit.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${problem.id}' />" />
	<p>
	</p>
	<jstl:if test="${problem.isDraft}">
<input type="submit" name="delete" value="<spring:message code='delete' />" />	
</jstl:if>
</form>








</form:form>