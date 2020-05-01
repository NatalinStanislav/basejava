package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Resume;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ABlockOfCode {
    Resume execute(PreparedStatement ps) throws SQLException;
}
