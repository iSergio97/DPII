<%--
 * administrator/dashborard.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- QUERY C.1 -->

<p>
	<spring:message code="dashboard.c.1.minimum" />
	<br>
	<jstl:out value="${brotherhoodMemberStatisticsMinimum}" />
</p>

<p>
	<spring:message code="dashboard.c.1.maximum" />
	<br>
	<jstl:out value="${brotherhoodMemberStatisticsMaximum}" />
</p>

<p>
	<spring:message code="dashboard.c.1.average" />
	<br>
	<jstl:out value="${brotherhoodMemberStatisticsAverage}" />
</p>

<p>
	<spring:message code="dashboard.c.1.stddev" />
	<br>
	<jstl:out value="${brotherhoodMemberStatisticsStandardDeviation}" />
</p>

<!-- QUERY C.2 -->

<p>
	<spring:message code="dashboard.c.2" />
	<br>
	<display:table name="largestBrotherhoods" id="row">
		<display:column titleKey="dashboard.title">
			<jstl:out value="${row.title}"/>
		</display:column>
	</display:table>
</p>

<!-- QUERY C.3 -->

<p>
	<spring:message code="dashboard.c.3" />
	<br>
	<display:table name="smallestBrotherhoods" id="row">
		<display:column titleKey="dashboard.title">
			<jstl:out value="${row.title}"/>
		</display:column>
	</display:table>
</p>

<!-- QUERY C.4 -->

<p>
	<spring:message code="dashboard.c.4.accepted" />
	<br>
	<jstl:out value="${acceptedRequestRatio}" />
</p>

<p>
	<spring:message code="dashboard.c.4.rejected" />
	<br>
	<jstl:out value="${rejectedRequestRatio}" />
</p>

<p>
	<spring:message code="dashboard.c.4.pending" />
	<br>
	<jstl:out value="${pendingRequestRatio}" />
</p>

<!-- QUERY C.5 -->

<p>
	<spring:message code="dashboard.c.5" />
	<br>
	<display:table name="paradesWithin30Days" id="row">
		<display:column titleKey="dashboard.title">
			<jstl:out value="${row.title}"/>
		</display:column>
	</display:table>
</p>

<!-- QUERY C.7 -->

<p>
	<spring:message code="dashboard.c.7" />
	<br>
	<display:table name="membersWithAtLeastTenPercentOfTheMaximumNumberOfAcceptedRequests" id="row">
		<display:column titleKey="dashboard.name">
			<jstl:out value="${row.name}"/>
		</display:column>
		<display:column titleKey="dashboard.middleName">
			<jstl:out value="${row.middleName}"/>
		</display:column>
		<display:column titleKey="dashboard.surname">
			<jstl:out value="${row.surname}"/>
		</display:column>
	</display:table>
</p>

<!-- QUERY C.8 -->

<spring:message code="dashboard.c.8" />
<br>
<table>
	<tr>
		<th>
			<spring:message code="dashboard.c.8.keys" />
		</th>
		<th>
			<spring:message code="dashboard.c.8.values" />
		</th>
	</tr>
	<jstl:forEach items="${positionHistogram}" var="entry">
		<tr>
			<td>
				<jstl:out value="${entry.key}" />
			</td>
			<td>
				<jstl:out value="${entry.value}%" />
			</td>
		</tr>
	</jstl:forEach>
</table>
