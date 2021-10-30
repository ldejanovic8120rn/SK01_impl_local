package com.storageimpl;

import com.storage.Storage;
import com.utils.Privilege;

import java.io.File;
import java.util.List;

public class LocalStorage extends Storage {

    @Override
    public File getConfig(String s) {
        return null;
    }

    @Override
    public File getUsers(String s) {
        return null;
    }

    @Override
    public void createStorage(String s, String s1, String s2, String s3) {

    }

    @Override
    public void editConfig(String s, String s1, String s2, List<String> list) {

    }

    @Override
    public void editUsers(String s, String s1, String s2, Privilege privilege) {

    }
}
