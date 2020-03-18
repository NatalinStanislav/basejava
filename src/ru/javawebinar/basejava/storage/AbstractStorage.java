package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object iD = validateResumeNotExist(resume.getUuid());
        factualUpdate(resume, iD);
    }

    public void save(Resume resume) {
        Object iD = validateResumeExist(resume.getUuid());
        factualSave(resume, iD);
    }

    public void delete(String uuid) {
        Object iD = validateResumeNotExist(uuid);
        factualDelete(iD);
    }

    public Resume get(String uuid) {
        Object iD = validateResumeNotExist(uuid);
        return factualGet(iD);
    }

    protected Object validateResumeNotExist(String uuid) {
        Object iD = getID(uuid);
        if (!isExist(iD)) {
            throw new NotExistStorageException(uuid);
        }
        return iD;
    }

    protected Object validateResumeExist(String uuid) {
        Object iD = getID(uuid);
        if (isExist(iD)) {
            throw new ExistStorageException(uuid);
        }
        return iD;

    }

    protected abstract Object getID(String uuid);

    protected abstract void factualUpdate(Resume resume, Object iD);

    protected abstract void factualSave(Resume resume, Object iD);

    protected abstract void factualDelete(Object iD);

    protected abstract Resume factualGet(Object iD);

    protected abstract boolean isExist(Object iD);
}
