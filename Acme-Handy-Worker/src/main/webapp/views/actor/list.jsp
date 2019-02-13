<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="actors" id="row"
	requestURI="actor/administrator/list.do" pagesize="5"
	class="displaytag">

	<display:column property="name" titleKey="actor.name" />
	<display:column property="email" titleKey="actor.email" />
	<display:column property="phone" titleKey="actor.phone" />
	<display:column property="adress" titleKey="actor.adress" />



</display:table>