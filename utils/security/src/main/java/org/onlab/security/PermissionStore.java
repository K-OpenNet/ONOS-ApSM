package org.onlab.security;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Singleton class to store the permissions of security-sensitive class and method.
 */
public final class PermissionStore {

    private final String onosRoot = "ONOS_ROOT";
    private final String nullText = "#text";
    private final String xmlTag = "onos-api";
    private final Logger log = getLogger(getClass());
    private static PermissionStore instance = new PermissionStore();
    private HashMap<Integer, String> permList;
    private Document doc;
    private final String permXml = "/utils/security/perm.xml";

    private PermissionStore() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            String onosPath = System.getenv(onosRoot);
            File permFile = new File(onosPath + permXml);
            doc = docBuilder.parse(permFile);
            permList = new HashMap<>();
        } catch (Exception e) {
            log.warn("Error while initialize PermissionStore : " + e.getMessage());
        }
    }

    /**
     * Method to set the permission list.
     */
    public void setPermList() {

        NodeList classNodes = doc.getElementsByTagName(xmlTag);

        for (int i = 0; i < classNodes.getLength(); i++) {
            for (Node classNode = classNodes.item(i).getFirstChild(); classNode != null;
                 classNode = classNode.getNextSibling()) {
                if (classNode.getNodeName().equals(nullText)) {
                    continue;
                }

                for (Node methodNode = classNode.getFirstChild(); methodNode != null;
                     methodNode = methodNode.getNextSibling()) {
                    if (methodNode.getNodeName().equals(nullText)) {
                        continue;
                    }
                    int key = (classNode.getNodeName().hashCode()) ^ (methodNode.getNodeName().hashCode());
                    String value = methodNode.getTextContent();

                    if (permList.get(key) == null) {
                        permList.put(key, value);
                    }
                }
            }
        }
    }

    /**
     * Method to get the permission list.
     * @return list of permission
     */
    public HashMap<Integer, String> getPermList() {
        return permList;
    }

    /**
     * Method to get the instance of PermissionStore.
     * @return PermissionStore instance
     */
    public static PermissionStore getInstance() {
        return instance;
    }
}
