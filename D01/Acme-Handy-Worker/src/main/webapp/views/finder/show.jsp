<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p>
	<spring:message code="handyWorker.finder.edit" />
</p>

<form:form modelAttribute="finder" action="finder/handy-worker/edit.do">

	<!-- Hidden fields -->
	<form:hidden path="id"/>
	<form:hidden path="version" />

	<!-- Editable fields -->
	<acme:textbox code="handyWorker.finder.keyWord" path="keyWord" placeholder="<spring:message code='handyWorker.finder.keyWord'/>"/>
	<acme:textbox code="handyWorker.finder.minimumPrice" path="minimumPrice" placeholder="<spring:message code='handyWorker.finder.minimumPrice'/>"/>
	<acme:textbox code="handyWorker.finder.maximumPrice" path="maximumPrice" placeholder="<spring:message code='handyWorker.finder.maximumPrice'/>"/>
	<acme:textbox code="handyWorker.finder.minimumDate" path="minimumDate" placeholder="<spring:message code='handyWorker.finder.minimumDate'/>"/>
	<acme:textbox code="handyWorker.finder.maximumDate" path="maximumDate" placeholder="<spring:message code='handyWorker.finder.maximumDate'/>"/>

	<!-- Control -->
	<acme:submit name="save" code="handyWorker.save"/>
	<acme:cancel code="event.cancel" url="/"/>

</form:form>
