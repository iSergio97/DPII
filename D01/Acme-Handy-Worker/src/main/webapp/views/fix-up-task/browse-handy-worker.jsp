<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="actors" id="row"
	requestURI="fix-up-task/browse-handy-worker.do" pagesize="5"
	class="displaytag">

	<display:column property="customer" titleKey="fixUpTask.customer" />
	<display:column titleKey="fixUpTask.customer"> <a href="customer.show.do?customerId=${row.id}"> <jstl:out value="fixUpTask.customer.surname"/></a> </display:column>
	<display:column property="category" titleKey="fixUpTask.category" />
	<display:column property="address" titleKey="fixUpTask.address" />
	<display:column property="timeLimit" titleKey="fixUpTask.timeLimit" />
	<display:column property="maximumPrice" titleKey="fixUpTask.maximumPrice" />



</display:table>