<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="categories" id="row"
	requestURI="fixUpTaskCategory/administrator/list.do" pagesize="5"
	class="displaytag">
	
	<display:column property="name" titleKey="fixUpTaskCategory.name" />
	<display:column> <a href="fixUpTaskCategory/administrator/show.do?fixUpTaskCategoryId=${row.id}"> <jstl:out value="Show"/></a> </display:column>
	
</display:table>

<a href="fixUpTaskCategory/administrator/create.do">New</a>