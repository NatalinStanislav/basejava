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

        System.out.println(ls.size());
        System.out.println(ls.getResumes());
        ls.delete("3");
        System.out.println(ls.size());
        System.out.println(ls.getResumes());
        ls.update(new Resume("5"));
        System.out.println(ls.size());
        System.out.println(ls.getResumes());
        ls.clear();
        System.out.println(ls.size());
        System.out.println(ls.getResumes());

        ls.save(new Resume("1"));
        ls.save(new Resume("2"));
        ls.save(new Resume("3"));
        ls.save(new Resume("4"));
        ls.save(new Resume("5"));
        System.out.println(ls.get("2"));
        System.out.println(ls.size());
        System.out.println(ls.getResumes());

        //ls.save(new Resume("1"));
        //ls.delete("8");
        //ls.update(new Resume("dummy"));
        //ls.get("dummy");


    }
}
