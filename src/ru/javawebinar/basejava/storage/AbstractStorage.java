package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {

    //    protected final Logger LOG = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        T iD = validateResumeNotExist(resume.getUuid());
        doUpdate(resume, iD);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        T iD = validateResumeExist(resume.getUuid());
        doSave(resume, iD);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T iD = validateResumeNotExist(uuid);
        doDelete(iD);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T iD = validateResumeNotExist(uuid);
        return doGet(iD);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

    protected T validateResumeNotExist(String uuid) {
        T iD = getSearchKey(uuid);
        if (!isExist(iD)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return iD;
    }

    protected T validateResumeExist(String uuid) {
        T iD = getSearchKey(uuid);
        if (isExist(iD)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return iD;
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract void doUpdate(Resume resume, T iD);

    protected abstract void doSave(Resume resume, T iD);

    protected abstract void doDelete(T iD);

    protected abstract Resume doGet(T iD);

    protected abstract boolean isExist(T iD);

    protected abstract List<Resume> doCopyAll();
}
