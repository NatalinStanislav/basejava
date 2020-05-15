package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume = null;
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    String value = rs.getString("value");
                    if (value != null) {
                        resume.addContact(ContactType.valueOf(rs.getString("type")), value);
                    }
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM section " +
                    "     WHERE resume_uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String value = rs.getString("value");
                    if (value != null) {
                        String type = rs.getString("type");
                        resume.addSection(SectionType.valueOf(type), getSection(type, value));
                    }
                }
            }
            return resume;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
                ps.execute();
            }
            deleteContacts(r, conn);
            deleteSections(r, conn);
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    resumes.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                addFieldsToResumes(ps, resumes, (resultSet, uuid) -> {
                    resumes.get(uuid).addContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                });
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                addFieldsToResumes(ps, resumes, (resultSet, uuid) -> {
                    String type = resultSet.getString("type");
                    resumes.get(uuid).addSection(SectionType.valueOf(type), getSection(type, resultSet.getString("value")));
                });
            }
            return null;
        });
        return new ArrayList<>(resumes.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                String name = e.getKey().name();
                ps.setString(2, name);
                if (name.equals("PERSONAL") || name.equals("OBJECTIVE")) {
                    ps.setString(3, e.getValue().toString());
                } else if (name.equals("ACHIEVEMENT") || name.equals("QUALIFICATIONS")) {
                    ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getItems()));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private AbstractSection getSection(String sectionType, String value) {
        switch (sectionType) {
            case "PERSONAL":
            case "OBJECTIVE":
                return new TextSection(value);
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                return new ListSection(value.split("\n"));
            case "EXPERIENCE":
            case "EDUCATION":
            default:
                return null;
        }
    }

    private void addFieldsToResumes(PreparedStatement ps, Map<String, Resume> resumes, SQLInterface<ResultSet, String> biConsumer) throws SQLException {
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String uuid = rs.getString("resume_uuid");
            String type = rs.getString("type");
            if (type != null) {
                biConsumer.accept(rs, uuid);
            }
        }
    }

    private interface SQLInterface<T, U> {
        void accept(T t, U u) throws SQLException;
    }
}