<%-- 
    Document   : detail-user
    Created on : Aug 7, 2023, 9:39:48 AM
    Author     : vuongthai1205
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>${user.id}</h1>
<c:url value="/detail-user" var="action" />
<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <form:hidden path="id" />
    <form:hidden path="avatar" />
    <form:hidden path="password" />
    <form:hidden path="dateOfBirth"/>
    <form:hidden path="gender" />
    <form:hidden path="address" />
    <form:hidden path="createAt"/>
    <form:errors path="*" element="div" /> 
    <div class="form-floating mb-3 mt-3">
        <label for="name">User Name</label>
        <form:input type="text" class="form-control" 
                    path="username" placeholder="Tieu de bai viet" />
        <form:errors path="username" cssClass="text-danger" />
    </div>
    <div class="form-floating">
        <label for="des">First Name</label>
        <form:input type="text" class="form-control" 
                    path="firstName" placeholder="Tieu de bai viet" />
    </div>
    <div class="form-floating">
        <label for="des">Last Name</label>
        <form:input type="text" class="form-control" 
                    path="LastName" placeholder="Tieu de bai viet" />
    </div>
    <div class="form-floating">
        <label for="des">Phone</label>
        <form:input type="phone" class="form-control" 
                    path="phone" placeholder="Tieu de bai viet" />
    </div>
    <div class="form-floating">
        <label for="des">Email</label>
        <form:input type="email" class="form-control" 
                    path="email" placeholder="Tieu de bai viet" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <label for="file">Avatar</label>
        <form:input type="file" class="form-control" 
                    path="file" id="file"  />
        
        <c:if test="${user.avatar != null}">
            <img src="${user.avatar}" width="120" />
        </c:if>
    </div>
    <div class="form-floating mb-3 mt-3">
        

        <label for="auctionStatus" class="form-label">User Role</label>
    </div>

    <div class="form-floating mb-3 mt-3">
        <button class="btn btn-info" type="submit">
            Update User
        </button>
    </div>

</form:form>