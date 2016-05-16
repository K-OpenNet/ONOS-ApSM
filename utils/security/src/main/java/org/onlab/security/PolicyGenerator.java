package org.onlab.security;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Simple class to generate app.xml policy file based on given application's necessary permission automatically.
 */
public class PolicyGenerator {

    private final String onosRoot = "ONOS_ROOT";
    private final String indent = "{http://xml.apache.org/xslt}indent-amount";
    private final String xmlFileName = "/app-policy.xml";
    private final String appPath = "/apps/";
    private final Logger log = getLogger(getClass());
    private DocumentBuilderFactory db;
    private DocumentBuilder parser;
    private Document doc;

    public PolicyGenerator() {
        db = DocumentBuilderFactory.newInstance();
        try {
            parser = db.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.warn("Error while initialize PolicyGenerator : " + e.getMessage());
        }
        doc = parser.newDocument();
    }

    /**
     * Method to generate policy file.
     * @param appName application name of given application
     * @param artifactId artifactId of given application
     * @param appPerm necessary permission list of analyzed application
     */
    public void genAppxml(String appName, String artifactId, ArrayList<String> appPerm) {
        Element root = doc.createElement("app");
        root.setAttribute("name", appName);
        root.setAttribute("origin", "ON.Lab");
        root.setAttribute("version", "${project.version}");
        root.setAttribute("featuresRepo",
                "mvn:${project.groupId}/${project.artifactId}/${project.version}/xml/features");
        root.setAttribute("features", "${project.artifactId}");
        doc.appendChild(root);

        Element description = doc.createElement("description");
        description.setTextContent("${project.description}");
        root.appendChild(description);

        Element artifact = doc.createElement("artifact");
        artifact.setTextContent("mvn:${project.groupId}/${project.artifactId}/${project.version}");
        root.appendChild(artifact);

        Element security = doc.createElement("security");
        root.appendChild(security);

        Element role = doc.createElement("role");
        role.setTextContent("USER");
        security.appendChild(role);

        Element permissions = doc.createElement("permissions");
        security.appendChild(permissions);

        for (String perm : appPerm) {
            Element ap = doc.createElement("app-perm");
            ap.setTextContent(perm);
            permissions.appendChild(ap);
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(indent, "4");
            StreamResult xmlFile = new StreamResult(new File(System.getenv(onosRoot)
                    + appPath + artifactId + xmlFileName));
            DOMSource ds = new DOMSource(doc);
            transformer.transform(ds, xmlFile);
        } catch (TransformerException e) {
            log.warn("Error while write the policy file : " + e.getMessage());
        }
    }
}