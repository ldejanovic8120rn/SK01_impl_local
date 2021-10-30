package com.storageimpl;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Testing {

    public static void main(String[] args) {

        try {
            //cao laki
            // komentar
            // novo
            File configFile = new File("/Users/lazardejanovic/Desktop/MyStorage/config.json");
            File usersFile = new File("/Users/lazardejanovic/Desktop/MyStorage/users.json");

            Map<String, Object> configMap = new HashMap<>();
            configMap.put("path", "/Users/lazardejanovic/Desktop/MyStorage");
            configMap.put("admin", "adminName");
            configMap.put("maxSize", "");
            configMap.put("maxNumOfFiles", "");
            configMap.put("unsupportedFiles", null);


            try {
                Writer writer = new FileWriter(configFile);
                writer.append(",");
                new Gson().toJson(configMap, writer);
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            FileUtils.touch(configFile);
            FileUtils.touch(usersFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
