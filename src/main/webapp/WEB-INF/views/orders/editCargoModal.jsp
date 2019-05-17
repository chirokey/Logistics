<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Edit Cargo Modal-->
<div class="modal fade" id="editCargoModal" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editCargoModalLabel"><s:message code="all.edit"/></h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><s:message code="order.close"/></span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editCargoForm" name="editCargoForm" method="post">
                    <div class="form-group">
                        <label for="cargoName"><s:message code="cg.name"/></label>
                        <input id="cargoName"
                               type="text" name="name"
                               minlength="1"
                               maxlength="255"
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
                    <input type="submit" value="<s:message code="all.edit"/>" class="btn btn-primary btn-user btn-block"/>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">
                    <s:message code="order.cancel"/>
                </button>
            </div>
        </div>
    </div>
</div>
