<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<table
        class="table table-bordered"
        id="dataTable"
        width="100%" cellspacing="0">
    <thead>
    <tr>
        <th><s:message code="driver.username"/></th>
        <th><s:message code="driver.firstName"/></th>
        <th><s:message code="driver.lastName"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:input path="username"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      minlength="2" maxlength="20" required="1"/>
            <sf:errors path="username"/>
        </td>
        <td>
            <sf:input path="firstName"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      minlength="2" maxlength="30" required="1"/>
            <sf:errors path="firstName"/>
        </td>
        <td>
            <sf:input path="lastName"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      minlength="2" maxlength="30" required="1"/>
            <sf:errors path="lastName"/>
        </td>
    </tr>
    </tbody>

    <thead>
    <tr>
        <th><s:message code="driver.password"/></th>
        <th><s:message code="driver.matchPassword"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:password path="password"
                         cssClass="form-control"
                         cssErrorClass="form-control is-invalid"
                         minlength="5" maxlength="40"/>
            <sf:errors path="password"/>
        </td>
        <td>
            <sf:password path="matchingPassword"
                         cssClass="form-control" cssErrorClass="form-control is-invalid"
                         minlength="5" maxlength="40"/>
            <sf:errors path="matchingPassword"/>
        </td>
    </tr>
    </tbody>

    <thead>
    <tr>
        <th><s:message code="driver.personalNumber" /></th>
        <th><s:message code="driver.monthWorkedHours"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:input path="personalNumber" type="number"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      min="0" max="99999"
                      required="1"/>
            <sf:errors path="personalNumber"/>
        </td>
        <td>
            <sf:input path="monthWorkedHours" type="number"
                      cssClass="form-control" cssErrorClass="form-control is-invalid"
                      min="0" max="999"
                      required="1"/>
            <sf:errors path="monthWorkedHours"/>
        </td>
    </tr>
    </tbody>

    <thead>
    <tr>
        <th><s:message code="driver.status"/></th>
        <th><s:message code="driver.curCity"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:select path="status" items="${driverStatusList}"
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

    <thead>
    <tr>
        <th><s:message code="driver.curTruck"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <sf:select path="currentTruck" cssClass="form-control" cssErrorClass="form-control is-invalid">
                <sf:option value="none" label="none"/>
                <sf:options items="${trucksList}" itemLabel="regNumber" itemValue="regNumber"/>
            </sf:select>
            <sf:errors path="currentTruck"/>
        </td>
    </tr>
    </tbody>
</table>
