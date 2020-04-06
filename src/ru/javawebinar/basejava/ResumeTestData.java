package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");
        resume.setContacts(getContacts());
        resume.setSections(getSections());

        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry);
        }

        System.out.println("----------------------------------------------------------------");

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry);
        }
    }

    private static Map<ContactType, String> getContacts() {
        Map<ContactType, String> contacts = new HashMap<>();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");
        return contacts;
    }

    private static Map<SectionType, AbstractSection> getSections() {
        Map<SectionType, AbstractSection> sections = new HashMap<>();

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        ListSection achievement = new ListSection(getAchievementList());
        ListSection qualification = new ListSection(getQualificationList());
        OrganizationSection experience = new OrganizationSection(getWorkExperienceList());
        OrganizationSection education = new OrganizationSection(getEducationList());

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
        list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\n");
        list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.\n");
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.\n");
        list.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.\n");
        list.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).\n");
        list.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.\n");
        return list;
    }

    private static List<String> getQualificationList() {
        List<String> list = new ArrayList<>();
        list.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2\n");
        list.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce\n");
        list.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,\n");
        list.add("MySQL, SQLite, MS SQL, HSQLDB\n");
        list.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,\n");
        list.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,\n");
        list.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).\n");
        list.add("Python: Django.\n");
        list.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js\n");
        list.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka\n");
        list.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.\n");
        list.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,\n");
        list.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.\n");
        list.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования\n");
        list.add("Родной русский, английский \"upper intermediate\"\n");
        return list;
    }

    public static List<Organization> getWorkExperienceList() {
        List<Organization> list = new ArrayList<>();

        Map<Organization.TimePeriod, Organization.Position> javaProjectsPeriodMap = new HashMap<>();
        javaProjectsPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2013, 10, 1), LocalDate.now()),
                new Organization.Position("Автор проекта.\n", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization javaProjects = new Organization("Java Online Projects", "http://javaops.ru/", javaProjectsPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> wrikePeriodMap = new HashMap<>();
        wrikePeriodMap.put(new Organization.TimePeriod(LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1)),
                new Organization.Position("Старший разработчик (backend)\n", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Organization wrike = new Organization("Wrike", "https://www.wrike.com/", wrikePeriodMap);

        Map<Organization.TimePeriod, Organization.Position> ritCenterPeriodMap = new HashMap<>();
        ritCenterPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1)),
                new Organization.Position("Java архитектор\n", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        Organization ritCenter = new Organization("RIT Center", null, ritCenterPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> luxoftPeriodMap = new HashMap<>();
        luxoftPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1)),
                new Organization.Position("Ведущий программист\n", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Organization luxoft = new Organization("Luxoft (Deutsche Bank)", "https://www.luxoft.com/", luxoftPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> yotaPeriodMap = new HashMap<>();
        yotaPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1)),
                new Organization.Position("Ведущий специалист\n", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        Organization yota = new Organization("Yota", "https://www.yota.ru/", yotaPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> enkataPeriodMap = new HashMap<>();
        enkataPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2007, 3, 1), LocalDate.of(2008, 6, 1)),
                new Organization.Position("Разработчик ПО\n", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        Organization enkata = new Organization("Enkata", "https://www.pega.com/products/pega-platform/robotic-automation", enkataPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> siemensAGPeriodMap = new HashMap<>();
        siemensAGPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1)),
                new Organization.Position("Разработчик ПО\n", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        Organization siemensAG = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", siemensAGPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> alcatelPeriodMap = new HashMap<>();
        alcatelPeriodMap.put(new Organization.TimePeriod(LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1)),
                new Organization.Position("Инженер по аппаратному и программному тестированию\n", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        Organization alcatel = new Organization("Alcatel", "http://www.alcatel.ru/", alcatelPeriodMap);

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

    public static List<Organization> getEducationList() {
        List<Organization> list = new ArrayList<>();

        Map<Organization.TimePeriod, Organization.Position> courseraPeriodMap = new HashMap<>();
        courseraPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)),
                new Organization.Position("\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        Organization coursera = new Organization("Coursera", "https://www.coursera.org/learn/progfun1", courseraPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> luxoftPeriodMap = new HashMap<>();
        luxoftPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1)),
                new Organization.Position("\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        Organization luxoft = new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", luxoftPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> siemensAGPeriodMap = new HashMap<>();
        siemensAGPeriodMap.put(new Organization.TimePeriod(LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1)),
                new Organization.Position("3 месяца обучения мобильным IN сетям (Берлин)", null));
        Organization siemensAG = new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html", siemensAGPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> alcatelPeriodMap = new HashMap<>();
        alcatelPeriodMap.put(new Organization.TimePeriod(LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1)),
                new Organization.Position("6 месяцев обучения цифровым телефонным сетям (Москва)", null));
        Organization alcatel = new Organization("Alcatel", "http://www.alcatel.ru/", alcatelPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> itmoPeriodMap = new HashMap<>();
        itmoPeriodMap.put(new Organization.TimePeriod(LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)),
                new Organization.Position("Аспирантура (программист С, С++)", null));
        itmoPeriodMap.put(new Organization.TimePeriod(LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)),
                new Organization.Position("Инженер (программист Fortran, C)", null));
        Organization itmo = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "https://itmo.ru/ru/", itmoPeriodMap);

        Map<Organization.TimePeriod, Organization.Position> schoolPeriodMap = new HashMap<>();
        schoolPeriodMap.put(new Organization.TimePeriod(LocalDate.of(1984, 9, 1), LocalDate.of(1987, 6, 1)),
                new Organization.Position("Закончил с отличием", null));
        Organization school = new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", schoolPeriodMap);

        list.add(coursera);
        list.add(luxoft);
        list.add(siemensAG);
        list.add(alcatel);
        list.add(itmo);
        list.add(school);
        return list;
    }
}
