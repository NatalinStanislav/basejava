package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
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
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[size()]);
    }

    @Override
    protected String getID(String uuid) {
        return resumeMap.containsKey(uuid) ? uuid : null;
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
        return key != null;
    }
}
