<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form modelAttribute="fixUpTaskCategory" action="fixUpTaskCategory/administrator/edit.do">

<!-- Hidden Fields -->
<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="fixUpTaskCategoryChildren"/>


<!-- Editable Fields -->


<form:label path="name"><spring:message code="fixUpTaskCategory.name" /></form:label>
<form:input path="name" />
<form:errors cssClass="error" path="name"></form:errors>
<br/>

<spring:message code="fixUpTaskCategory.fixUpTaskCategoryParent"/>
<form:select id="parents" path="fixUpTaskCategoryParent">
<form:options items="${parents}" itemLabel = "name" itemValue="id"/>
<form:option label="----" value="0" />
</form:select>

<!--  Control  -->
<br/>
<input type="submit" name="save" value="<spring:message code="fixUpTaskCategory.save" />" />
<input type="button" name="cancel" value ="<spring:message code="security.cancel"/>" onclick="javascript:RelativeRedir('/fixUpTaskCategory/administrator/list.do');"/>

</form:form>