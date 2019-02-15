<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="actor/handyworker/edit.do" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<spring:message code="actor.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="middleName">
		<spring:message code="actor.middleName" />:
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />:
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />:
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="telephone">
		<spring:message code="actor.telephone" />:
	</form:label>
	<form:input path="telephone" />
	<form:errors cssClass="error" path="telephone" />
	<br />

	<form:label path="address">
		<spring:message code="actor.address" />:
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="actor.save"/>" />

	<input type="submit" name="delete"
		value="<spring:message code="actor.delete"/>" />


	<input type="submit" name="cancel"
		value="<spring:message code="actor.cancel"/>" />






</form:form>