
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="nick" />
	<br>
	<jstl:out value="${socialProfile.nick}" />
</p>
<p>
	<spring:message code="socialNetworkName" />
	<br>
	<jstl:out value="${socialProfile.socialNetworkName}" />
</p>
<p>
	<spring:message code="profileLink" />
	<br>
	<a href="<jstl:out value="${socialProfile.profileLink}" />"><spring:message code="profileLink" /></a>
</p>
