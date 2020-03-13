package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collection;

public class ListStorage extends AbstractStorage {
    {
        resumes = new ArrayList<>();
    }

    @Override
    public Resume get(String uuid) {
        ArrayList<Resume> list = (ArrayList<Resume>) resumes;
        Resume resume = new Resume(uuid);
        int index = list.indexOf(resume);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return list.get(index);
    }

    public Collection<Resume> getResumes() {   //for MainTestListStorage
        return resumes;
    }
}
