package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("D:/BaseJava/basejava/src/ru/javawebinar/basejava");
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                recursiveFileSearch(file);
            }
        }
    }

    public static void recursiveFileSearch(File file) {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    recursiveFileSearch(f);
                }
            }
        } else {
            System.out.println(file.getName());
        }
    }


}