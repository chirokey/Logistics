<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<s:message code="register.title" var="regTitleMsg"/>
<c:import url="../template/header.jsp">
  <c:param name="title" value="${regTitleMsg}"/>
</c:import>

<body class="bg-gradient-primary">

  <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4"><s:message code="register.msg" /></h1>
              </div>
              <sf:form method="post" modelAttribute="user" cssClass="user">
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <sf:input path="firstName" cssClass="form-control form-control-user" placeholder="First Name"/>
                    <sf:errors path="firstName" />
                  </div>
                  <div class="col-sm-6">
                    <sf:input path="lastName" cssClass="form-control form-control-user" placeholder="Last Name"/>
                    <sf:errors path="lastName" />
                  </div>
                </div>
                <div class="form-group">
                  <sf:input path="username" cssClass="form-control form-control-user" placeholder="Username"/>
                  <sf:errors path="username" />
                </div>
                <div class="form-group">
                  <sf:input path="email" type="email" cssClass="form-control form-control-user" placeholder="Email Address"/>
                  <sf:errors path="email" />
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <sf:password path="password" cssClass="form-control form-control-user" placeholder="Password"/>
                    <sf:errors path="password" />
                  </div>
                  <div class="col-sm-6">
                    <sf:password path="matchingPassword" cssClass="form-control form-control-user" placeholder="Repeat Password"/>
                    <sf:errors path="matchingPassword" />
                  </div>
                </div>
                <input type="submit" value="Register" class="btn btn-primary btn-user btn-block">
              </sf:form>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

  <c:import url="../template/footerScripts.jsp"/>

</body>

</html>
