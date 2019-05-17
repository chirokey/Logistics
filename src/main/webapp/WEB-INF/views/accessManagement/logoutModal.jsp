<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize access="isAuthenticated()">
    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><s:message code="logout.msg"/></h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><s:message code="logout.close"/></span>
                    </button>
                </div>
                <div class="modal-body"><s:message code="logout.action"/></div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal"><s:message code="logout.cancel"/></button>
                    <s:url value="/logout" var="logoutURL" />
                    <form action="${logoutURL}" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Logout" class="btn btn-primary btn-user btn-block"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>