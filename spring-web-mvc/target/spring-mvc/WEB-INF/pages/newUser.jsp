<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div align="center">
			
			<form:form action="registrationSuccess" method="POST" modelAttribute="user">
			<br> username <form:input path="username" /><br />
			<br> password <form:input path="password" /><br />
			<br> firstname <form:input path="firstname" /><br />
			<br> lastname <form:input path="lastname" /><br />
			<br> age <form:input path="age" /><br />
			<br> email <form:input path="email" /><br />
			<br> gender<form:input path="gender" /><br />
			<br> phone <form:input path="phone" /><br />

			<input type="submit" value="Register" />
			</form:form>

		</div>
</body>
</html>