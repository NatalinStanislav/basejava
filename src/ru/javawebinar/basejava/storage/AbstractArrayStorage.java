package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected void factualUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    protected void factualSave(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, index);
            size++;
        }
    }

    protected void factualDelete(int index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected Resume factualGet(int index) {
        return storage[index];
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);
}
