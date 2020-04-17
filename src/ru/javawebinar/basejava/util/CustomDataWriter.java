package ru.javawebinar.basejava.util;

import java.io.DataOutputStream;
import java.io.IOException;

public interface CustomDataWriter<T> {
    void writeFromCollection(T data, DataOutputStream dataOutputStream) throws IOException;
}
