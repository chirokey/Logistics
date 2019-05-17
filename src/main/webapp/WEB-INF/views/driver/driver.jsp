<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<s:message code="driver.title" var="driverTitleMsg"/>
<c:import url="../template/header.jsp">
    <c:param name="title" value="${driverTitleMsg}"/>
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
                <!-- DataTales Example -->
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">${driverTitleMsg}</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table
                                    class="table table-bordered"
                                    id="dataTable"
                                    width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th><s:message code="driver.username"/></th>
                                    <th><s:message code="driver.firstName"/></th>
                                    <th><s:message code="driver.lastName"/></th>
                                    <th><s:message code="driver.personalNumber"/></th>
                                    <th><s:message code="driver.status"/></th>
                                    <th><s:message code="driver.curCity"/></th>
                                    <th><s:message code="driver.curTruck"/></th>
                                    <th><s:message code="driver.delete"/></th>
                                    <th><s:message code="driver.edit"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="driver" items="${drivers}">
                                    <tr>
                                        <td>${driver.username}</td>
                                        <td>${driver.firstName}</td>
                                        <td>${driver.lastName}</td>
                                        <td>${driver.personalNumber}</td>
                                        <td>${driver.status}</td>
                                        <td>${driver.currentCity}</td>
                                        <td>${driver.currentTruck}</td>
                                        <td>
                                            <s:url value="/drivers/delete" var="delDriverURL"/>
                                            <sf:form action="${delDriverURL}" method="post">
                                                <input type="hidden" name="driverID" value="${driver.id}"/>
                                                <input type="submit" value="Delete"
                                                       class="btn btn-primary btn-user btn-block"/>
                                            </sf:form>
                                        </td>
                                        <td>
                                            <s:url value="/drivers/edit" var="editDriverURL"/>
                                            <sf:form action="${editDriverURL}" method="get">
                                                <input type="hidden" name="driverID" value="${driver.id}"/>
                                                <input type="submit" value="Edit"
                                                       class="btn btn-primary btn-user btn-block"/>
                                            </sf:form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <c:import url="../template/pagination.jsp">
                                <c:param name="currentPage" value="${currentPage}"/>
                                <c:param name="url" value="/drivers"/>
                                <c:param name="numberOfPages" value="${numberOfPages}"/>
                            </c:import>
                        </div>
                    </div>
                </div>

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