<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="phase/handyWorker/edit.do" modelAttribute="phase">


<form:hidden path="id"/>
<form:errors cssClass="error" path="id"></form:errors>
<form:hidden path="version"/>
<form:errors cssClass="error" path="version"></form:errors>
<form:hidden path="workPlan"/>
<form:errors cssClass="error" path="workPlan"></form:errors>



<form:label path="title"><spring:message code="phase.title" /></form:label>
<form:input path="title" />
<form:errors cssClass="error" path="title"></form:errors>
<br/>

<form:label path="description"><spring:message code="phase.description" /></form:label>
<form:input path="description" />
<form:errors cssClass="error" path="description"></form:errors>
<br/>

<form:label path="startMoment"><spring:message code="phase.start" /></form:label>
<form:input path="startMoment" />
<form:errors cssClass="error" path="startMoment"></form:errors>
<br/>

<form:label path="endMoment"><spring:message code="phase.end" /></form:label>
<form:input path="endMoment" />
<form:errors cssClass="error" path="endMoment"></form:errors>
<br/>



<input type="submit" name="save" value="<spring:message code="phase.save" />" />
<input type="button" name="cancel" value ="<spring:message code="phase.cancel" />" onclick="javascript:relativeRedir('phase/handyWorker/list.do?workPlanId=${phase.workPlan.id}');"/><br/>
</form:form>