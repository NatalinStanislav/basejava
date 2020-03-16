package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    public void getAll() throws Exception {
        Resume[] resumes = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] resumes2 = storage.getAll();
        Arrays.sort(resumes2);
        Assert.assertArrayEquals(resumes, resumes2);
    }

    @Ignore
    @Test
    @Override
    public void saveOverflow() throws Exception {
    }
}