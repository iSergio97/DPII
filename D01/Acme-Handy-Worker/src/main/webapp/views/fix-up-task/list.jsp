<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="profile/show.do"><spring:message code="fixUpTask.toProfile" /></a>

<display:table name="fixUpTasks" id="row"
	requestURI="fixUpTask/customer/list.do" pagesize="5"
	class="displaytag">
	
	<display:column property="maximumPrice" titleKey="fixUpTask.maximumPrice" />
	<display:column property="timeLimit" titleKey="fixUpTask.timeLimit" />
	<display:column property="description" titleKey="fixUpTask.description" />
	<display:column> <a href="fixUpTask/customer/show.do?fixUpTaskId=${row.id}"> <spring:message code="fixUpTask.show" /></a> </display:column>
	

</display:table>

<a href="fixUpTask/customer/create.do"><spring:message code="fixUpTask.new" /></a>