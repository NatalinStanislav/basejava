package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(resumeList);
        list.sort(RESUME_COMPARATOR_BY_NAME);
        return list;
    }

    @Override
    protected Integer getID(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
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
    protected boolean isExist(Object index) {
        return (int) index >= 0;
    }
}
