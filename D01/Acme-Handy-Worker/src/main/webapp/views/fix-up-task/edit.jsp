<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="fixUpTask/customer/edit.do" modelAttribute="fixUpTask">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="customer" />
	<form:hidden path="applications" />
	<form:hidden path="workPlan" />
	<form:hidden path="complaints" />

	<form:label path="address">
		<spring:message code="fixUpTask.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address"></form:errors>
	<br />

	<form:label path="maximumPrice">
		<spring:message code="fixUpTask.maximumPrice" />
	</form:label>
	<form:input path="maximumPrice" />
	<form:errors cssClass="error" path="maximumPrice"></form:errors>
	<br />

	<form:label path="timeLimit">
		<spring:message code="fixUpTask.timeLimit" />
	</form:label>
	<form:input path="timeLimit" />
	<form:errors cssClass="error" path="timeLimit"></form:errors>
	<br />

	<spring:message code="fixUpTask.warranty" />
	<form:select id="warranties" path="warranty">
		<form:options items="${warranties}" itemLabel="title" itemValue="id" />
		<form:option value="0" label="---" />
	</form:select>
	<br />

	<spring:message code="fixUpTask.category" />
	<form:select id="fixUpTaskCategories" path="fixUpTaskCategory">
		<form:options items="${fixUpTaskCategories}" itemLabel="name"
			itemValue="id" />
		<form:option value="0" label="---" />
	</form:select>
	<br />


	<form:label path="description">
		<spring:message code="fixUpTask.description" />
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description"></form:errors>
	<br />

	<input type="submit" name="save"
		value="<spring:message code="fixUpTask.update" />" />
	<input type="button" name="cancel"
		value="<spring:message code="fixUpTask.cancel" />"
		onclick="javascript:relativeRedir('fixUpTask/customer/list.do');" />

</form:form>