package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected Integer getID(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void factualUpdate(Resume resume, Integer index) {
        resumeList.set(index, resume);
    }

    @Override
    protected void factualSave(Resume resume, Integer index) {
        resumeList.add(resume);
    }

    @Override
    protected void factualDelete(Integer index) {
        resumeList.remove(index.intValue());
    }

    @Override
    protected Resume factualGet(Integer index) {
        return resumeList.get(index);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(resumeList);
    }
}
