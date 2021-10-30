package com.storageimpl;

import com.storage.Create;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class LocalCreate extends Create {

    @Override
    public void saveDirectory(String path, String directoryName) {
        File dir = FileUtils.getFile(path + "/" + directoryName);
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
    public void saveFile(String path, String fileName) {
        File file = new File(path + "/" + fileName);

        try {
            FileUtils.touch(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
