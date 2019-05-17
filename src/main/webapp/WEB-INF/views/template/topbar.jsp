<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

    <!-- Sidebar Toggle (Topbar) -->
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>

    <!-- Topbar Navbar -->
    <ul class="navbar-nav ml-auto">

        <div class="topbar-divider d-none d-sm-block"></div>

        <!-- Nav Item - User Information -->
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                                <sec:authorize access="isAuthenticated()">
                                    <sec:authentication property="principal.username" var="username"/>
                                </sec:authorize>
                                <sec:authorize access="isAnonymous()">
                                    <s:message code="topbar.username" />
                                </sec:authorize>
                            </span>
                <img class="img-profile rounded-circle" src="https://source.unsplash.com/WqPAETBU2G8/60x60">
            </a>
            <!-- Dropdown - User Information -->
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                 aria-labelledby="userDropdown">
                <sec:authorize access="isAuthenticated()">
                    <a class="dropdown-item" href="#">
                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                        <s:message code="topbar.profile" />
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        <s:message code="topbar.logout"/>
                    </a>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <s:url value="/login" var="loginURL"/>
                    <a class="dropdown-item" href="${loginURL}">
                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                        <s:message code="topbar.login"/>
                    </a>
                    <div class="dropdown-divider"></div>
                    <s:url value="/register" var="registerURL"/>
                    <a class="dropdown-item" href="${registerURL}">
                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                        <s:message code="topbar.register"/>
                    </a>
                </sec:authorize>
            </div>
        </li>

    </ul>

</nav>