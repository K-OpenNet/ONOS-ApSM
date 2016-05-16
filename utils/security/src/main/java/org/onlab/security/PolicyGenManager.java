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
        //TODO: TBD
        return null;
    }

    /**
     * Method to make the jar path based on the bundle location.
     * @param bundleLocation location of bundle
     * @return location of bundle jar file
     */
    private String makeJarpath(String bundleLocation) {
        //TODO: TBD
        return null;
    }

    /**
     * Method to parse the path of bundle location.
     * @param bundleLocation location of bundle
     * @return parsed path
     */
    private String[] locationParser(String bundleLocation) {
        //TODO: TBD
        return null;
    }

    /**
     * Method to extract the app artifactId from the bundle location.
     * @param bundleLocation location of bundle
     * @return artifactId of app
     */
    private String getArtifactId(String bundleLocation) {
        //TODO: TBD
        return null;
    }

    /**
     * Start point of the policy generation.
     */
    public void start() {
        //TODO: TBD
    }
}
