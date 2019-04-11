<%--
 * parade/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="miscellaneousDataList" id="row"
	requestURI="miscellaneous-data/hacker/list.do" pagesize="5" class="displaytag">

	<display:column titleKey="miscellaneousData.freeText">
		<jstl:out value="${row.freeText}"/>
	</display:column>
	<display:column titleKey="miscellaneousData.attachments">
		<jstl:out value="${row.attachments}"/>
	</display:column>

	<display:column titleKey="blank">
		<a href="miscellaneous-data/hacker/edit.do?miscellaneousDataId=<jstl:out value="${row.id}" />">
			<spring:message code="action.edit"/>
		</a>
	</display:column>
	
	<display:column titleKey="blank">
		<a href="miscellaneous-data/hacker/show.do?miscellaneousDataId=<jstl:out value="${row.id}" />">
			<spring:message code="action.show"/>
		</a>
	</display:column>

</display:table>
