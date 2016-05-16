package org.onlab.security;

import java.util.HashMap;

/**
 * Created by sdn on 16. 2. 23.
 */

/**
 * Class to check whether invoked class and method is security-sensitive class and method or not.
 *
 * NOTE : Java's native permission and OSGI permission are not supported yet.
 */
public class PermissionComparer {

    public PermissionComparer() {

    }
    public String check(String visitClass, String visitMethod) {
        return appPermcheck(visitClass, visitMethod);
    }

    /**
     * Method to check the permission based on invoked class and method.
     * @param visitClass current invoked class name
     * @param visitMethod current invoked method name
     * @return matched permission
     */
    private String appPermcheck(String visitClass, String visitMethod) {
        //TODO: TBD
    }

    /**
     * Method to set the permission list of PermissionStore.
     * @param permStore storehouse of permission information
     */
    private void setStorePermList(PermissionStore permStore) {
        permStore.setPermList();
    }

    /**
     * Method to get the permission list from PermissionStore.
     * @param permStore storehouse of permission information
     * @return list of permission
     */
    private HashMap<Integer, String> getStorePermList(PermissionStore permStore) {
        return permStore.getPermList();
    }
}


