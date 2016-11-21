package org.onlab.security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdn on 16. 5. 23.
 */
public final class OnosApiStore {

    public List<OnosApiMapper> mapper;
    public List<OnosApiMapper> nmapper;
    private List<String> permList;
    private List<String> serviceList;

    private static OnosApiStore instance = new OnosApiStore();

    private OnosApiStore() {
        mapper = new ArrayList<OnosApiMapper>();
        nmapper = new ArrayList<OnosApiMapper>();
        permList = new ArrayList<String>();
    }

    public void putMapper(String interfaceName, String className, String permission) {
        OnosApiMapper obj = new OnosApiMapper(interfaceName, className, permission);
        mapper.add(obj);
    }

    public void putnMapper(String interfaceName, String className) {
        OnosApiMapper obj = new OnosApiMapper(interfaceName, className);
        nmapper.add(obj);
    }

    public List<OnosApiMapper> getMappers() {
        return mapper;
    }

    public List<OnosApiMapper> getnMappers() {
        return nmapper;
    }

    public List<String> getPermList() {
        return permList;
    }

    public static OnosApiStore getInstance() {
        return instance;

    }
}
