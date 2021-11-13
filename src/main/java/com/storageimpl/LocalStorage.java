package com.storageimpl;

import com.exception.LogException;
import com.google.gson.Gson;
import com.storage.Storage;
import com.utils.Privilege;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalStorage extends Storage {

    @Override
    public File getConfig(String path) {
//        ClassLoader cl = getClass().getClassLoader();
//        return new File(cl.getResource(path + "/config.json").getFile());

        return FileUtils.getFile(path + "/config.json");
    }
    @Override
    public File getUsers(String path) {
        ClassLoader cl = getClass().getClassLoader();
        return new File(cl.getResource(path + "/users.json").getFile());
    }

    @Override
    public void createStorage(String path, String storageName, String adminName, String adminPsw) throws Exception {
        if (StorageInfo.getStorageInfo().getUser().isLogged()) {
            throw new LogException("User must be logged out to create a new storage");
        }

        File storageDir = FileUtils.getFile(path + "/" + storageName);
        if (!storageDir.exists()) {
            try {
                FileUtils.forceMkdir(storageDir);

                File configFile = new File(path + "/" + storageName + "/config.json");
                File usersFile = new File(path + "/" + storageName + "/users.json");

                initConfig(configFile, path + "/" + storageName, adminName);
                initUsers(usersFile, adminName, adminPsw);

                FileUtils.touch(configFile);
                FileUtils.touch(usersFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void editConfig(String path, String maxSize, String maxNumOfFiles, List<String> unsupportedFiles) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", path);
        configMap.put("admin", StorageInfo.getStorageInfo().getUser().getName());
        configMap.put("maxSize", (maxSize != null) ? maxSize : StorageInfo.getStorageInfo().getConfig().getMaxSize());
        configMap.put("maxNumOfFiles", (maxNumOfFiles != null) ? maxNumOfFiles : StorageInfo.getStorageInfo().getConfig().getMaxNumOfFiles());
        configMap.put("unsupportedFiles", (unsupportedFiles != null) ? unsupportedFiles : StorageInfo.getStorageInfo().getConfig().getUnsupportedFiles());

        try {
            Writer writer = new FileWriter(path + "/config.json");
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editUsers(String path, String name, String password, Privilege privilege) {
        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("name", name);
        usersMap.put("password", password);
        usersMap.put("privilege", privilege);

        try {
            Writer writer = new FileWriter(path + "/users.json", true);
            writer.append(",");
            new Gson().toJson(usersMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initConfig(File configFile, String path, String adminName) {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("path", path);
        configMap.put("admin", adminName);
        configMap.put("maxSize", "UN");
        configMap.put("maxNumOfFiles", "UN");
        configMap.put("unsupportedFiles", null);  // proveriti da li pravi praznu listu

        try {
            Writer writer = new FileWriter(configFile);
            new Gson().toJson(configMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUsers(File usersFile, String adminName, String adminPsw) {
        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("name", adminName);
        usersMap.put("password", adminPsw);
        usersMap.put("privilege", Privilege.ADMIN);

        try {
            Writer writer = new FileWriter(usersFile);
            new Gson().toJson(usersMap, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
