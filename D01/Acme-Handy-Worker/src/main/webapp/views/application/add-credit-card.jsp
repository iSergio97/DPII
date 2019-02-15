<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="creditCard" action="application/add-credit-card.do">
	<form:label path="holder">
		<strong><spring:message code="customer.creditCard.holder"/>:</strong>
	</form:label>
	<form:input path="holder"/>
	<form:errors cssClass="error" path="holder" />
	<br/><br/>
	
	<form:label path="brand">
		<strong><spring:message code="customer.creditCard.brand"/>:</strong>
	</form:label>
	<form:select id="brands" path="brand">
		<form:options items="${brands}"/>
		<form:option value="" label="----" />
	</form:select>
	<form:errors cssClass="error" path="brand" />
	<br/><br/>
	
	<form:label path="number">
		<strong><spring:message code="customer.creditCard.number"/>:</strong>
	</form:label>
	<form:input path="number"/>
	<form:errors cssClass="error" path="number" />
	<br/><br/>
	
	<form:label path="expirationMonth">
		<strong><spring:message code="customer.creditCard.expirationMonth"/>:</strong>
	</form:label>
	<form:input path="expirationMonth"/>
	<form:errors cssClass="error" path="expirationMonth" />
	<br/><br/>
	
	<form:label path="expirationYear">
		<strong><spring:message code="customer.creditCard.expirationYear"/>:</strong>
	</form:label>
	<form:input path="expirationYear"/>
	<form:errors cssClass="error" path="expirationYear" />
	<br/><br/>
	
	<form:label path="CVV">
		<strong><spring:message code="customer.creditCard.CVV"/>:</strong>
	</form:label>
	<form:input path="CVV"/>
	<form:errors cssClass="error" path="CVV" />
	<br/><br/>
	
	<input type="submit" name="submit" value="<spring:message code="options.submit" />" />
	<input type="button" name="cancel" value="<spring:message code="options.cancel" />"
			onclick="javascript:relativeRedir('application/customer.do');" />
</form:form>
