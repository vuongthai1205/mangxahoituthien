<%-- 
    Document   : detail-post
    Created on : Aug 4, 2023, 10:18:27 AM
    Author     : vuongthai1205
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>${post.id}</h1>
<c:url value="/detail-post" var="action" />
<form:form method="post" action="${action}" modelAttribute="post" enctype="multipart/form-data">
    <form:hidden path="id" />
    <form:hidden path="image" />
    <div class="form-floating mb-3 mt-3">
        <label for="name">Tieu de bai viet</label>
        <form:input type="text" class="form-control" 
                    path="title" id="title" placeholder="Tieu de bai viet" />
    </div>
    <div class="form-floating">
        <label for="des">Noi dung</label>
        <form:textarea class="form-control" id="content_post" name="text" 
                       path="content" placeholder="Noi dung"></form:textarea>
    </div>
    <div class="form-floating mb-3 mt-3">
        <label for="file">Anh bai viet</label>
        <form:input type="file" class="form-control" 
                    path="file" id="file"  />
        
        <c:if test="${post.image != null}">
            <img src="${post.image}" width="120" />
        </c:if>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-select" id="auctionStatus" name="auctionStatus" path="auctionStatus">
            <c:forEach items="${auctionStatuses}" var="a">
                <c:choose>
                    <c:when test="${a.id == post.auctionStatus.id}">
                        <option value="${a.id}" selected>${a.nameAuctionStatus}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${a.id}">${a.nameAuctionStatus}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </form:select>

        <label for="auctionStatus" class="form-label">Trang thai dau gia cua bai viet</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-info" type="submit">
            Cap nhat bai viet
        </button>
    </div>

</form:form>