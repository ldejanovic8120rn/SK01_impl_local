package com.storageimpl;

import com.storage.Operations;
import com.utils.FileMetadata;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// TODO - proveriti za sve metode da li se fajlovi i putanje nalaze u skladistu
public class LocalOperations extends Operations {

    @Override
    public List<FileMetadata> getAllFiles(String path) {
        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        return getFileMetadata(files);
    }

    @Override
    public List<FileMetadata> getAllDirectories(String path) {
        File directory = new File("/Users/lazardejanovic/Desktop/MyStorage");
        File[] files = directory.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        return getFileMetadata(Arrays.asList(files));
    }

    @Override
    public List<FileMetadata> getAllFilesRecursive(String path) {
        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, TrueFileFilter.TRUE);
        return getFileMetadata(files);
    }

    @Override
    public void download(String path) {
        File file = new File(path);

        try {
            if (file.isDirectory()) {
                FileUtils.moveDirectoryToDirectory(file, new File("./"), false);
            }
            else {
                FileUtils.moveFileToDirectory(file, new File("./"), false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFile(String fromPath, String toPath) {
        File file = new File(fromPath);

        try {
            if (file.isDirectory()) {
                FileUtils.moveDirectoryToDirectory(file, new File(toPath), false);
            }
            else {
                FileUtils.moveFileToDirectory(file, new File(toPath), false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveFile(String fromPath, String toPath) {
        File file = new File(fromPath);

        try {
            if (file.isDirectory()) {
                FileUtils.moveDirectoryToDirectory(file, new File(toPath), false);
            }
            else {
                FileUtils.moveFileToDirectory(file, new File(toPath), false);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<FileMetadata> getFileMetadata(List<File> files) {
        List<FileMetadata> filesMD = new ArrayList<>();

        for (File file: files) {
            String name = file.getName();
            Date cd = getCreationDate(file);
            Date lmd = new Date(file.lastModified());

            FileMetadata fmd = new FileMetadata(name, cd, lmd);
            filesMD.add(fmd);
        }

        return filesMD;
    }

    private Date getCreationDate(File file) {
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new Date(attr != null ? attr.creationTime().toMillis() : 0);
    }
}
