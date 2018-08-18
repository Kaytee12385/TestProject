<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
</head>
<body>
<div align="center">
		<h1>Item List</h1>
		
		<table border="1">

			<th>Item id</th>
			<th>Item name</th>
			<th>Item price</th>
			<th>Item desc</th>
			<th>Item on sale</th>
			<th>Item discount</th>
			
			<c:forEach var="entry" items="${cartItems}">
				<tr>

					<td>${entry.value.ITEM_ID}</td>
					<td>${entry.value.ITEM_NAME}</td>
					<td>${entry.value.ITEM_PRICE}</td>
					<td>${entry.value.ITEM_DESC}</td>
					<td>${entry.value.ITEM_IS_ON_SALE}</td>
					<td>${entry.value.ITEM_DISCOUNT}</td>
					
					
					
				</tr>
			</c:forEach>
		</table>
		
	</div>
<form method="POST" action="Login">
		<div align="center"><br><br><br><br><br><br><br><br><br><br><br>
  
 
  <input type="submit" value="proceed to checkout" />
  
  </div>
	</form>


</body>
</html>