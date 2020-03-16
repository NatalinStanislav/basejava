package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    public int size() {
        return resumeMap.size();
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[size()]);
    }

    @Override
    protected String getIndex(String uuid) {
        for (String str : resumeMap.keySet()) {
            if (str.equals(uuid)) {
                return uuid;
            }
        }
        return null;
    }

    @Override
    protected void factualUpdate(Resume resume, Object object) {
        resumeMap.put((String) object, resume);
    }

    @Override
    protected void factualSave(Resume resume, Object object) {
        resumeMap.put((String) object, resume);
    }

    @Override
    protected void factualDelete(Object object) {
        resumeMap.remove(object);
    }

    @Override
    protected Resume factualGet(Object object) {
        return resumeMap.get(object);
    }

    @Override
    protected Object validateResumeNotExist(String uuid) {
        String str = getIndex(uuid);
        if (str == null) {
            throw new NotExistStorageException(uuid);
        }
        return uuid;
    }

    @Override
    protected Object validateResumeExist(String uuid) {
        String str = getIndex(uuid);
        if (str != null) {
            throw new ExistStorageException(uuid);
        }
        return uuid;
    }
}
