<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="listFut" id="listFut" requestURI="/handy-worker/finder/fut-listed/show.do" pagesize="5" class="displaytag">

    <security:authorize access="hasRole('handy-worker')" >

        <display:column property="<spring:message code='handyWorker.fut.ticker'/>" titleKey="handyWorker.fut.ticker"/>
        <display:column property="<spring:message code='handyWorker.fut.date'/>" titleKey="handyWorker.fut.date"/>
        <display:column property="<spring:message code='handyWorker.fut.descrip'/>" titleKey="handyWorker.fut.descrip"/>
        <display:column property="<spring:message code='handyWorker.fut.address'/>" titleKey="handyWorker.fut.address"/>
        <display:column property="<spring:message code='handyWorker.finder.minPrice'/>" titleKey="handyWorker.finder.minPrice"/>
        <display:column property="<spring:message code='handyWorker.finder.maxPrice'/>" titleKey="handyWorker.finder.maxPrice"/>

    </security:authorize>

</display:table>