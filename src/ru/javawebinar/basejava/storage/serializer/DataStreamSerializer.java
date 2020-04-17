package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                AbstractSection section = entry.getValue();
                String sectionClass = section.getClass().getSimpleName();
                dos.writeUTF(sectionClass);
                switch (sectionClass) {
                    case "TextSection":
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case "ListSection": {
                        int size = ((ListSection) section).getItems().size();
                        dos.writeInt(size);
                        for (String str : ((ListSection) section).getItems()) {
                            dos.writeUTF(str);
                        }
                        break;
                    }
                    case "OrganizationSection": {
                        int size = ((OrganizationSection) section).getOrganizations().size();
                        dos.writeInt(size);
                        for (int i = 0; i < size; i++) {
                            Organization organization = ((OrganizationSection) section).getOrganizations().get(i);
                            dos.writeUTF(organization.getHomePage().getName());
                            String url = organization.getHomePage().getUrl();
                            if (url == null) {
                                dos.writeUTF("null#value");
                            } else {
                                dos.writeUTF(url);
                            }
                            int listSize = organization.getPositions().size();
                            dos.writeInt(listSize);
                            for (int j = 0; j < listSize; j++) {
                                Organization.Position position = ((OrganizationSection) section).getOrganizations().get(i).getPositions().get(j);
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                String description = position.getDescription();
                                if (description == null) {
                                    dos.writeUTF("null#value");
                                } else {
                                    dos.writeUTF(description);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            int mapSize = dis.readInt();
            for (int i = 0; i < mapSize; i++) {
                String key = dis.readUTF();
                String nameSection = dis.readUTF();
                AbstractSection section = null;
                switch (nameSection) {
                    case "TextSection":
                        section = new TextSection(dis.readUTF());
                        break;
                    case "ListSection": {
                        int listSize = dis.readInt();
                        List<String> items = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            items.add(dis.readUTF());
                        }
                        section = new ListSection(items);
                        break;
                    }
                    case "OrganizationSection": {
                        int listSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("null#value")) {
                                url = null;
                            }
                            Link link = new Link(name, url);
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>(positionsSize);
                            for (int k = 0; k < positionsSize; k++) {
                                String startDate = dis.readUTF();
                                String endDate = dis.readUTF();
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null#value")) {
                                    description = null;
                                }
                                positions.add(new Organization.Position(LocalDate.parse(startDate), LocalDate.parse(endDate), title, description));
                            }
                            organizations.add(new Organization(link, positions));
                        }
                        section = new OrganizationSection(organizations);
                        break;
                    }
                }
                resume.addSection(SectionType.valueOf(key), section);
            }
            return resume;
        }
    }
}