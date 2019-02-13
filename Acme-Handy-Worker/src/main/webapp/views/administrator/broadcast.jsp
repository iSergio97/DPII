
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="broadcast" />
</p>

<div>
	<form:form modelAttribute="message" method="POST">

		<!-- Campos ocultos -->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="date" />
		<form:hidden path="sender" />
		<form:hidden path="recipients" />
		<form:hidden path="messageBoxes" />

		<!-- Campos de entrada -->

		<div>
			<form:label path="subject">
				<spring:message code="message.subject" />
			</form:label>
			<form:input path="subject" />
			<form:errors path="subject" />
		</div>
		<br>

		<div>
			<form:label path="body">
				<spring:message code="message.body" />
			</form:label>
			<form:input path="body" />
			<form:errors path="body" />
		</div>
		<br>

		<div>
			<form:label path="priority">
				<spring:message code="message.priority" />
			</form:label>
			<form:select path="priority">
				<form:option value="HIGH" />
				<form:option value="NEUTRAL" />
				<form:option value="LOW" />
			</form:select>
			<form:errors path="priority" />
		</div>
		<br>

		<div>
			<form:label path="tags">
				<spring:message code="message.tags" />
			</form:label>
			<form:input path="tags" />
			<form:errors path="tags" />
		</div>
		<br>

		<input type="submit" name="save"
			value="<spring:message code="broadcast"/>" />

	</form:form>
</div>
