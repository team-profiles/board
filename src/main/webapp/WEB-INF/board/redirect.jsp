<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    

<c:set var="msg" value="${board_msg}" />
<c:set var="url" value="${board_url}" />
<c:if test="${msg != null || url != null}">
    <script>
        alert("${msg}");  // Display the value of msg in the alert
        location.href="${pageContext.request.contextPath}${board_url}";  // Uncomment if you want to navigate to the URL
    </script>
</c:if>
