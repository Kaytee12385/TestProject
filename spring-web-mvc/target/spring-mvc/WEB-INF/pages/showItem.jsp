<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h1>Item Values</h1>

		<table border="1">

			<th>Item id</th>
			<th>Item name</th>
			<th>Item price</th>
			<th>Item desc</th>
			<th>Item on sale</th>
			<th>Item discount</th>

			<c:forEach var="entry" items="${item}">
				<tr>

					<td>${entry.ITEM_ID}</td>
					<td>${entry.ITEM_NAME}</td>
					<td>${entry.ITEM_PRICE}</td>
					<td>${entry.ITEM_DESC}</td>
					<td>${entry.ITEM_IS_ON_SALE}</td>
					<td>${entry.ITEM_DISCOUNT}</td>



				</tr>
			</c:forEach>
		</table>
		<c:forEach var="entry" items="${item}">
		<form:form action="addToCart" method="GET">
			<input type="hidden" name="itemId" value="${entry.ITEM_ID}"
				size="25" /><br /> <input type="submit" value="Add To Cart" />
			
			</form:form>
				</c:forEach>
</body>
</html>