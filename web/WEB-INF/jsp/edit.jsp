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
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
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
        <c:forEach var="type" items="<%=SectionType.firstFourValues()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><textarea name="${type.name()}" rows="10"
                              cols="95">${SectionUtil.sectionToTextArea(type,resume.getSection(type))}</textarea></dd>
            </dl>
        </c:forEach>
        <p><b>Опыт работы</b></p>
        <c:set var="orgId" value='0'/>
        <c:set var="positionId" value="0"/>
        <c:forEach begin="0" end="9" varStatus="loop">
            <label>Название организации N${orgId+1}: <input type="text" name="expOrganizationName${orgId}"
                                                            value="${SectionUtil.orgNameToText(resume.getSection(SectionType.EXPERIENCE),orgId)}"/></label>
            <label>Адрес http:// <input type="text" name="expOrganizationLink${orgId}"
                                        value="${SectionUtil.orgUrlToText(resume.getSection(SectionType.EXPERIENCE),orgId)}"/></label>
            <label>Должность: <input type="text" name="expPosition${orgId}${positionId}"
                                     value="${SectionUtil.orgPositionToText(resume.getSection(SectionType.EXPERIENCE),orgId,positionId)}"/></label><br/>
            <label>Начало работы: <input type="date" name="expStartDate${orgId}${positionId}"
                                         value="${SectionUtil.orgPositionStartDate(resume.getSection(SectionType.EXPERIENCE),orgId,positionId)}"/></label>
            <label>Окончание работы: <input type="date" name="expEndDate${orgId}${positionId}"
                                            value="${SectionUtil.orgPositionEndDate(resume.getSection(SectionType.EXPERIENCE),orgId,positionId)}"/></label><br/>
            <label>Обязанности: <textarea name="expDuties${orgId}${positionId}" rows="5"
                                          cols="95">${SectionUtil.orgPositionDescriptionToText(resume.getSection(SectionType.EXPERIENCE),orgId,positionId)}</textarea></label><br/><br/><br/>
            <c:choose>
                <c:when test="${SectionUtil.isItLastPosition(resume.getSection(SectionType.EXPERIENCE),orgId,positionId)}">
                    <c:set var="orgId" value="${orgId + 1}"/>
                    <c:set var="positionId" value="0"/>
                </c:when>
                <c:otherwise>
                    <c:set var="orgId" value="${orgId}"/>
                    <c:set var="positionId" value="${positionId + 1}"/>
                </c:otherwise>
            </c:choose>
        </c:forEach>


        <c:set var="orgId" value='0'/>
        <c:set var="positionId" value="0"/>
        <p><b>Образование</b></p>
        <c:forEach begin="0" end="9" varStatus="loop">
            <label>Учебное заведение N${orgId+1}: <input type="text" name="edOrganizationName${orgId}"
                                                         value="${SectionUtil.orgNameToText(resume.getSection(SectionType.EDUCATION),orgId)}"/></label>
            <label>Адрес http:// <input type="text" name="edOrganizationLink${orgId}"
                                        value="${SectionUtil.orgUrlToText(resume.getSection(SectionType.EDUCATION),orgId)}"/></label><br/>
            <label>Начало учебы: <input type="date" name="edStartDate${orgId}${positionId}"
                                        value="${SectionUtil.orgPositionStartDate(resume.getSection(SectionType.EDUCATION),orgId,positionId)}"/></label>
            <label>Окончание учебы: <input type="date" name="edEndDate${orgId}${positionId}"
                                           value="${SectionUtil.orgPositionEndDate(resume.getSection(SectionType.EDUCATION),orgId,positionId)}"/></label><br/>
            <label>Описание: <textarea name="edPosition${orgId}${positionId}" rows="3"
                                       cols="95">${SectionUtil.orgPositionToText(resume.getSection(SectionType.EDUCATION),orgId,positionId)}</textarea></label><br/><br/><br/>

            <c:choose>
                <c:when test="${SectionUtil.isItLastPosition(resume.getSection(SectionType.EDUCATION),orgId,positionId)}">
                    <c:set var="orgId" value="${orgId + 1}"/>
                    <c:set var="positionId" value="0"/>
                </c:when>
                <c:otherwise>
                    <c:set var="orgId" value="${orgId}"/>
                    <c:set var="positionId" value="${positionId + 1}"/>
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
