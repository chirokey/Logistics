<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3"><s:message code="sidebar.title"/></div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Heading -->

    <sec:authorize access="isAnonymous()">
        <div id="anon-menu">
            <!-- Nav Item - Order -->
            <s:url value="/login" var="loginURL"/>
            <li class="nav-item">
                <a class="nav-link" href="${loginURL}">
                    <i class="fas fa-fw fa-table"></i>
                    <span><s:message code="sidebar.login"/></span></a>
            </li>
            <!-- Nav Item - Order -->
            <s:url value="/register" var="registerURL"/>
            <li class="nav-item">
                <a class="nav-link" href="${registerURL}">
                    <i class="fas fa-fw fa-table"></i>
                    <span><s:message code="sidebar.register"/> </span></a>
            </li>
        </div>
    </sec:authorize>

    <sec:authorize access="hasAuthority('ROLE_ADMIN')">
        <div id="admin-menu">
            <sec:authorize url="/orders/">
                <!-- Nav Item - Orders -->


                <s:url value="/orders/" var="ordersURL"/>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOrder" aria-expanded="true" aria-controls="collapseOrder">
                        <i class="fas fa-fw fa-cog"></i>
                        <span><s:message code="sidebar.orders"/></span>
                    </a>
                    <div id="collapseOrder" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${ordersURL}"><s:message code="sidebar.orders.showall"/></a>
                            <s:url value="/orders/add" var="addOrderURL"/>
                            <a class="collapse-item" href="${addOrderURL}"><s:message code="sidebar.orders.add"/></a>
                        </div>
                    </div>
                </li>
            </sec:authorize>

            <sec:authorize url="/drivers/">
                <!-- Nav Item - Drivers -->
                <s:url value="/drivers/" var="driversURL"/>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseDriver" aria-expanded="true" aria-controls="collapseDriver">
                        <i class="fas fa-fw fa-cog"></i>
                        <span><s:message code="sidebar.drivers"/></span>
                    </a>
                    <div id="collapseDriver" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${driversURL}"><s:message code="sidebar.drivers.showall"/></a>
                            <s:url value="/drivers/add" var="addDriverURL"/>
                            <a class="collapse-item" href="${addDriverURL}"><s:message code="sidebar.drivers.add"/></a>
                        </div>
                    </div>
                </li>


            </sec:authorize>

            <sec:authorize url="/trucks/">
                <!-- Nav Item - Trucks -->
                <s:url value="/trucks/" var="trucksURL"/>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                        <i class="fas fa-fw fa-cog"></i>
                        <span><s:message code="sidebar.trucks"/></span>
                    </a>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${trucksURL}"><s:message code="sidebar.trucks.showall"/></a>
                            <s:url value="/trucks/add" var="addTruckURL"/>
                            <a class="collapse-item" href="${addTruckURL}"><s:message code="sidebar.trucks.add"/></a>
                        </div>
                    </div>
                </li>
            </sec:authorize>
        </div>
    </sec:authorize>

    <sec:authorize access="hasAuthority('ROLE_DRIVER')">
        <div id="driver-menu">
            <sec:authorize url="/driver/">
                <!-- Nav Item - Order -->
                <s:url value="/driver/" var="driverURL"/>
                <li class="nav-item">
                    <a class="nav-link" href="${driverURL}">
                        <i class="fas fa-fw fa-table"></i>
                        <span><s:message code="sidebar.driver.order" /></span></a>
                </li>
            </sec:authorize>
        </div>
    </sec:authorize>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>
