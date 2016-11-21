package org.onlab.security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdn on 16. 5. 23.
 */
public class OnosApiMapper {
    public List<String> interfaces;
    public String interfaceName;
    public String implementedClassName;
    public String permission;

    public OnosApiMapper(String iName, String cName, String perm) {
        interfaceName = iName;
        implementedClassName = cName;
        permission = perm;
        interfaces = new ArrayList<String>();
    }

    public OnosApiMapper(String iName, String cName) {
        interfaceName = iName;
        implementedClassName = cName;
        interfaces = new ArrayList<String>();
    }
}
