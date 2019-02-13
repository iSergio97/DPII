<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<a href="customer/show.do">Back</a>

<display:table name="fixUpTasks" id="row"
	requestURI="fix-up-task/browse.do" pagesize="5"
	class="displaytag">
	
	<display:column property="maximumPrice" titleKey="fixUpTask.maximumPrice" />
	<display:column property="timeLimit" titleKey="fixUpTask.timeLimit" />
	<display:column property="descriptiom" titleKey="fixUpTask.description" />
	<display:column> <a href="fixUpTask.show.do?fixUpTaskId=${row.id}"> <jstl:out value="Show"/></a> </display:column>
	

</display:table>

<a href="fix-up-task/new.do">New</a>