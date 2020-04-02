package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File dir = new File("D:/BaseJava/basejava");
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                recursiveFileSearch(file);
            }
        }
    }

    public static void recursiveFileSearch(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveFileSearch(f);
            }
        } else {
            System.out.println(file.getName());
        }
    }
}