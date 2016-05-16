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
        print("[Start] Automatic Policy Generation");
        ApplicationAdminService applicationAdminService = get(ApplicationAdminService.class);
        ApplicationId appId = applicationAdminService.getId(name);
        if (appId == null) {
            print("No such application: %s", name);
            return;
        }
        Application app = applicationAdminService.getApplication(appId);
        Set<String> bundleLocations = getBundleLocations(app);
        if (bundleLocations == null) {
            print("Bundle information is not available");
            return;
        } else {
            for (String location : bundleLocations) {
                System.out.println("location" + location);
            }
            PolicyGenManager policyManager = new PolicyGenManager(bundleLocations, appId.name());
            policyManager.start();
        }
    }

    private Set<String> getBundleLocations(Application app) {
        FeaturesService featuresService = get(FeaturesService.class);
        Set<String> locations = new HashSet<>();
        for (String name : app.features()) {
            try {
                Feature feature = featuresService.getFeature(name);
                locations.addAll(
                        feature.getBundles().stream().map(BundleInfo::getLocation).collect(Collectors.toList()));
            } catch (Exception e) {
                print("[Exception] Fail to find bundle location.");
                return locations;
            }
        }
        return locations;
    }

}