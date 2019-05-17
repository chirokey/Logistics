<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-4">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">${wpTitleMsg}</h6>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <s:url value="/orders/add/finishOrder" var="addDriver"/>
                    <form action="${addDriver}" method="post">
                        <c:forEach begin="1" end="${numberOfDrivers}" varStatus="loop">
                            <select id="driverID_${loop.index}" name="driverID" class="form-control">
                                <c:forEach var="driver" items="${drivers}">
                                    <option value="${driver.id}">${driver.username}</option>
                                </c:forEach>
                            </select>
                            <br/>
                        </c:forEach>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Next" class="btn btn-primary btn-user btn-block"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
