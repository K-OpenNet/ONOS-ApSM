package org.onlab.security.cli;

import org.apache.karaf.features.BundleInfo;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.app.ApplicationAdminService;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.core.Application;
import org.onosproject.core.ApplicationId;
import org.onlab.security.PolicyGenManager;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages application inventory.
 */
@Command(scope = "onos", name = "genpolicy",
        description = "Generate policy for SM-ONOS automatically")
public class GenPolicyCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "name",
            description = "Application name",
            required = true, multiValued = false)
    String name = null;

    @Override
    protected void execute() {
        //TODO: TBD
    }

    private Set<String> getBundleLocations(Application app) {
        //TODO: TBD
        return null;
    }

}