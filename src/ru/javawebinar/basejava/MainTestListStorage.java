package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.ListStorage;

public class MainTestListStorage {
    public static void main(String[] args) {
        ListStorage ls = new ListStorage();
        ls.save(new Resume("1"));
        ls.save(new Resume("2"));
        ls.save(new Resume("3"));
        ls.save(new Resume("4"));
        ls.save(new Resume("5"));
        System.out.println(ls.get("3"));
        ls.delete("4");
        System.out.println(ls.size());
        ls.update(new Resume("2"));
        for (Resume resume : ls.getAll())
            System.out.print(resume + ", ");
    }
}
