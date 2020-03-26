package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.setContacts(getContacts());
        resume.setSections(getSections());

        for (Map.Entry entry : resume.getContacts().entrySet()) {
            System.out.println(entry);
        }

        System.out.println("----------------------------------------------------------------");

        for (Map.Entry entry : resume.getSections().entrySet()) {
            System.out.println(entry);
        }
    }

    private static Map<Contacts, String> getContacts() {
        Map<Contacts, String> contacts = new HashMap<>();
        contacts.put(Contacts.PHONE_NUMBER, "+7(921) 855-0482");
        contacts.put(Contacts.SKYPE_ACCOUNT, "grigory.kislin");
        contacts.put(Contacts.EMAIL, "gkislin@yandex.ru");
        contacts.put(Contacts.LINKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        contacts.put(Contacts.GITHUB_PROFILE, "https://github.com/gkislin");
        contacts.put(Contacts.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473/grigory-kislin");
        contacts.put(Contacts.HOME_PAGE, "http://gkislin.ru/");
        return contacts;
    }

    private static Map<SectionType, TextSection> getSections() {
        Map<SectionType, TextSection> sections = new HashMap<>();

        TextSection objective = new TextSection();
        objective.setInfo("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection();
        personal.setInfo("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        ListTextSection achievement = new ListTextSection();
        achievement.setInfoList(getAchievementList());

        ListTextSection qualification = new ListTextSection();
        qualification.setInfoList(getQualificationList());

        ChronoDateSection experience = new ChronoDateSection();
        experience.setSectionList(getWorkExperienceList());

        ChronoDateSection education = new ChronoDateSection();
        education.setSectionList(getEducationList());

        sections.put(SectionType.OBJECTIVE, objective);
        sections.put(SectionType.PERSONAL, personal);
        sections.put(SectionType.ACHIEVEMENT, achievement);
        sections.put(SectionType.QUALIFICATIONS, qualification);
        sections.put(SectionType.EXPERIENCE, experience);
        sections.put(SectionType.EDUCATION, education);
        return sections;
    }

    private static List<String> getAchievementList() {
        List<String> list = new ArrayList<>();
        list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        list.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        list.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        list.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        return list;
    }

    private static List<String> getQualificationList() {
        List<String> list = new ArrayList<>();
        list.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        list.add("MySQL, SQLite, MS SQL, HSQLDB");
        list.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        list.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        list.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        list.add("Python: Django.");
        list.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        list.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        list.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        list.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        list.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        list.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        list.add("Родной русский, английский \"upper intermediate\"");
        return list;
    }

    public static List<DateSection> getWorkExperienceList() {
        List<DateSection> list = new ArrayList<>();

        DateSection javaProjects = new DateSection("Java Online Projects", "http://javaops.ru/");
        javaProjects.getPlacesMap().put(new TimePeriod(YearMonth.of(2013, 10), YearMonth.now()), "Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        DateSection wrike = new DateSection("Wrike", "https://www.wrike.com/");
        wrike.getPlacesMap().put(new TimePeriod(YearMonth.of(2014, 10), YearMonth.of(2016, 1)), "Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        DateSection ritCenter = new DateSection("RIT Center", null);
        ritCenter.getPlacesMap().put(new TimePeriod(YearMonth.of(2012, 4), YearMonth.of(2014, 10)), "Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");

        DateSection luxoft = new DateSection("Luxoft (Deutsche Bank)", "https://www.luxoft.com/");
        luxoft.getPlacesMap().put(new TimePeriod(YearMonth.of(2010, 12), YearMonth.of(2012, 4)), "Ведущий программист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");

        DateSection yota = new DateSection("Yota", "https://www.yota.ru/");
        yota.getPlacesMap().put(new TimePeriod(YearMonth.of(2008, 6), YearMonth.of(2010, 12)), "Ведущий специалист\n" +
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");

        DateSection enkata = new DateSection("Enkata", "https://www.pega.com/products/pega-platform/robotic-automation");
        enkata.getPlacesMap().put(new TimePeriod(YearMonth.of(2007, 3), YearMonth.of(2008, 6)), "Разработчик ПО\n" +
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");

        DateSection siemensAG = new DateSection("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        siemensAG.getPlacesMap().put(new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2007, 2)), "Разработчик ПО\n" +
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");

        DateSection alcatel = new DateSection("Alcatel", "http://www.alcatel.ru/");
        alcatel.getPlacesMap().put(new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(2005, 1)), "Инженер по аппаратному и программному тестированию\n" +
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        list.add(javaProjects);
        list.add(wrike);
        list.add(ritCenter);
        list.add(luxoft);
        list.add(yota);
        list.add(enkata);
        list.add(siemensAG);
        list.add(alcatel);
        return list;
    }

    public static List<DateSection> getEducationList() {
        List<DateSection> list = new ArrayList<>();

        DateSection coursera = new DateSection("Coursera", "https://www.coursera.org/learn/progfun1");
        coursera.getPlacesMap().put(new TimePeriod(YearMonth.of(2013, 3), YearMonth.of(2013, 5)), "\"Functional Programming Principles in Scala\" by Martin Odersky");

        DateSection luxoft = new DateSection("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html");
        luxoft.getPlacesMap().put(new TimePeriod(YearMonth.of(2011, 3), YearMonth.of(2011, 4)), "\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");

        DateSection siemensAG = new DateSection("Siemens AG", "https://new.siemens.com/ru/ru.html");
        siemensAG.getPlacesMap().put(new TimePeriod(YearMonth.of(2005, 1), YearMonth.of(2005, 4)), "3 месяца обучения мобильным IN сетям (Берлин)");

        DateSection alcatel = new DateSection("Alcatel", "http://www.alcatel.ru/");
        alcatel.getPlacesMap().put(new TimePeriod(YearMonth.of(1997, 9), YearMonth.of(1998, 3)), "6 месяцев обучения цифровым телефонным сетям (Москва)");

        DateSection itmo = new DateSection("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/ru/");
        itmo.getPlacesMap().put(new TimePeriod(YearMonth.of(1993, 9), YearMonth.of(1996, 7)), "Аспирантура (программист С, С++)");
        itmo.getPlacesMap().put(new TimePeriod(YearMonth.of(1987, 9), YearMonth.of(1993, 7)), "Инженер (программист Fortran, C)");

        DateSection school = new DateSection("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/");
        school.getPlacesMap().put(new TimePeriod(YearMonth.of(1984, 9), YearMonth.of(1987, 6)), "Закончил с отличием");

        list.add(coursera);
        list.add(luxoft);
        list.add(siemensAG);
        list.add(alcatel);
        list.add(itmo);
        list.add(school);
        return list;
    }
}
