package com.storageimpl;

import com.storage.Operations;
import com.utils.FileMetadata;
import com.utils.Privilege;
import com.utils.StorageInfo;
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

public class LocalOperations extends Operations {

    @Override
    public List<FileMetadata> getAllFiles(String path) throws Exception {
        path = StorageInfo.getStorageInfo().getConfig().getPath() + path;

        if (!StorageInfo.getStorageInfo().checkUser()) {
            throw new Exception("Korisnik nije logovan");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(path)) {
            throw new Exception("Ne postoji zadata putanja u skladistu");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, FalseFileFilter.FALSE);
        return getFileMetadata(files);
    }

    @Override
    public List<FileMetadata> getAllDirectories(String path) throws Exception {
        path = StorageInfo.getStorageInfo().getConfig().getPath() + path;

        if (!StorageInfo.getStorageInfo().checkUser()) {
            throw new Exception("Korisnik nije logovan");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(path)) {
            throw new Exception("Ne postoji zadata putanja u skladistu");
        }

        File directory = new File(path);
        File[] files = directory.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        return getFileMetadata(Arrays.asList(files));
    }

    @Override
    public List<FileMetadata> getAllFilesRecursive(String path) throws Exception {
        path = StorageInfo.getStorageInfo().getConfig().getPath() + path;

        if (!StorageInfo.getStorageInfo().checkUser()) {
            throw new Exception("Korisnik nije logovan");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(path)) {
            throw new Exception("Ne postoji zadata putanja u skladistu");
        }

        List<File> files = (List<File>) FileUtils.listFiles(new File(path), HiddenFileFilter.VISIBLE, TrueFileFilter.TRUE);
        return getFileMetadata(files);
    }

    @Override
    public void download(String path) throws Exception {
        path = StorageInfo.getStorageInfo().getConfig().getPath() + path;

        if (!StorageInfo.getStorageInfo().checkUser(Privilege.ADMIN, Privilege.RDCD, Privilege.RD)) {
            throw new Exception("Korisnik nije logovan ili nema privilegiju");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(path)) {
            throw new Exception("Ne postoji zadata putanja u skladistu");
        }

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
    public void uploadFile(String fromPath, String toPath) throws Exception {
        toPath = StorageInfo.getStorageInfo().getConfig().getPath() + toPath;

        if (!StorageInfo.getStorageInfo().checkUser(Privilege.ADMIN, Privilege.RDCD)) {
            throw new Exception("Korisnik nije logovan ili nema privilegiju");
        }

        if (!LocalFileChecker.getLFC().ckeckPath(fromPath)) {
            throw new Exception("Fajl ne postoji ili se nalazi u okviru trenutnog skladista");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(toPath)) {
            throw new Exception("Ne postoji zadata putanja u skladistu");
        }

        String extension = fromPath.split("/")[fromPath.split("/").length - 1].split("\\.")[1];
        if (!LocalFileChecker.getLFC().ckeckExtention(extension)) {
            throw new Exception("Nedozvoljena ekstenzija");
        }

        if (!LocalFileChecker.getLFC().checkNumOfFiles()) {
            throw new Exception("Prekoracen broj fajlova");
        }

        File file = new File(fromPath);
        if (!LocalFileChecker.getLFC().checkMaxSize(FileUtils.sizeOf(file))) {
            throw new Exception("Prekoracena velicina skladista");
        }

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
    public void moveFile(String fromPath, String toPath) throws Exception {
        fromPath = StorageInfo.getStorageInfo().getConfig().getPath() + fromPath;
        toPath = StorageInfo.getStorageInfo().getConfig().getPath() + toPath;

        File file = new File(fromPath);

        if (!StorageInfo.getStorageInfo().checkUser(Privilege.ADMIN, Privilege.RDCD)) {
            throw new Exception("Korisnik nije logovan ili nema privilegiju");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(fromPath)) {
            throw new Exception("Ne postoji zadata putanja u skladistu za - fromPath");
        }

        if (!LocalFileChecker.getLFC().ckeckStoragePath(toPath)) {
            throw new Exception("Ne postoji zadata putanja u skladistu za - toPath");
        }

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
