package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object ID = validateResumeNotExist(resume.getUuid());
        factualUpdate(resume, ID);
    }

    public void save(Resume resume) {
        Object ID = validateResumeExist(resume.getUuid());
        factualSave(resume, ID);
    }

    public void delete(String uuid) {
        Object ID = validateResumeNotExist(uuid);
        factualDelete(ID);
    }

    public Resume get(String uuid) {
        Object ID = validateResumeNotExist(uuid);
        return factualGet(ID);
    }

    protected Object validateResumeNotExist(String uuid) {
        Object ID = getID(uuid);
        if (isNotExist(ID)) {
            throw new NotExistStorageException(uuid);
        }
        return ID;
    }

    protected Object validateResumeExist(String uuid) {
        Object ID = getID(uuid);
        if (isExist(ID)) {
            throw new ExistStorageException(uuid);
        }
        return ID;

    }

    protected abstract Object getID(String uuid);

    protected abstract void factualUpdate(Resume resume, Object ID);

    protected abstract void factualSave(Resume resume, Object ID);

    protected abstract void factualDelete(Object ID);

    protected abstract Resume factualGet(Object ID);

    protected abstract boolean isExist(Object object);

    protected abstract boolean isNotExist(Object object);
}
