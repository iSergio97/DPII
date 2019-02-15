
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="dashboard" />
</p>

<!-- QUERY C.1 -->

<p>
	<spring:message code="dashboard.c.1.minimum" />
	<br>
	<jstl:out value="${fixUpTaskStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.c.1.maximum" />
	<br>
	<jstl:out value="${fixUpTaskStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.c.1.average" />
	<br>
	<jstl:out value="${fixUpTaskStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.c.1.stddev" />
	<br>
	<jstl:out value="${fixUpTaskStatisticsStandardDeviation}" />
</p>

<!-- QUERY C.2 -->

<p>
	<spring:message code="dashboard.c.2.minimum" />
	<br>
	<jstl:out value="${applicationStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.c.2.maximum" />
	<br>
	<jstl:out value="${applicationStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.c.2.average" />
	<br>
	<jstl:out value="${applicationStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.c.2.stddev" />
	<br>
	<jstl:out value="${applicationStatisticsStandardDeviation}" />
</p>

<!-- QUERY C.3 -->

<p>
	<spring:message code="dashboard.c.3.minimum" />
	<br>
	<jstl:out value="${maximumPriceStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.c.3.maximum" />
	<br>
	<jstl:out value="${maximumPriceStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.c.3.average" />
	<br>
	<jstl:out value="${maximumPriceStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.c.3.stddev" />
	<br>
	<jstl:out value="${maximumPriceStatisticsStandardDeviation}" />
</p>

<!-- QUERY C.4 -->

<p>
	<spring:message code="dashboard.c.4.minimum" />
	<br>
	<jstl:out value="${offeredPriceStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.c.4.maximum" />
	<br>
	<jstl:out value="${offeredPriceStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.c.4.average" />
	<br>
	<jstl:out value="${offeredPriceStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.c.4.stddev" />
	<br>
	<jstl:out value="${offeredPriceStatisticsStandardDeviation}" />
</p>

<!-- QUERY C.5 -->

<p>
	<spring:message code="dashboard.c.5" />
	<br>
	<jstl:out value="${pendingRatio}" />
</p>

<!-- QUERY C.6 -->

<p>
	<spring:message code="dashboard.c.6" />
	<br>
	<jstl:out value="${acceptedRatio}" />
</p>

<!-- QUERY C.7 -->

<p>
	<spring:message code="dashboard.c.7" />
	<br>
	<jstl:out value="${rejectedRatio}" />
</p>

<!-- QUERY C.8 -->

<p>
	<spring:message code="dashboard.c.8" />
	<br>
	<jstl:out value="${expiredRatio}" />
</p>

<!-- QUERY C.9 -->

<p>
	<spring:message code="dashboard.c.9" />
	<br>
	<display:table name="topFixUpTasks" id="row">
		<display:column property="name" titleKey="dashboard.name" />
		<display:column property="middleName" titleKey="dashboard.middleName" />
		<display:column property="surname" titleKey="dashboard.surname" />
	</display:table>
</p>

<!-- QUERY C.10 -->

<p>
	<spring:message code="dashboard.c.10" />
	<br>
	<display:table name="topApplications" id="row">
		<display:column property="name" titleKey="dashboard.name" />
		<display:column property="middleName" titleKey="dashboard.middleName" />
		<display:column property="surname" titleKey="dashboard.surname" />
	</display:table>
</p>

<!-- QUERY B.1 -->

<p>
	<spring:message code="dashboard.b.1.minimum" />
	<br>
	<jstl:out value="${complaintStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.b.1.maximum" />
	<br>
	<jstl:out value="${complaintStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.b.1.average" />
	<br>
	<jstl:out value="${complaintStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.b.1.stddev" />
	<br>
	<jstl:out value="${complaintStatisticsStandardDeviation}" />
</p>

<!-- QUERY B.2 -->

<p>
	<spring:message code="dashboard.b.2.minimum" />
	<br>
	<jstl:out value="${noteStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.b.2.maximum" />
	<br>
	<jstl:out value="${noteStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.b.2.average" />
	<br>
	<jstl:out value="${noteStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.b.2.stddev" />
	<br>
	<jstl:out value="${noteStatisticsStandardDeviation}" />
</p>

<!-- QUERY B.3 -->

<p>
	<spring:message code="dashboard.b.3" />
	<br>
	<jstl:out value="${complaintRatio}" />
</p>

<!-- QUERY B.4 -->

<p>
	<spring:message code="dashboard.b.4" />
	<br>
	<display:table name="topComplaintsCustomer" id="row">
		<display:column property="name" titleKey="dashboard.name" />
		<display:column property="middleName" titleKey="dashboard.middleName" />
		<display:column property="surname" titleKey="dashboard.surname" />
	</display:table>
</p>

<!-- QUERY B.5 -->

<p>
	<spring:message code="dashboard.b.5" />
	<br>
	<display:table name="topComplaintsHandyWorker" id="row">
		<display:column property="name" titleKey="dashboard.name" />
		<display:column property="middleName" titleKey="dashboard.middleName" />
		<display:column property="surname" titleKey="dashboard.surname" />
	</display:table>
</p>
