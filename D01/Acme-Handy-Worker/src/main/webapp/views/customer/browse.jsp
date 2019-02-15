<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="customer.fixUpTasks" id="row"
	requestURI="/browse.do" pagesize="5"
	class="displaytag">
	
	<display:column property="descriptiom" titleKey="fixUpTask.description" />
	<display:column> <a href="fixUpTask.show.do?fixUpTaskId=${row.id}"> <jstl:out value="fixUpTask.description"/></a> </display:column>
	<jstl:forEach var="application" items="${row.applications}">
	<display:column property="handyWorker" titleKey="application.handyWorker.name" />
	</jstl:forEach>

</display:table>