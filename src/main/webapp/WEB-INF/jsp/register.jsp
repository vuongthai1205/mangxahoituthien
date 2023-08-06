<%-- 
    Document   : register
    Created on : Aug 6, 2023, 3:28:33 PM
    Author     : vuongthai1205
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
                        </div>
                        <c:url value="/register" var="action" />
                        <form:form enctype="multipart/form-data" class="user" action="${action}" method="post" modelAttribute="user">
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    
                                    <form:input type="text" class="form-control form-control-user"
                                                path="firstName" placeholder="First Name" />
                                </div>
                                <div class="col-sm-6">
                                    <form:input type="text" class="form-control form-control-user"
                                                path="lastName" placeholder="Last Name" />
                                </div>
                            </div>
                            <div class="form-group">
                                <form:input type="phone" class="form-control form-control-user" 
                                                path="phone" placeholder="Phone" />
                            </div>
                            <div class="form-group">
                                
                                <form:input type="text" class="form-control form-control-user"
                                                path="userName" placeholder="Username" />
                            </div>
                            <div class="form-group">
                                <label>Anh dai dien</label>
                                <form:input type="file" class="form-control" path="file" id="file"  />
                            </div>
                            
                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <form:input type="password" class="form-control form-control-user"
                                                path="password" placeholder="Password" />
                                </div>
                                <div class="col-sm-6">
                                    <form:input type="password" class="form-control form-control-user"
                                                path="repeatPassword" placeholder="Password repeat" />
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary btn-user btn-block">
                                Register Account
                            </button>
                        </form:form>
                        <c:if test="${errMsg != null}">
                            <h1>${errMsg}</h1>
                        </c:if>
                        <hr>
                        <div class="text-center">
                            <a class="small" href="<c:url value="/login"/>">Already have an account? Login!</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
