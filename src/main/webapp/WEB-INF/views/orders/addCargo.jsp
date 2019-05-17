<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    <!-- Add cargo form -->
    <div class="col-lg-4">
        <div class="card shadow mb-4">
            <!-- Card Header - Accordion -->
            <a href="#addNewCargo" class="d-block card-header py-3" data-toggle="collapse"
               role="button" aria-expanded="true" aria-controls="addNewCargo">
                <h6 class="m-0 font-weight-bold text-primary">Add new cargo</h6>
            </a>
            <!-- Card Content - Collapse -->
            <div class="collapse show" id="addNewCargo">
                <div class="card-body">
                    <form id="addNewCargoForm" name="addNewCargoForm" method="post">
                        <div class="form-group">
                            <label for="cargoName"><s:message code="cg.name"/></label>
                            <input id="cargoName"
                                   type="text" name="name"
                                   minlength="1"
                                   maxlength="255"
                                   placeholder="<s:message code="cg.name"/>"
                                   class="form-control" required/>
                            <small id="cargoNameHelpBlock" class="form-text text-muted">
                                Not empty, min 1 and max 255 characters
                            </small>
                        </div>

                        <div class="form-group">
                            <label for="cargoWeight"><s:message code="cg.weight"/></label>
                            <input id="cargoWeight"
                                   type="number" name="weight"
                                   min="1" max="24000"
                                   placeholder="<s:message code="cg.weight"/>"
                                   class="form-control" required/>
                            <small id="cargoWeightHelpBlock" class="form-text text-muted">
                                Not empty, min 1 and max 24000
                            </small>
                        </div>

                        <div class="form-group">
                            <label for="cargoLoadCity"><s:message code="cg.load"/></label>
                            <select id="cargoLoadCity" name="loadingCity" class="form-control">
                                <c:forEach var="city" items="${citiesList}">
                                    <option value="${city}">${city}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="cargoUnLoadCity"><s:message code="cg.unload"/></label>
                            <select id="cargoUnLoadCity" name="unloadingCity" class="form-control">
                                <c:forEach var="city" items="${citiesList}">
                                    <option value="${city}">${city}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Add new cargo" class="btn btn-primary btn-user btn-block"/>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Cargo list -->
    <div class="col-lg-8">
        <div class="card shadow mb-4">
            <!-- Card Header - Accordion -->
            <a href="#cargoList" class="d-block card-header py-3" data-toggle="collapse"
               role="button" aria-expanded="true" aria-controls="cargoList">
                <h6 class="m-0 font-weight-bold text-primary">Cargo list</h6>
            </a>
            <!-- Card Content - Collapse -->
            <div class="collapse show" id="cargoList">
                <div class="card-body">
                    <div class="table-responsive">
                        <table
                                class="table table-bordered"
                                id="dataTable"
                                width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th><s:message code="cg.name"/></th>
                                <th><s:message code="cg.weight"/></th>
                                <th><s:message code="cg.load"/></th>
                                <th><s:message code="cg.unload"/></th>
                                <th><s:message code="all.delete"/></th>
                                <th><s:message code="all.edit"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="cargo" items="${cargoListOnOnePage}" varStatus="cargoLoopCount">
                                <tr id="cargo_${cargoLoopCount.count - 1}">
                                    <td id="cg_name_${cargoLoopCount.count - 1}">${cargo.name}</td>
                                    <td id="cg_weight_${cargoLoopCount.count - 1}">${cargo.weight}</td>
                                    <td id="cg_loadCity_${cargoLoopCount.count - 1}">${cargo.loadingCity}</td>
                                    <td id="cg_unloadCity_${cargoLoopCount.count - 1}">${cargo.unloadingCity}</td>
                                    <td>
                                        <s:url value="/orders/add/cargoList/delete" var="delCargoURL"/>
                                        <sf:form action="${delCargoURL}" method="post">
                                            <input type="hidden" name="id" value="${cargoLoopCount.count - 1}"/>
                                            <input type="submit" value="Delete"
                                                   class="btn btn-primary btn-user btn-block"/>
                                        </sf:form>
                                    </td>
                                    <td>
                                        <a class="dropdown-item" href="#" data-toggle="modal"
                                           data-target="#editCargoModal">
                                            <button onclick="onEditButtonClick(${cargoLoopCount.count - 1})"
                                                    type="button" class="btn btn-primary">Edit</button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <c:import url="editCargoModal.jsp"/>

                        <c:import url="../template/pagination.jsp">
                            <c:param name="currentPage" value="${currentPage}"/>
                            <c:param name="url" value="/orders/add"/>
                            <c:param name="numberOfPages" value="${numberOfPages}"/>
                        </c:import>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<s:url value="/resources/js/cargo.js" />"></script>
