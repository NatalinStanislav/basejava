package ru.javawebinar.basejava.util;

import java.io.IOException;

public interface CustomDataWriter<T> {
    void writeFromCollection(T data) throws IOException;
}
