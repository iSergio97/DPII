<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="warranty/administrator/edit.do" modelAttribute="warranty">

<input type="button" name="cancel" value ="<spring:message code="warranty.cancel" />" onclick="javascript:relativeRedir('warranty/administrator/list.do');"/><br/>
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="draft"/>



<form:label path="title"><spring:message code="warranty.title" /></form:label>
<form:input path="title" />
<form:errors cssClass="error" path="title"></form:errors>
<br/>






<jstl:forEach begin="1" end ="${size}" var="x" >
	<form:label path="applicableLaws"><spring:message code="warranty.laws" />${x}</form:label>
    <form:input path="applicableLaws[${x}]" />
    <form:errors cssClass="error" path="applicableLaws"></form:errors>
</jstl:forEach>
<br/>

<form:label path="terms"><spring:message code="warranty.terms" /></form:label>
<form:input path="terms" />
<form:errors cssClass="error" path="terms"></form:errors>
<br/>


<input type="submit" name="save" value="<spring:message code="warranty.save" />" />

</form:form>