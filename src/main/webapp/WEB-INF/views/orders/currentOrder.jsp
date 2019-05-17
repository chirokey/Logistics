<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<s:message code="order.title" var="orderTitleMsg"/>
<c:import url="../template/header.jsp">
    <c:param name="title" value="${orderTitleMsg}"/>
</c:import>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <c:import url="../template/sidebar.jsp"/>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <c:import url="../template/topbar.jsp"/>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">
                <c:if test="${step1}">
                    <c:import url="addCargo.jsp"/>
                    <s:url value="/orders/add/step2" var="goToStep2URL"/>
                    <sf:form action="${goToStep2URL}" method="post">
                        <input type="submit" value="Next" class="btn btn-primary btn-user btn-block"/>
                    </sf:form>
                </c:if>

                <c:if test="${step2}">
                    <c:import url="addTruck.jsp"/>
                </c:if>

                <c:if test="${step3}">
                    <c:import url="addDriver.jsp"/>
                </c:if>

                <c:if test="${not step1 and not step2 and not step3}">
                    <c:import url="orderDescription.jsp">
                        <c:param name="orderID" value="${orderID}"/>
                    </c:import>
                </c:if>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <c:import url="../template/footer.jsp"/>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<c:import url="../accessManagement/logoutModal.jsp"/>

<c:import url="../template/footerScripts.jsp"/>

</body>

</html>
