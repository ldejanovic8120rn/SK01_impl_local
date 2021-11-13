package com.localimpl;

import com.storage.FileChecker;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class LocalFileChecker extends FileChecker {

    private LocalFileChecker() {}

    private static final class LocalFileCheckerHolder {
        static final LocalFileChecker localFileChecker = new LocalFileChecker();
    }

    public static LocalFileChecker getLFC() {
        return LocalFileCheckerHolder.localFileChecker;
    }

    @Override
    public boolean ckeckPath(String path) {
        File file = new File(path);
        return file.exists() && !path.startsWith(StorageInfo.getStorageInfo().getConfig().getPath());
    }

    @Override
    public boolean ckeckStoragePath(String path) {
        File file = new File(path);
        return file.exists();
    }

    @Override
    public boolean checkNumOfFiles() {
        if (StorageInfo.getStorageInfo().getConfig().getMaxNumOfFiles().equals("UN")) {
            return true;
        }

        return countFiles(StorageInfo.getStorageInfo().getConfig().getPath(), 0) + 1 <= Integer.parseInt(StorageInfo.getStorageInfo().getConfig().getMaxNumOfFiles());
    }

    @Override
    public boolean checkMaxSize(long size) {
        if (StorageInfo.getStorageInfo().getConfig().getMaxSize().equals("UN")) {
            return true;
        }

        File root = new File(StorageInfo.getStorageInfo().getConfig().getPath());
        return FileUtils.sizeOfDirectory(root) + size <= Long.parseLong(StorageInfo.getStorageInfo().getConfig().getMaxSize());
    }


    @Override
    public boolean ckeckExtention(String extension) {
        return StorageInfo.getStorageInfo().getConfig().getUnsupportedFiles().contains(extension);
    }

    private int countFiles(String rootPath, int counter) {
        File dir = new File(rootPath);

        for (File file: dir.listFiles()) {
            if (file.isHidden()) {
                continue;
            }

            if (file.isDirectory()) {
                counter = countFiles(file.getPath(), counter);
            }
            else {
                counter++;
            }
        }

        return counter;
    }
}
