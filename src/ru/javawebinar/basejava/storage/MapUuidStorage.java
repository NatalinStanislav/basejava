package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(resumeMap.values());
        list.sort(RESUME_COMPARATOR_BY_NAME);
        return list;
    }

    @Override
    protected String getID(String uuid) {
        return uuid;
    }

    @Override
    protected void factualUpdate(Resume resume, Object key) {
        resumeMap.put((String) key, resume);
    }

    @Override
    protected void factualSave(Resume resume, Object key) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void factualDelete(Object key) {
        resumeMap.remove(key);
    }

    @Override
    protected Resume factualGet(Object key) {
        return resumeMap.get(key);
    }

    @Override
    protected boolean isExist(Object key) {
        return resumeMap.containsKey(key);
    }
}
