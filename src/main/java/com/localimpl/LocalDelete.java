package com.localimpl;

import com.storage.Delete;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalDelete extends Delete {

    @Override
    public void deleteDirectory(String directoryName) {
        File dir = new File(StorageInfo.getStorageInfo().getConfig().getPath() + directoryName);

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
    public void deleteFile(String fileName) {
        File file = new File(StorageInfo.getStorageInfo().getConfig().getPath() + fileName);

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
            if (file.isDirectory()) {  //rekurzivno prolazimo za direktorijume i brisemo ih
                deleteAll(file.getPath());
            }
            if (!(file.getName().equals("config.json") || file.getName().equals("users.json"))) {
                file.delete();
            }
        }
    }
}
