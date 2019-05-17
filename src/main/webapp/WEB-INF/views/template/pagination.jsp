<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="currentPage" type="number" value="${param.currentPage}"/>
<fmt:parseNumber var="numberOfPages" type="number" value="${param.numberOfPages}"/>
<c:if test="${numberOfPages > 1}">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
                <%--For displaying Previous link except for the 1st page --%>
            <c:if test="${currentPage != 1}">
                <s:url value="${param.url}?page=${currentPage - 1}" var="prevPageURL"/>
                <li class="page-item">
                    <a class="page-link" href="${prevPageURL}">
                        <s:message code="pag.prev"/>
                    </a>
                </li>
            </c:if>
            <c:forEach begin="${currentPage}" end="${currentPage}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active">
                        <span class="page-link">
                                ${i}
                        </span>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <s:url value="${param.url}?page=${i}" var="pageURL"/>
                        <li class="page-item">
                            <a class="page-link" href="${pageURL}">
                                    ${i}
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
                <%--For displaying Next link --%>
            <c:if test="${currentPage < numberOfPages}">
                <s:url value="${param.url}?page=${currentPage + 1}" var="nextPageURL"/>
                <li class="page-item">
                    <a class="page-link" href="${nextPageURL}">
                        <s:message code="pag.next"/>
                    </a>
                </li>
            </c:if>
            <br/>
                <%--<li class="page-item">--%>
            <s:url value="${param.url}" var="currURL"/>
            <form action="${currURL}" method="get">
                <div class="row">
                    <div class="col" style="margin-left: 10px;">
                        <input type="number" name="page" class="form-control" placeholder="page"
                               style="width: 80px;"
                               min="1" max="${numberOfPages}"/>
                    </div>
                    <div class="col">
                        <input type="submit" value="Go" class="form-control"/>
                    </div>
                    <div class="col" style="padding-top: 7px;">
                        1...${numberOfPages}
                    </div>
                </div>
            </form>
        </ul>
    </nav>
</c:if>
