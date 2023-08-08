<%-- 
    Document   : user-manager
    Created on : Aug 7, 2023, 7:56:12 AM
    Author     : vuongthai1205
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>



<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">User manager</h6>
        <a href="<c:url value="/add-user"/>">Add user</a>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Id User</th>
                        <th>User name</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Date Of birth</th>
                        <th>Avatar</th>
                        <th>Gender</th>
                        <th>Address</th>
                        <th>Ngay tao</th>
                        <th>Ngay cap nhat</th>
                        <th>Hanh dong</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.userName}"/></td>
                            <td><c:out value="${user.firstName}"/></td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.dateOfBirth}"/></td>
                            <td><img width="200" alt="${user.userName}" src="<c:out value="${user.avatar}"/>"/>  </td>
                            <td>
                                <c:choose>
                                    
                                    <c:when test="${user.gender == null}">
                                        None
                                    </c:when>
                                    <c:when test="${user.gender == 1}">
                                        Male
                                    </c:when>
                                    <c:otherwise>
                                        Female
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${user.address}"/></td>
                            <td><c:out value="${user.createAt}"/></td>
                            <td><c:out value="${user.updateAt}"/></td>
                            <td>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <c:url value="/api/user/${user.id}" var="apiDel"/>
                                    <a href="<c:url value="/detail-user/${user.id}"/>">Cap nhat</a>
                                    <button class="btn btn-danger" onclick="delPro('${apiDel}', ${user.id})">Xóa</button>
                                </sec:authorize> 
                                <sec:authorize access="hasRole('ROLE_MEMBER')">
                                    Ban khong co quyen sua hoac xoa
                                </sec:authorize> 
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
    <script src="<c:url value="/js/main.js" />"></script>