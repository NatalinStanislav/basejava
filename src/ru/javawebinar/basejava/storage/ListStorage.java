package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    public List<Resume> resumeList = new ArrayList<>();

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
    protected int getIndex(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void factualUpdate(Resume resume, int index) {
        resumeList.set(index, resume);
    }

    @Override
    protected void factualSave(Resume resume, int index) {
        resumeList.add(resume);
    }

    @Override
    protected void factualDelete(int index) {
        resumeList.remove(index);
    }

    @Override
    protected Resume factualGet(int index) {
        return resumeList.get(index);
    }
}
