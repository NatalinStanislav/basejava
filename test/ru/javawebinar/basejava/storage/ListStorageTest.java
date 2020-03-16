package ru.javawebinar.basejava.storage;

import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Ignore
    @Test
    @Override
    public void saveOverflow() throws Exception {
        super.saveOverflow();
    }
}