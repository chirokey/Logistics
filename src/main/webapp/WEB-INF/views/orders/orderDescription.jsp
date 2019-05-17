<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-lg-8">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary"><s:message code="map"/></h6>
            </div>
            <div class="card-body">
                <div id="orderID" style="display: none">
                    ${param.orderID}
                </div>
                <div id="mapid">
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary"><s:message code="wp.title"/></h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table
                            class="table table-bordered"
                            id="dataTableWaypoints"
                            width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th><s:message code="wp.number"/></th>
                            <th><s:message code="wp.type"/></th>
                            <th><s:message code="wp.city"/></th>
                            <th><s:message code="wp.cargo"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="waypoint" items="${waypoints}">
                            <tr>
                                <td>${waypoint.waypointNumber}</td>
                                <td>${waypoint.waypointType}</td>
                                <td>${waypoint.city}</td>
                                <td>${waypoint.cargoName}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary"><s:message code="sidebar.cargo"/></h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table
                            class="table table-bordered"
                            id="dataTableCargo"
                            width="100%" cellspacing="0">
                        <thead>
                        <tr>
                            <th><s:message code="cg.name"/></th>
                            <th><s:message code="cg.weight"/></th>
                            <th><s:message code="cg.status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="cargo" items="${loads}">
                            <tr>
                                <td>${cargo.name}</td>
                                <td>${cargo.weight}</td>
                                <td>${cargo.status}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-4">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary"><s:message code="order.truck"/></h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table
                            class="table table-bordered"
                            id="dataTableTruck"
                            width="100%" cellspacing="0">
                        <tbody>
                        <tr>
                            <td>
                                <s:message code="truck.regNumber"/>
                            </td>
                            <td>
                                ${truck.regNumberLetters} ${truck.regNumberDigits}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="truck.driverNum"/>
                            </td>
                            <td>
                                ${truck.numberOfDrivers}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="truck.weightCap"/>
                            </td>
                            <td>
                                ${truck.weightCapacity}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="truck.status"/>
                            </td>
                            <td>
                                ${truck.status}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="truck.curCity"/>
                            </td>
                            <td>
                                ${truck.currentCity}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary"><s:message code="driver.title"/></h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table
                            class="table table-bordered"
                            id="dataTableDrivers"
                            width="100%" cellspacing="0">
                        <tbody>
                        <tr>
                            <td>
                                <s:message code="driver.username"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.username}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.firstName"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.firstName}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.lastName"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.lastName}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.personalNumber"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.personalNumber}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.status"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.status}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.curCity"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.currentCity}
                                </td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>
                                <s:message code="driver.curTruck"/>
                            </td>
                            <c:forEach var="driver" items="${drivers}">
                                <td>
                                        ${driver.currentTruck}
                                </td>
                            </c:forEach>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://unpkg.com/leaflet@1.4.0/dist/leaflet.js"
        integrity="sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg=="
        crossorigin=""></script>
<script src="<s:url value="/resources/js/map.js" />"></script>
