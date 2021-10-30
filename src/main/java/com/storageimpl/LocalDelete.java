package com.storageimpl;

import com.storage.Delete;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalDelete extends Delete {

    @Override
    public void deleteDirectory(String path, String directoryName) {
        File dir = new File(path + "/" + directoryName);

        if (FileUtils.isDirectory(dir)) {
            try {
                FileUtils.deleteDirectory(dir);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteFile(String path, String fileName) {
        File file = new File(path + "/" + fileName);

        try {
            FileUtils.delete(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAll(String rootPath) {
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
