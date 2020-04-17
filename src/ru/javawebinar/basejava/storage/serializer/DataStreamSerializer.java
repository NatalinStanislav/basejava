package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.CustomDataWriter;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            writeWithException(contacts.entrySet(), dos, (data, dataOutputStream) -> {
                dataOutputStream.writeUTF(data.getKey().name());
                dataOutputStream.writeUTF(data.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), dos, (data, dataOutputStream) -> {
                String key = data.getKey().name();
                dataOutputStream.writeUTF(key);
                AbstractSection section = data.getValue();
                switch (key) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        dataOutputStream.writeUTF(((TextSection) section).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        int size = ((ListSection) section).getItems().size();
                        dataOutputStream.writeInt(size);
                        writeWithException(((ListSection) section).getItems(), dataOutputStream, (data2, dataOutputStream2) ->
                                dataOutputStream2.writeUTF(data2));
                        break;
                    }
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        int size = ((OrganizationSection) section).getOrganizations().size();
                        dataOutputStream.writeInt(size);
                        writeWithException(((OrganizationSection) section).getOrganizations(), dataOutputStream, (data2, dataOutputStream2) -> {
                            dataOutputStream2.writeUTF(data2.getHomePage().getName());
                            String url = data2.getHomePage().getUrl();
                            if (url == null) {
                                dataOutputStream2.writeUTF("null#value");
                            } else {
                                dataOutputStream2.writeUTF(url);
                            }
                            int listSize = data2.getPositions().size();
                            dataOutputStream2.writeInt(listSize);
                            writeWithException(data2.getPositions(), dataOutputStream2, (data3, dataOutputStream3) -> {
                                dataOutputStream3.writeUTF(data3.getStartDate().toString());
                                dataOutputStream3.writeUTF(data3.getEndDate().toString());
                                dataOutputStream3.writeUTF(data3.getTitle());
                                String description = data3.getDescription();
                                if (description == null) {
                                    dataOutputStream3.writeUTF("null#value");
                                } else {
                                    dataOutputStream3.writeUTF(description);
                                }
                            });
                        });
                        break;
                    }
                }
            });
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

            int mapSize = dis.readInt();
            for (int i = 0; i < mapSize; i++) {
                String key = dis.readUTF();
                AbstractSection section = null;
                switch (key) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        section = new TextSection(dis.readUTF());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        int listSize = dis.readInt();
                        List<String> items = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            items.add(dis.readUTF());
                        }
                        section = new ListSection(items);
                        break;
                    }
                    case "EXPERIENCE":
                    case "EDUCATION": {
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

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream, CustomDataWriter<T> writer) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(dataOutputStream);
        Objects.requireNonNull(writer);

        for (T t : collection) {
            writer.writeFromCollection(t, dataOutputStream);
        }
    }
}