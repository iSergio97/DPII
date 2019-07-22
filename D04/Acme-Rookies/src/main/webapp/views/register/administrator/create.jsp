<%--
 * register/rookie/create.jsp
 *
 * Copyright (C) 2019 Group 16 Desing & Testing II
 * @author José Antonio Domínguez Gómez
 --%>
<<<<<<< HEAD
=======
 
  <script type="text/javascript">
      function test() {
          //let patron = "[(][+][0-9]{2}[)] [0-9]{3} [0-9]{6,}";
          if(!document.getElementById("phoneNumber").value.includes("(+34)")){
              return !confirm("Your phone number dont have prefix. Do you want to add one? \n Tu teléfono no tiene prefijo de zona. ¿Quiere añadir uno?");
          }
        }
    </script>
>>>>>>> develop

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="administrator" action="register/administrator/edit.do">

	<form:hidden path="id"/>
	
	<%-- Campos de usuario --%>
	<acme:register code="name" path="name"/>
	<acme:register code="surnames" path="surnames"/>
	<acme:register code="vat" path="vat"/>
	<acme:register code="email" path="email"/>
	<acme:registerWithPlaceholder code="photo" path="photo" placeholder="https://imgur.com/r/panda/vhGv9Z0"/>
		<acme:registerWithPlaceholder code="phoneNumber" path="phoneNumber" placeholder="+XX (YYY) ZZZZZZ"/>
	<acme:register code="address" path="address"/>
	<acme:register code="username" path="username"/>
	<acme:password code="password" path="password"/>
	<acme:password code="confirmPassword" path="confirmPassword"/>
	
	<%-- Campos de tarjeta de crédito --%>
	<acme:register code="holder" path="holder"/>
	<acme:register code="brand" path="brand"/>
	<acme:register code="number" path="number"/>
	<acme:register code="expirationMonth" path="expirationMonth"/>
	<acme:register code="expirationYear" path="expirationYear"/>
	<acme:register code="cvv" path="CVV"/>
	
	<acme:submit name="save" code="save"/>
	<acme:cancel url="welcome/index.do" code="cancel"/>

</form:form>