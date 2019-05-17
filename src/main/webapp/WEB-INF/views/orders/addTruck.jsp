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
                    <s:url value="/orders/add/step3" var="goToStep3URL"/>
                    <form action="${goToStep3URL}" method="post">
                        <label for="trucksForOrder"><s:message code="sidebar.trucks.add"/></label>
                        <select id="trucksForOrder" name="truckRegNumber" class="form-control">
                            <c:forEach var="truck" items="${trucksList}">
                                <option value="${truck.regNumberLetters} ${truck.regNumberDigits}">
                                        ${truck.regNumberLetters} ${truck.regNumberDigits}
                                </option>
                            </c:forEach>
                        </select>
                        <br/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Next" class="btn btn-primary btn-user btn-block"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
