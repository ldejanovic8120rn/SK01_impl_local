package com.storageimpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class Testing {

    public static void main(String[] args) {
        File directory = new File("/Users/lazardejanovic/Desktop/MyStorage");

        List<File> list = Arrays.asList(directory.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY));
        //List<File> list = (List<File>) FileUtils.listFilesAndDirs(new File("/Users/lazardejanovic/Desktop/MyStorage"), DirectoryFileFilter.DIRECTORY, DirectoryFileFilter.DIRECTORY);
        System.out.println(list);
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
