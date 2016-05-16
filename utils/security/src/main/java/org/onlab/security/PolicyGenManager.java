/*
 * Copyright 2016 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onlab.security;

import org.apache.bcel.classfile.ClassParser;
import org.slf4j.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Simple class to generate app.xml policy file based on given application's necessary permission automatically.
 */
public class PolicyGenManager {

    private final Logger log = getLogger(getClass());
    private final String  defaultRepoPath = "/.m2/repository/";
    private String homePath;
    private ArrayList<String> jarPaths;
    private ArrayList<String> appPermSet;
    private String appName;
    private String artifactId;

    public PolicyGenManager(Set<String> locations, String name) {
        appName = name;
        appPermSet = new ArrayList<>();
        jarPaths = new ArrayList<>();
        homePath = System.getenv("HOME");
        boolean feature = true;
        String[] bundleLocations = locations.toArray(new String[locations.size()]);

        for (String bundleLocation : bundleLocations) {
            if (feature) {
                artifactId = getArtifactId(bundleLocation);
            }
            jarPaths.add(makeJarpath(bundleLocation));
            feature = false;
        }
        System.out.println("app Name = " + appName);
        for (String location : locations) {
            System.out.println("location : " + location);
        }
        System.out.println("Policy gen");
    }

    /**
     * Method to make the jar path based on the bundle location.
     * @param bundleLocation location of bundle
     * @return location of bundle jar file
     */
    private String makeJarpath(String bundleLocation) {
        String[] parsedLocation = locationParser(bundleLocation);
        String jarPath = "";

        jarPath = jarPath + homePath + defaultRepoPath;
        for (String loc : parsedLocation) {
            jarPath = jarPath + loc + "/";
        }
        jarPath = jarPath + parsedLocation[parsedLocation.length - 2] + "-"
                + parsedLocation[parsedLocation.length - 1] + ".jar";

        return jarPath;
    }

    /**
     * Method to parse the path of bundle location.
     * @param bundleLocation location of bundle
     * @return parsed path
     */
    private String[] locationParser(String bundleLocation) {
        String[] parsedPath = bundleLocation.split("/");
        parsedPath[0] = parsedPath[0].substring(parsedPath[0].indexOf(':') + 1, parsedPath[0].length());
        parsedPath[0] = parsedPath[0].replace('.', '/');

        return parsedPath;
    }

    /**
     * Method to extract the app artifactId from the bundle location.
     * @param bundleLocation location of bundle
     * @return artifactId of app
     */
    private String getArtifactId(String bundleLocation) {
        String[] parsedPath = bundleLocation.split("/");
        String[] sparsedPath = parsedPath[1].split("-");
        return sparsedPath[2];
    }

    /**
     * Start point of the policy generation.
     */
    public void start() {


        ClassParser cp;

        try {
            for (String bundlePath : jarPaths) {
                if (bundlePath != null) {
                    File bundleFile = new File(bundlePath);

                    if (!bundleFile.exists()) {
                        log.warn("Jar file " + bundlePath + "does not exist" + "\n");
                    }

                    JarFile bundleJar = new JarFile(bundleFile);
                    Enumeration<JarEntry> bundleEntries = bundleJar.entries();
                    File file = new File("/home/sdn/call.txt"); //
                    FileWriter fw = new FileWriter(file, true); //
                    fw.write("<"+bundlePath+">"+"\n"); //
                    fw.flush();//
                    fw.close();//
                    while (bundleEntries.hasMoreElements()) {
                        JarEntry entry = bundleEntries.nextElement();
                        if (entry.isDirectory()) {
                            continue;
                        }

                        if (!entry.getName().endsWith(".class")) {
                            continue;
                        }

                        cp = new ClassParser(bundlePath, entry.getName());
                        ClassVisitor classVisitor = new ClassVisitor(cp.parse(), appPermSet);
                        classVisitor.start();

                    }

                    PolicyGenerator xg = new PolicyGenerator();
                    xg.genAppxml(appName, artifactId, appPermSet);
                }
            }
        } catch (IOException e) {
            log.warn("Error while processing bundle jar file : " + e.getMessage());
        }
    }
}
