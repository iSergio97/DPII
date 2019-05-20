<%--
administrator/dashborard.jsp

Copyright (C) 2019 Group 16 Desing & Testing II
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="seriesPerPublisherStatistics.minimum" />
	<br>
	<jstl:out value="${seriesPerPublisherStatistics[0]}" />
</p>
<p>
	<spring:message code="seriesPerPublisherStatistics.maximum" />
	<br>
	<jstl:out value="${seriesPerPublisherStatistics[1]}" />
</p>
<p>
	<spring:message code="seriesPerPublisherStatistics.average" />
	<br>
	<jstl:out value="${seriesPerPublisherStatistics[2]}" />
</p>
<p>
	<spring:message code="seriesPerPublisherStatistics.standardDeviation" />
	<br>
	<jstl:out value="${seriesPerPublisherStatistics[3]}" />
</p>

<p>
	<spring:message code="seasonsPerSerieStatistics.minimum" />
	<br>
	<jstl:out value="${seasonsPerSerieStatistics[0]}" />
</p>
<p>
	<spring:message code="seasonsPerSerieStatistics.maximum" />
	<br>
	<jstl:out value="${seasonsPerSerieStatistics[1]}" />
</p>
<p>
	<spring:message code="seasonsPerSerieStatistics.average" />
	<br>
	<jstl:out value="${seasonsPerSerieStatistics[2]}" />
</p>
<p>
	<spring:message code="seasonsPerSerieStatistics.standardDeviation" />
	<br>
	<jstl:out value="${seasonsPerSerieStatistics[3]}" />
</p>

<p>
	<spring:message code="chaptersPerSeasonStatistics.minimum" />
	<br>
	<jstl:out value="${chaptersPerSeasonStatistics[0]}" />
</p>
<p>
	<spring:message code="chaptersPerSeasonStatistics.maximum" />
	<br>
	<jstl:out value="${chaptersPerSeasonStatistics[1]}" />
</p>
<p>
	<spring:message code="chaptersPerSeasonStatistics.average" />
	<br>
	<jstl:out value="${chaptersPerSeasonStatistics[2]}" />
</p>
<p>
	<spring:message code="chaptersPerSeasonStatistics.standardDeviation" />
	<br>
	<jstl:out value="${chaptersPerSeasonStatistics[3]}" />
</p>

<p>
	<spring:message code="commentsPerSerieStatistics.minimum" />
	<br>
	<jstl:out value="${commentsPerSerieStatistics[0]}" />
</p>
<p>
	<spring:message code="commentsPerSerieStatistics.maximum" />
	<br>
	<jstl:out value="${commentsPerSerieStatistics[1]}" />
</p>
<p>
	<spring:message code="commentsPerSerieStatistics.average" />
	<br>
	<jstl:out value="${commentsPerSerieStatistics[2]}" />
</p>
<p>
	<spring:message code="commentsPerSerieStatistics.standardDeviation" />
	<br>
	<jstl:out value="${commentsPerSerieStatistics[3]}" />
</p>
