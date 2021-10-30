package com.storageimpl;

import com.storage.Operations;
import com.utils.FileMetadata;

import java.util.List;

public class LocalOperations extends Operations {

    @Override
    public List<FileMetadata> getAllFiles(String path) {
        return null;
    }

    @Override
    public List<FileMetadata> getAllDirectories(String path) {
        return null;
    }

    @Override
    public List<FileMetadata> getAllFilesRecursive(String path) {
        return null;
    }

    @Override
    public void uploadFile() {

    }

    @Override
    public void moveFile(String fromPath, String toPath, String fileName) {

    }
}
