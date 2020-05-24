<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.SectionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="isNew" type="java.lang.String" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="isNew" value="${isNew}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${!SectionUtil.isOrgSection(type)}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><textarea name="${type.name()}" rows="10"
                                      cols="95">${SectionUtil.sectionToTextArea(type,resume.getSection(type))}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:otherwise>
                    <p><b>${type.title}</b></p>
                    <c:set var="orgId" value='0'/>
                    <c:forEach var="org" items="${SectionUtil.getOrgList(resume, type)}">
                        <c:set var="positionId" value="0"/>
                        <c:forEach var="pos" items="${org.getPositions()}">
                            <label>Организация: <input type="text" name="${type.name()}OrganizationName${orgId}"
                                                       value="${SectionUtil.orgNameToText(resume.getSection(type),orgId)}"/></label>
                            <label>Адрес http:// <input type="text" name="${type.name()}OrganizationLink${orgId}"
                                                        value="${SectionUtil.orgUrlToText(resume.getSection(type),orgId)}"/></label>
                            <label>Позиция: <input type="text" name="${type.name()}Position${orgId}${positionId}"
                                                   value="${SectionUtil.orgPositionToText(resume.getSection(type),orgId,positionId)}"/></label><br/>
                            <label>Начало: <input type="date" name="${type.name()}StartDate${orgId}${positionId}"
                                                  value="${SectionUtil.orgPositionStartDate(resume.getSection(type),orgId,positionId)}"/></label>
                            <label>Конец: <input type="date" name="${type.name()}EndDate${orgId}${positionId}"
                                                 value="${SectionUtil.orgPositionEndDate(resume.getSection(type),orgId,positionId)}"/></label><br/>
                            <label>Описание: <textarea name="${type.name()}Description${orgId}${positionId}" rows="5"
                                                       cols="95">${SectionUtil.orgPositionDescriptionToText(resume.getSection(type),orgId,positionId)}</textarea></label><br/><br/><br/>
                            <c:set var="positionId" value="${positionId + 1}"/>
                        </c:forEach>
                        <input type="hidden" name="${type.name()}posCount${orgId}" value="${positionId}">
                        <c:set var="orgId" value="${orgId + 1}"/>
                    </c:forEach>
                    <input type="hidden" name="${type.name()}orgCount" value="${orgId}">
                    <b><em>Добавить позиции в раздел "${type.title}:"</em></b><br/><br/>
                    <c:forEach begin="0" end="4" varStatus="loop">
                        <label>Организация: <input type="text"
                                                   name="${type.name()}addOrganizationName${loop.count}"/></label>
                        <label>Адрес http:// <input type="text" name="${type.name()}addOrganizationLink${loop.count}"/></label>
                        <label>Позиция: <input type="text" name="${type.name()}addPosition${loop.count}"/></label><br/>
                        <label>Начало: <input type="date" name="${type.name()}addStartDate${loop.count}"/></label>
                        <label>Конец: <input type="date" name="${type.name()}addEndDate${loop.count}"/></label><br/>
                        <label>Описание: <textarea name="${type.name()}addDescription${loop.count}" rows="5"
                                                   cols="95"></textarea></label><br/><br/><br/>
                    </c:forEach>

                </c:otherwise>
            </c:choose>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
