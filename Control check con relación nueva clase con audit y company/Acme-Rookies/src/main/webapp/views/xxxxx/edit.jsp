<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="xxxxxForm" method="POST"
	action="xxxxx/company/save.do">

	<!-- Hidden Fields -->

	<form:hidden path="id" />
	<form:hidden path="auditId" />
	
	<!-- Input Fields -->
	<acme:register path="body" code="body" />
	<acme:registerWithPlaceholder path="picture" code="picture" placeholder="https://imgur.com/a/TqyGLNB"/>
	
	<!-- Buttons -->
	<acme:submit name="save" code="save" />
	<jstl:if test="${xxxxx.id ne 0 and xxxxx.isDraft}">
		<acme:submit name="saveAsFinal" code="saveAsFinal" />
		<acme:submit name="delete" code="delete" />
	</jstl:if>
	
	<acme:cancel url="welcome/index.do" code="action.cancel" />

</form:form>