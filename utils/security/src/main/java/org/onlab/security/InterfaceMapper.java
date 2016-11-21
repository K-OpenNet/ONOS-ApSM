package org.onlab.security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdn on 16. 6. 7.
 */
public class InterfaceMapper {
    public List<String> interfaces;
    public String implementedClass;

    public InterfaceMapper() {
        interfaces = new ArrayList<String>();
    }
}
