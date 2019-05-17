<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<table
        class="table table-bordered"
        id="dataTable"
        width="100%" cellspacing="0">
    <thead>
    <tr>
        <th><s:message code="truck.regNumber.letters"/></th>
        <th><s:message code="truck.regNumber.digits"/></th>
        <th><s:message code="truck.driverNum"/></th>
        <th><s:message code="truck.weightCap"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:input path="regNumberLetters"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      minlength="2" maxlength="2" required="1"/>
            <sf:errors path="regNumberLetters"/>
        </td>
        <td>
            <sf:input path="regNumberDigits" type="number"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      max="99999"
                      required="1"/>
            <sf:errors path="regNumberDigits"/>
        </td>
        <td>
            <sf:input path="numberOfDrivers" type="number"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      min="1" max="3"
                      required="1"/>
            <sf:errors path="numberOfDrivers"/>
        </td>
        <td>
            <sf:input path="weightCapacity" type="number"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      min="10" max="24"
                      required="1"/>
            <sf:errors path="weightCapacity"/>
        </td>
    </tr>

    </tbody>
    <thead>
    <tr>
        <th><s:message code="truck.status"/></th>
        <th><s:message code="truck.curCity"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:select path="status" items="${truckStatusList}"
                       cssClass="form-control" cssErrorClass="form-control is-invalid"/>
            <sf:errors path="status"/>
        </td>
        <td>
            <sf:select path="currentCity" items="${citiesList}"
                       cssClass="form-control" cssErrorClass="form-control is-invalid"/>
            <sf:errors path="currentCity"/>
        </td>
    </tr>
    </tbody>
</table>
