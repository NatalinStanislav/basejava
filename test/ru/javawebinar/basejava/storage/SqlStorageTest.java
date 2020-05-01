package ru.javawebinar.basejava.storage;

import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.util.SqlHelper;

import static ru.javawebinar.basejava.ResumeTestData.R1;


public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getURL(), Config.get().getUser(), Config.get().getPassword())));
    }

    @Test(expected = StorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }
}
