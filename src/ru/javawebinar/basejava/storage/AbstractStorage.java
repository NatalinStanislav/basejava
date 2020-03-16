package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume resume) {
        Object object = validateResumeNotExist(resume.getUuid());
        factualUpdate(resume, object);
    }

    public void save(Resume resume) {
        Object object = validateResumeExist(resume.getUuid());
        factualSave(resume, object);
    }

    public void delete(String uuid) {
        Object object = validateResumeNotExist(uuid);
        factualDelete(object);
    }

    public Resume get(String uuid) {
        Object object = validateResumeNotExist(uuid);
        return factualGet(object);
    }

    protected abstract Object getIndex(String uuid);

    protected abstract void factualUpdate(Resume resume, Object object);

    protected abstract void factualSave(Resume resume, Object object);

    protected abstract void factualDelete(Object object);

    protected abstract Resume factualGet(Object object);

    protected abstract Object validateResumeNotExist(String uuid);

    protected abstract Object validateResumeExist(String uuid);
}
