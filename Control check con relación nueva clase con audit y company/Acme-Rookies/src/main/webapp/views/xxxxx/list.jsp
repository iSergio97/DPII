<%--
 * xxxxx/list.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author Carlos Ruiz Briones
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="xxxxxs" id="row" pagesize="5" class="displaytag">

	<display:column property="body">
	<jstl:out value="${row.body}"></jstl:out>
	</display:column>
	<display:column>
		<a href="xxxxx/company/show.do?xxxxxId=${row.id}"> <spring:message
				code="show" />
		</a>
	</display:column>
	<security:authorize access="hasRole('COMPANY')">
		<jstl:if test="${row.draftMode eq true}">
			<display:column>
				<a href="xxxxx/company/edit.do?xxxxxId=${row.id}"> <spring:message
						code="edit" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>
