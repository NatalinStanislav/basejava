package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected Resume getID(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void factualUpdate(Resume resume, Resume key) {
        resumeMap.put(key.getUuid(), resume);
    }

    @Override
    protected void factualSave(Resume resume, Resume key) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void factualDelete(Resume key) {
        resumeMap.remove(key.getUuid());
    }

    @Override
    protected Resume factualGet(Resume key) {
        return key;
    }

    @Override
    protected boolean isExist(Resume iD) {
        return iD != null;
    }

    @Override
    protected List<Resume> getResumeList() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
