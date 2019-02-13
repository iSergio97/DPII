<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="draftedWarranties" id="row"
	requestURI="warranty/administrator/list.do" pagesize="5"
	class="displaytag">
	<display:column property="title" titleKey="warranty.title" />
	<display:column property="terms" titleKey="warranty.terms" />
	<display:column property="applicableLaws" titleKey="warranty.laws" />
	<display:column> <a href="warranty/administrator/show.do?warrantyId=${row.id}"> <spring:message code="warranty.show" /></a> </display:column>
</display:table>

<display:table name="warranties" id="row"
	requestURI="warranty/administrator/list.do" pagesize="5"
	class="displaytag">
	<display:column property="title" titleKey="warranty.title" />
	<display:column property="terms" titleKey="warranty.terms" />
	<display:column property="applicableLaws" titleKey="warranty.laws" />
	<display:column> <a href="warranty/administrator/show.do?warrantyId=${row.id}"> <spring:message code="warranty.show" /></a> </display:column>
</display:table>

<a href="warranty/administrator/create.do"><spring:message code="warranty.new" /></a>