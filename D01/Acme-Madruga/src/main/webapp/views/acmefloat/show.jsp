
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- TODO: SHOW SHOULD HAVE A FORM TO ADD PICTURES -->

<p>
	<spring:message code="show" />
</p>

<p>
	<spring:message code="title" />
	<br>
	<jstl:out value="${acmeFloat.title}" />
</p>
<p>
	<spring:message code="description" />
	<br>
	<jstl:out value="${acmeFloat.description}" />
</p>
<p>
	<spring:message code="brotherhood" />
	<br>
	<jstl:out value="${acmeFloat.brotherhood.name}" />
	<br>
	<jstl:out value="${acmeFloat.brotherhood.middleName}" />
	<br>
	<jstl:out value="${acmeFloat.brotherhood.surname}" />
</p>

<p>
	<spring:message code="processions" />
	<jstl:forEach items="${acmeFloat.processions}" var="procession">
		<br>
		<jstl:out value="${procession.title}" />
	</jstl:forEach>
</p>

<p>
	<spring:message code="pictures" />
	<jstl:forEach items="${acmeFloat.pictures}" var="picture">
		<br>
		<jstl:out value="${picture}" />
	</jstl:forEach>
</p>

<form action="acmefloat/show.do" method="POST">
	<input type="hidden" name="id" value="<jstl:out value='${acmeFloat.id}' />" />
	<p>
		<spring:message code="addpicture" />
		<br><input type="text" name="addpicture" />
	</p>
	<input type="submit" name="addpicture" value="<spring:message code='save' />" />
</form>
