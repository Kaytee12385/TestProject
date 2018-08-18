<%@ page import="com.training.springmvc.model.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order status</title>
</head>
<body>

Your order has been successfully placed.<br>
Order Id = <c:out value="${orderId}"></c:out>



 <form method="POST" action="home">
   <input type="submit" value="Go to home" />
 </form>
</body>
</html>