<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<s:message code="truck.title" var="truckTitleMsg"/>
<c:import url="../template/header.jsp">
    <c:param name="title" value="${truckTitleMsg}"/>
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
                        <h6 class="m-0 font-weight-bold text-primary">${truckTitleMsg}</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table
                                    class="table table-bordered"
                                    id="dataTable"
                                    width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th><s:message code="truck.regNumber"/></th>
                                    <th><s:message code="truck.driverNum"/></th>
                                    <th><s:message code="truck.weightCap"/></th>
                                    <th><s:message code="truck.status"/></th>
                                    <th><s:message code="truck.curCity"/></th>
                                    <th><s:message code="truck.delete"/></th>
                                    <th><s:message code="truck.edit"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="truck" items="${trucks}">
                                    <tr>
                                        <td>${truck.regNumberLetters} ${truck.regNumberDigits}</td>
                                        <td>${truck.numberOfDrivers}</td>
                                        <td>${truck.weightCapacity}</td>
                                        <td>${truck.status}</td>
                                        <td>${truck.currentCity}</td>
                                        <td>
                                            <s:url value="/trucks/delete" var="delTruckURL"/>
                                            <sf:form action="${delTruckURL}" method="post">
                                                <input type="hidden" name="truckID" value="${truck.id}"/>
                                                <input type="submit" value="Delete"
                                                       class="btn btn-primary btn-user btn-block"/>
                                            </sf:form>
                                        </td>
                                        <td>
                                            <s:url value="/trucks/edit" var="editTruckURL"/>
                                            <sf:form action="${editTruckURL}" method="get">
                                                <input type="hidden" name="truckID" value="${truck.id}"/>
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
                                <c:param name="url" value="/trucks"/>
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