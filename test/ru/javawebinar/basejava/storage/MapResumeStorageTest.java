package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Ignore
    @Test
    @Override
    public void saveOverflow() throws Exception {
    }

}