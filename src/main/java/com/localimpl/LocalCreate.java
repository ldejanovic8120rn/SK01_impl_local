package com.localimpl;

import com.exception.ConfigException;
import com.storage.Create;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        String extension = fileName.split("\\.")[1];
        if (!LocalFileChecker.getLFC().ckeckExtention(extension)) {
            throw new ConfigException("Extension not supported");
        }

        if (!LocalFileChecker.getLFC().checkNumOfFiles()) {
            throw new ConfigException("File number exceeded");
        }

        try {
            FileUtils.touch(new File(StorageInfo.getStorageInfo().getConfig().getPath() + fileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
