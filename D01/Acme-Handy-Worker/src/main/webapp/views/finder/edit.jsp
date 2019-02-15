<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p>
	<spring:message code="handyWorker.finder.edit" />
</p>

	<form:form modelAttribute="finder" action="finder/handyWorker/edit.do">
		<!-- Hidden fields -->
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="handyWorker" />

		<!-- Editable fields -->


		<form:label path="keyword">
			<spring:message code="finder.keyword" />
		</form:label>
		<form:input path="keyword" />
		<form:errors cssClass="error" path="keyword" />
		<br />

		<form:label path="warranty">
			<spring:message code="finder.warranty" />
		</form:label>
		<form:select id="warranties" path="warranty">
			<form:options items="${ warranties}" itemLabel="title" itemValue="id" />
			<form:option label="----" value="0" />
		</form:select>
		<br />

		<form:label path="minimumDate">
			<spring:message code="finder.minimumDate" />
		</form:label>
		<form:input path="minimumDate" />
		<form:errors cssClass="error" path="minimumDate" />
		<br />

		<form:label path="maximumDate">
			<spring:message code="finder.maximumDate" />
		</form:label>
		<form:input path="maximumDate" />
		<form:errors cssClass="error" path="maximumDate" />
		<br />

		<form:label path="minimumPrice">
			<spring:message code="finder.minimumPrice" />
		</form:label>
		<form:input path="minimumPrice" />
		<form:errors cssClass="error" path="minimumPrice" />
		<br />

		<form:label path="maximumPrice">
			<spring:message code="finder.maximumPrice" />
		</form:label>
		<form:input path="maximumPrice" />
		<form:errors cssClass="error" path="maximumPrice" />
		<br />

		<form:label path="fixUpTaskCategory">
			<spring:message code="finder.fixUpTaskCategory" />
		</form:label>
		<form:select id="categories" path="fixUpTaskCategory">
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
			<form:option label="----" value="0" />
		</form:select>
		<br />

		<!-- Control -->
		<input type="submit" name="save"
			value="<spring:message code="finder.save" />" />

		<input type="button" name="cancel"
			value="<spring:message code='security.cancel' />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</form:form>
