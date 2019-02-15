<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="editFinder"
	action="finder/handy-worker/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<security:authorize access="hasRole('handy-worker')">

		<form:label path="keyWord"
			placeholder="<spring:message code='handyWorker.keyWord'/>" />
		<form:input path="${handyWorker.finder.keyWord}" />
		<form:label path="minPrice"
			placeholder="<spring:message code='handyWorker.minPrice'/>" />
		<form:input path="${handyWorker.finder.minPrice}" />
		<form:label path="maxPrice"
			placeholder="<spring:message code='handyWorker.maxPrice'/>" />
		<form:input path="${handyWorker.finder.maxPrice}" />
		<form:label path="minDate"
			placeholder="<spring:message code='handyWorker.minDate'/>" />
		<form:input path="${handyWorker.finder.minDate}" />
		<form:label path="maxDate"
			placeholder="<spring:message code='handyWorker.maxDate'/>" />
		<form:input path="${handyWorker.finder.maxDate}" />

		<input type="submit" name="editFinder"
			value="<spring:message code='handyWorker.save'/>" />

	</security:authorize>

</form:form>