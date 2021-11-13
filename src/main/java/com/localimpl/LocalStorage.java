package com.localimpl;

import com.exception.LogException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.storage.Storage;
import com.utils.Privilege;
import com.utils.StorageInfo;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalStorage extends Storage {

    @Override
    public File getConfig(String path) {
        return FileUtils.getFile(path + "/config.json");
    }
    @Override
    public File getUsers(String path) {
        return FileUtils.getFile(path + "/users.json");
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
        JsonObject user = new JsonObject();
        user.addProperty("name", name);
        user.addProperty("password", password);
        user.addProperty("privilege", String.valueOf(privilege));

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getUsers(path)));
            String line = bufferedReader.readLine();
            line = line.replace("]", "");

            Writer writer = new FileWriter(path + "/users.json", false);
            writer.append(line);
            writer.append(",");

            new Gson().toJson(user, writer);
            writer.append("]");
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
        JsonObject user = new JsonObject();
        user.addProperty("name", adminName);
        user.addProperty("password", adminPsw);
        user.addProperty("privilege", String.valueOf(Privilege.ADMIN));

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(user);
        try {
            Writer writer = new FileWriter(usersFile);
            new Gson().toJson(jsonArray, writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
