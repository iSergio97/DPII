<%--
critique/list.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
--%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="critiques" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<acme:column value="${row.moment}" code="critique.moment" />
	<acme:column value="${row.text}" code="critique.text" />
	<acme:column value="${row.score}" code="critique.score" />
	<acme:column value="${row.serie.title}" code="critique.series" />
	<acme:actioncolumn url="critique/public/show.do?critiqueId" value="${row.id}" code="action.show"/>
	<acme:actioncolumn url="critique/critic/edit.do?critiqueId" value="${row.id}" code="action.edit"/>
</display:table>
