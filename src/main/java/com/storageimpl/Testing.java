package com.storageimpl;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Testing {

    public static void main(String[] args) {
        File file = new File("/Users/lazardejanovic/Desktop/MyStorage");
        System.out.println(FileUtils.sizeOfDirectory(file)/1024);
    }


}
