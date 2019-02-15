
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="suspicious" />
</p>

<display:table name="suspiciousActors" id="row">
	<display:column property="name" titleKey="suspicious.name" />
	<display:column property="middleName" titleKey="suspicious.middleName" />
	<display:column property="surname" titleKey="suspicious.surname" />
	<display:column titleKey="suspicious.options">
		<form action="administrator/suspicious.do" method="POST">
			<input type="hidden" name="id" value="<jstl:out value="${row.id}" />">
			<jstl:choose>
				<jstl:when test = "${row.isBanned}">
					<input type="submit" name="unban" value='<spring:message code="suspicious.unban" />' />
				</jstl:when>
				<jstl:otherwise>
					<input type="submit" name="ban" value='<spring:message code="suspicious.ban" />' />
				</jstl:otherwise>
			</jstl:choose>
		</form>
	</display:column>
</display:table>
