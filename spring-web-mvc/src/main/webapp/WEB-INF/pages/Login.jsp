<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checkout</title>
</head>
<body>

<SCRIPT LANGUAGE="JavaScript">
 var loginMsg = ${msg};
 if(loginMsg.length>0 && loginMsg!="null"){
	 alert(loginMsg);
	 }

 function validateUserDetails()
 {
     var userName = document.userDetails.userName.value;
     var password = document.userDetails.password.value;
    
     if (userName == "") {
         alert("User Name can not be empty.");                 
         return false;
                       
     } else  if (password == "") {
         alert("Password can not be empty.");                  
         return false;
     }                 
     else
     {
       document.userDetails.submit();
     } 
 }
 
 </SCRIPT>


<form:form action="loginRead" method="POST" modelAttribute="userDetails">
 Username:<form:input path="userName" /><br/> 
 Password:<form:input path="password" /><br/> 
			

			<input type="submit" value="Login" onClick="return validateUserDetails();" />
			</form:form>
			
		<form method="POST" action="newUser">



			<input type="submit" value="Register Here" />
			</form>

</body>
</html>