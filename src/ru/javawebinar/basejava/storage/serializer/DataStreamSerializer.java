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
            // TODO implements sections
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                AbstractSection section = entry.getValue();
                String sectionClass = section.getClass().getSimpleName();
                dos.writeUTF(sectionClass);
                if (sectionClass.equals("TextSection")) {
                    dos.writeUTF(((TextSection) section).getContent());
                } else if (sectionClass.equals("ListSection")) {
                    int size = ((ListSection) section).getItems().size();
                    dos.writeInt(size);
                    for (String str : ((ListSection) section).getItems()) {
                        dos.writeUTF(str);
                    }
                } else if (sectionClass.equals("OrganizationSection")) {
                    int size = ((OrganizationSection) section).getOrganizations().size();
                    dos.writeInt(size);
                    for (int i = 0; i < size; i++) {
                        Organization organization = ((OrganizationSection) section).getOrganizations().get(i);
                        dos.writeUTF(organization.getHomePage().getName());
                        dos.writeUTF(organization.getHomePage().getUrl());
                        int listSize = organization.getPositions().size();
                        dos.writeInt(listSize);
                        for (int j = 0; j < listSize; j++) {
                            Organization.Position position = ((OrganizationSection) section).getOrganizations().get(i).getPositions().get(j);
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        }
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
                if (nameSection.equals("TextSection")) {
                    section = new TextSection(dis.readUTF());
                } else if (nameSection.equals("ListSection")) {
                    int listSize = dis.readInt();
                    List<String> items = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        items.add(dis.readUTF());
                    }
                    section = new ListSection(items);
                } else if (nameSection.equals("OrganizationSection")) {
                    int listSize = dis.readInt();
                    List<Organization> organizations = new ArrayList<>(listSize);
                    for (int j = 0; j < listSize; j++) {
                        Link link = new Link(dis.readUTF(), dis.readUTF());
                        int positionsSize = dis.readInt();
                        List<Organization.Position> positions = new ArrayList<>(positionsSize);
                        for (int k = 0; k < positionsSize; k++) {
                            positions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                        }
                        organizations.add(new Organization(link, positions));
                    }
                    section = new OrganizationSection(organizations);
                }
                resume.addSection(SectionType.valueOf(key), section);
            }
            return resume;
        }
    }
}