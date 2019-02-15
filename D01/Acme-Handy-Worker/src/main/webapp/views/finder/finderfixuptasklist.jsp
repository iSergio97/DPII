<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="handyWorker.finder.list" />
</p>

<display:table name="fixuptasks" id="fixuptask" requestURI="/finder/handy-worker/fixuptasklist.do" pagesize="5" class="displaytag">
	<display:column property="ticker" titleKey="handyWorker.fixUpTask.ticker" />
	<display:column property="date" titleKey="handyWorker.fixUpTask.date" />
	<display:column property="description" titleKey="handyWorker.fixUpTask.description" />
	<display:column property="address" titleKey="handyWorker.fixUpTask.address" />
</display:table>
