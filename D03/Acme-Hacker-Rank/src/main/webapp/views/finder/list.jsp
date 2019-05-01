<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<a href="finder/create.do"><spring:message code="create" /></a>
</p>

<display:table name="positions" id="row" requestURI="/finder/list.do" pagesize="5" class="displaytag"> 

<display:column property="title">
		<jstl:out value="${row.title}" />
	</display:column>
	
	<display:column property="description">
		<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column>
		<a href="position/company/show.do?positionId=${row.id}" ><spring:message code="show"/></a>
		</display:column>
		
		<display:column>
		<a href="application/company/list.do?positionId=${row.id}" ><spring:message code="application"/></a>
	</display:column>

</display:table>