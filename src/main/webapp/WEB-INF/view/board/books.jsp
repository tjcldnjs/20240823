<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div>
        <c:forEach var="coverUrl" items="${coverImageUrls}">
            <div>
                <img src="${coverUrl}" alt="Book Cover" style="width: 200px; height: auto;" />
            </div>
        </c:forEach>
    </div>
</body>
</html>