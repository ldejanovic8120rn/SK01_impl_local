package com.storageimpl;

import com.StorageManager;

public class Local {

    static {
        StorageManager.registerStorage(new LocalStorage(), new LocalCreate(), new LocalDelete(), new LocalOperations());
    }
}
