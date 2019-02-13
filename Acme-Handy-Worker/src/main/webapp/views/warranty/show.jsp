<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="warranty/administrator/list.do"><spring:message code="warranty.cancel" /></a><br/>

<spring:message code="warranty.title" />
<jstl:out value=" ${warranty.title}"/><br/>
<spring:message code="warranty.laws" />
<jstl:out value="${warranty.applicableLaws}"/><br/>
<spring:message code="warranty.terms" />
<jstl:out value=" ${warranty.terms}"/><br/>




<jstl:if test="${warranty.draft}">
<a href="warranty/administrator/save.do?warrantyId=${warranty.id}"><spring:message code="warranty.saveFinal" /></a>
<a href="warranty/administrator/edit.do?warrantyId=${warranty.id}"><spring:message code="warranty.edit" /></a>
<a href="warranty/administrator/delete.do?warrantyId=${warranty.id}"><spring:message code="warranty.delete" /></a>
</jstl:if>