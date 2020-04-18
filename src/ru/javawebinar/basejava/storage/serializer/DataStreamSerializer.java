package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.CustomDataReader;
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
            writeWithException(contacts.entrySet(), dos, (data) -> {
                dos.writeUTF(data.getKey().name());
                dos.writeUTF(data.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            writeWithException(sections.entrySet(), dos, (data) -> {
                String key = data.getKey().name();
                dos.writeUTF(key);
                AbstractSection section = data.getValue();
                switch (key) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        int size = ((ListSection) section).getItems().size();
                        dos.writeInt(size);
                        writeWithException(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    }
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        int size = ((OrganizationSection) section).getOrganizations().size();
                        dos.writeInt(size);
                        writeWithException(((OrganizationSection) section).getOrganizations(), dos, (data2) -> {
                            dos.writeUTF(data2.getHomePage().getName());
                            String url = data2.getHomePage().getUrl();
                            if (url == null) {
                                dos.writeUTF("");
                            } else {
                                dos.writeUTF(url);
                            }
                            int listSize = data2.getPositions().size();
                            dos.writeInt(listSize);
                            writeWithException(data2.getPositions(), dos, (data3) -> {
                                dos.writeUTF(data3.getStartDate().toString());
                                dos.writeUTF(data3.getEndDate().toString());
                                dos.writeUTF(data3.getTitle());
                                String description = data3.getDescription();
                                if (description == null) {
                                    dos.writeUTF("");
                                } else {
                                    dos.writeUTF(description);
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
            readWithException(dis, () ->
                    resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                String key = dis.readUTF();
                AbstractSection section = null;
                switch (key) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        section = new TextSection(dis.readUTF());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS": {
                        List<String> items = new ArrayList<>();
                        readWithException(dis, () -> items.add(dis.readUTF()));
                        section = new ListSection(items);
                        break;
                    }
                    case "EXPERIENCE":
                    case "EDUCATION": {
                        List<Organization> organizations = new ArrayList<>();
                        readWithException(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("")) {
                                url = null;
                            }
                            Link link = new Link(name, url);
                            List<Organization.Position> positions = new ArrayList<>();
                            readWithException(dis, () -> {
                                String startDate = dis.readUTF();
                                String endDate = dis.readUTF();
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("")) {
                                    description = null;
                                }
                                positions.add(new Organization.Position(LocalDate.parse(startDate), LocalDate.parse(endDate), title, description));
                            });
                            organizations.add(new Organization(link, positions));
                        });
                        section = new OrganizationSection(organizations);
                        break;
                    }
                }
                resume.addSection(SectionType.valueOf(key), section);
            });
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dataOutputStream, CustomDataWriter<T> writer) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(dataOutputStream);
        Objects.requireNonNull(writer);

        for (T t : collection) {
            writer.writeFromCollection(t);
        }
    }

    private void readWithException(DataInputStream dataInputStream, CustomDataReader reader) throws IOException {
        Objects.requireNonNull(dataInputStream);
        Objects.requireNonNull(reader);
        int j = dataInputStream.readInt();

        for (int i = 0; i < j; i++) {
            reader.readFromDIS();
        }
    }
}