package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper helper;

    public SqlStorage(SqlHelper helper) {
        this.helper = helper;
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid.trim(), rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        helper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        helper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            if (!ps.execute()) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        final List<Resume> list = new ArrayList<>();
        helper.execute("SELECT uuid FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(get(rs.getString("uuid")));
            }
            Collections.sort(list);
            return null;
        });
        return list;
    }

    @Override
    public int size() {
        final int[] i = new int[1];
        helper.execute("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            i[0] = Integer.parseInt(rs.getString(1));
            return null;
        });
        return i[0];
    }
}