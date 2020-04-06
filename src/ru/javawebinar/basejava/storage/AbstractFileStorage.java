package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files != null) {
            return files.length;
        }
        return 0;
    }

    @Override
    protected File getID(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void factualUpdate(Resume r, File file) {
        delete(r.getUuid());
        save(r);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void factualSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume factualGet(File file) {
        return doGet(file);
    }

    @Override
    protected void factualDelete(File file) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (file.getName().equals(f.getName())) {
                    f.delete();
                }
            }
        }
    }

    @Override
    protected List<Resume> getResumeList() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                list.add(doGet(f));
            }
        }
        return list;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doGet(File file);
}