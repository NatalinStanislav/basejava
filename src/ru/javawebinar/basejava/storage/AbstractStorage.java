package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;

public abstract class AbstractStorage implements Storage {
    protected Collection<Resume> resumes;

    @Override
    public void clear() {
        resumes.clear();
    }

    @Override
    public void update(Resume resume) {
        if (resumes.remove(resume)) {
            resumes.add(resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (resumes.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            resumes.add(resume);
        }
    }

    @Override
    public void delete(String uuid) {
        Resume resume = new Resume(uuid);
        if (!resumes.remove(resume)) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return (Resume[]) resumes.toArray();
    }

    @Override
    public int size() {
        return resumes.size();
    }
}
