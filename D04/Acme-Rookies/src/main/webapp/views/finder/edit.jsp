<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="finder" method="POST" action="finder/edit.do">

<!-- Hidden fields -->

	<form:hidden path="id" />

<!-- Input fields -->

<div id="keyword" class="keyword">
		<form:label path="keyword">
			<spring:message code="keyword" />
		</form:label>
		<form:input path="keyword" />
		<form:errors path="keyword" />
	</div>
	
	<div id="deadline" class="deadline">
		<form:label path="deadline">
			<spring:message code="deadline" />
		</form:label>
		<form:input path="deadline" />
		<form:errors path="deadline" />
	</div>
	
	<div id="maximumDeadline" class="maximumDeadline">
		<form:label path="maximumDeadline">
			<spring:message code="maximumDeadline" />
		</form:label>
		<form:input path="maximumDeadline" />
		<form:errors path="maximumDeadline" />
	</div>
	
	<div id="minimumSalary" class="minimumSalary">
		<form:label path="minimumSalary">
			<spring:message code="minimumSalary" />
		</form:label>
		<form:input path="minimumSalary" />
		<form:errors path="minimumSalary" />
	</div>
	
	<!-- Form options -->
	
	<input type="submit" name="save" value="<spring:message code="send" />" />
	
<input type="button" name="edit" value="<spring:message code='delete' />" onclick="javascript: relativeRedir('finder/edit.do?finderId=${finder.id}');" />

</form:form>