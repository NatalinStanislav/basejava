package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected Resume getID(String uuid) {
        return resumeMap.get(uuid);
    }

    @Override
    protected void factualUpdate(Resume resume, Object key) {
        resumeMap.put(((Resume) key).getUuid(), resume);
    }

    @Override
    protected void factualSave(Resume resume, Object key) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void factualDelete(Object key) {
        resumeMap.remove(((Resume) key).getUuid());
    }

    @Override
    protected Resume factualGet(Object key) {
        return (Resume) key;
    }

    @Override
    protected boolean isExist(Object iD) {
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
