package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> resumeList = new ArrayList<>();

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[size()]);
    }

    @Override
    protected Integer getIndex(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void factualUpdate(Resume resume, Object index) {
        resumeList.set((int) index, resume);
    }

    @Override
    protected void factualSave(Resume resume, Object index) {
        resumeList.add(resume);
    }

    @Override
    protected void factualDelete(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    protected Resume factualGet(Object index) {
        return resumeList.get((int) index);
    }

    @Override
    protected Object validateResumeNotExist(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    @Override
    protected Object validateResumeExist(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

}
