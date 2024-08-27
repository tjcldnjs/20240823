<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Book Grid</title>
<style>
.container {
    display: grid;
    grid-template-columns: repeat(6, 1fr); /* 6 items per row */
    gap: 10px;
    padding: 20px;
    width: 80%; /* Adjust width as needed */
    justify-content: center; /* Center the grid horizontally */
    margin: 0 auto; /* Center the grid container horizontally */
}

.book-item {
    text-align: center;
    border: 1px solid #ddd;
    padding: 10px;
    border-radius: 5px;
    box-sizing: border-box;
    /* Ensure padding and border are included in width */
}

.book-item img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
}

.book-title {
    font-size: 14px;
    margin-top: 10px;
}

.book-author {
    font-size: 12px;
    color: #666;
}

.pagination {
    margin-top: 20px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 5px;
}

.pagination a {
    padding: 8px 15px;
    text-decoration: none;
    border: 1px solid #ddd;
    border-radius: 5px;
    color: #333;
    font-size: 14px;
    transition: background-color 0.3s, color 0.3s;
}

.pagination a.active {
    background-color: #333;
    color: white;
}

.pagination a.disabled {
    color: #ddd;
    border-color: #ddd;
    cursor: not-allowed;
}

.pagination .dots {
    padding: 8px 15px;
    font-size: 14px;
    color: #333;
}

.pagination .page-numbers {
    display: flex;
    gap: 5px;
}


</style>
</head>
<body>

	 <div class="container">
        <c:forEach var="title" items="${titles}" varStatus="status">
            <div class="book-item">
                <img src="${images[status.index]}" alt="${title}">
                <div class="book-title">${title}</div>
                <div class="book-author">${authors[status.index]}</div>
            </div>
        </c:forEach>
        
        <c:forEach var="item" items="${items}">
            <div class="book-item">
                <img src="${item.cover}" alt="${item.title}" />
                <div class="book-title">${item.title}</div>
                <div class="book-author">${item.author}</div>
            </div>
        </c:forEach>
    </div>

    <!-- Pagination numbers -->
    <div class="pagination">
        <!-- "맨앞으로 가기" 버튼 -->
        <c:choose>
            <c:when test="${currentPage > 1}">
                <a href="?page=1&size=${param.size}">맨앞으로 가기</a>
            </c:when>
            <c:otherwise>
                <a class="disabled">맨앞으로 가기</a>
            </c:otherwise>
        </c:choose>
        
        <!-- 페이지 번호들 -->
        <c:set var="startPage" value="${currentPage - 2}"/>
        <c:set var="endPage" value="${currentPage + 2}"/>
        
        <c:if test="${startPage < 1}">
            <c:set var="endPage" value="${endPage + (1 - startPage)}"/>
            <c:set var="startPage" value="1"/>
        </c:if>
        
        <c:if test="${endPage > totalPages}">
            <c:set var="startPage" value="${startPage - (endPage - totalPages)}"/>
            <c:set var="endPage" value="${totalPages}"/>
        </c:if>

        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <a href="?page=${i}&size=${param.size}" class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>

        <!-- "맨뒤로 가기" 버튼 -->
        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <a href="?page=${totalPages}&size=${param.size}">맨뒤로 가기</a>
            </c:when>
            <c:otherwise>
                <a class="disabled">맨뒤로 가기</a>
            </c:otherwise>
        </c:choose>
    </div>


</body>
</html>
