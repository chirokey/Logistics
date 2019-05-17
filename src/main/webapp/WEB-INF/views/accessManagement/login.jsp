<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<s:message code="login.title" var="loginTitleMsg"/>
<c:import url="../template/header.jsp">
  <c:param name="title" value="${loginTitleMsg}"/>
</c:import>

<body class="bg-gradient-primary">

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4"><s:message code="login.greeting"/></h1>
                  </div>
                  <s:url value="/login" var="loginProcessingUrl"/>
                  <form method="post" action="${loginProcessingUrl}" class="user">
                    <div class="form-group">
                      <input type="text" id="username" name="username" class="form-control form-control-user"/>
                    </div>
                    <div class="form-group">
                      <input type="password" id="password" name="password" class="form-control form-control-user"/>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" name="submit" class="btn btn-primary btn-user btn-block"/>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>

  <c:import url="../template/footerScripts.jsp"/>

</body>

</html>
