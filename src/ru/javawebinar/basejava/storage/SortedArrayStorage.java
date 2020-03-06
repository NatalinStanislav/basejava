package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void getSaveRealization(Resume resume, int index) {
        int insertIndex = (index + 1) * -1;
        if (size + 1 - insertIndex >= 0) {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size + 1 - insertIndex);
        }
        storage[insertIndex] = resume;
    }

    @Override
    protected void getDeleteRealization(int index) {
        if (size - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
        }
    }
}