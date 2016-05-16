package org.onlab.security.cli;

import org.onosproject.app.ApplicationService;
import org.onosproject.cli.AbstractChoicesCompleter;
import org.onosproject.core.Application;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.onosproject.app.ApplicationState.INSTALLED;
import static org.onosproject.cli.AbstractShellCommand.get;

/**
 * Created by sdn on 16. 3. 30.
 */
public class GenPolicyCommandCompleter extends AbstractChoicesCompleter {
    @Override
    public List<String> choices() {
        //TODO: TBD
    }
}
