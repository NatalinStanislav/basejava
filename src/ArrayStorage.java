/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = size();

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid))
                return storage[i];
        }
        System.out.println("No resume with uuid: \"" + uuid + "\"");
        return null;
    }

    void delete(String uuid) {
        int num = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            System.out.println("No resume with uuid: \"" + uuid + "\"");
            return;
        }
        for (int i = num; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        int count = 0;
        for (Resume resume : storage) {
            if (resume != null)
                count++;
        }
        return count;
    }
}
