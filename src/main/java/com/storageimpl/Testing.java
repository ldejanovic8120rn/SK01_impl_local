package com.storageimpl;

import java.io.File;

public class Testing {

    public static void main(String[] args) {
        deleteAll("/Users/lazardejanovic/Desktop");
    }

    public static void deleteAll(String rootPath) {
        File dir = new File(rootPath);

        for (File file: dir.listFiles()) {
            if (file.isDirectory())
                deleteAll(file.getPath());
            if (!(file.getName().equals("config.json") || file.getName().equals("users.json"))) {
                file.delete();
            }
        }
    }
}
