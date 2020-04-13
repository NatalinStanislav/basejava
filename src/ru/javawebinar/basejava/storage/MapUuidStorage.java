package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    public int size() {
        return resumeMap.size();
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume resume, String key) {
        resumeMap.put(key, resume);
    }

    @Override
    protected void doSave(Resume resume, String key) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String key) {
        resumeMap.remove(key);
    }

    @Override
    protected Resume doGet(String key) {
        return resumeMap.get(key);
    }

    @Override
    protected boolean isExist(String key) {
        return resumeMap.containsKey(key);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(resumeMap.values());
    }
}
