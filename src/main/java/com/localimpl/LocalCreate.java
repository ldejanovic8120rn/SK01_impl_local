package com.localimpl;

import com.exception.ConfigException;
import com.storage.Create;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalCreate extends Create {

    @Override
    public void saveDirectory(String directoryName) {
        File dir = FileUtils.getFile(StorageInfo.getStorageInfo().getConfig().getPath() + directoryName);
        if (!dir.exists()) {
            try {
                FileUtils.forceMkdir(dir);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFile(String fileName) throws Exception {
        File file = new File(StorageInfo.getStorageInfo().getConfig().getPath() + fileName);

        String extension = fileName.split("\\.")[1];
        if (!LocalFileChecker.getLFC().ckeckExtention(extension)) {
            throw new ConfigException("Extension not supported");
        }

        if (!LocalFileChecker.getLFC().checkNumOfFiles()) {
            throw new ConfigException("File number exceeded");
        }

        if (!LocalFileChecker.getLFC().checkMaxSize(FileUtils.sizeOf(file))) {
            throw new ConfigException("Storage size exceeded");
        }

        try {
            FileUtils.touch(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
