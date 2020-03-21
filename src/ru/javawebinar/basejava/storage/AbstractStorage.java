package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    /*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    */

    protected static final Comparator<Resume> RESUME_COMPARATOR_BY_NAME_AND_UUID = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

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

    public List<Resume> getAllSorted() {
        List<Resume> list = getResumeList();
        list.sort(RESUME_COMPARATOR_BY_NAME_AND_UUID);
        return list;
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

    protected abstract List<Resume> getResumeList();
}
