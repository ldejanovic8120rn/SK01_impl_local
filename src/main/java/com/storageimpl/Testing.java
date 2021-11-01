package com.storageimpl;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Testing {

    public static void main(String[] args) {
        File file = new File("/Users/lazardejanovic/Desktop/MyStorage");
        String fromPath = "/Users/lazardejanovic/Desktop/MyStorage/nesto.exe";
        String extension = fromPath.split("/")[fromPath.split("/").length - 1].split("\\.")[1];
        System.out.println(extension);
    }


}
