package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if ((indexInStorage(resume.getUuid()) == -1) && (size < 10000)) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Resume was not saved");
        }
    }

    public void update(Resume resume) {
        int index = indexInStorage(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Can't update resume");
        }
    }

    public Resume get(String uuid) {
        int index = indexInStorage(uuid);
        if (index == -1) {
            System.out.println("Resume was not found");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = indexInStorage(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume was not deleted");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int indexInStorage(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
