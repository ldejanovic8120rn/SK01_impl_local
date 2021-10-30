package com.storageimpl;

import com.storage.Operations;
import com.utils.FileMetadata;

import java.util.List;

public class LocalOperations extends Operations {

    @Override
    public List<FileMetadata> getAllFiles(String s) {
        return null;
    }

    @Override
    public List<FileMetadata> getAllDirectories(String s) {
        return null;
    }

    @Override
    public List<FileMetadata> getAllFilesRecursive(String s) {
        return null;
    }

    @Override
    public void uploadFile() {

    }

    @Override
    public void moveFile(String s, String s1, String s2) {

    }
}
