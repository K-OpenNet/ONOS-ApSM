package org.onosproject.attack;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.onlab.osgi.ServiceNotFoundException;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.link.LinkProvider;
import org.onosproject.net.link.LinkProviderRegistry;
import org.onosproject.net.link.LinkProviderService;
import org.onosproject.net.link.LinkDescription;
import org.onosproject.net.link.DefaultLinkDescription;
import org.onosproject.net.provider.AbstractProvider;
import org.onosproject.net.provider.ProviderId;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.onosproject.net.Link.Type.DIRECT;

/**
 * Attack Provider.
 */
@Component(immediate = true)
@Service(value = AttackProvider.class)
public class AttackProvider extends AbstractProvider implements LinkProvider {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private LinkProviderService linkProviderService;
    public ConnectPoint source;
    public ConnectPoint destination;
    public AttackProvider() {
        super(new ProviderId("attack", "org.onosproject.attack.AttackProvider"));
    }

    public void removeattack(ConnectPoint connectPoint) {
        linkProviderService.linksVanished(connectPoint);
    }
    public void addattack(ConnectPoint src, ConnectPoint dst) {
        source = src;
        destination = dst;
        LinkDescription desc = new DefaultLinkDescription(source, destination, DIRECT);
        linkProviderService.linkDetected(desc);
    }

    public static <T> T getService(Class<T> serviceClass) {
        BundleContext bc = FrameworkUtil.getBundle(serviceClass).getBundleContext();
        if (bc != null) {
            ServiceReference<T> reference = bc.getServiceReference(serviceClass);
            if (reference != null) {
                T impl = bc.getService(reference);
                if (impl != null) {
                    return impl;
                }
            }
        }
        throw new ServiceNotFoundException("Service " + serviceClass.getName() + " not found");
    }

    @Activate
    protected void activate() {
        LinkProviderRegistry linkProviderRegistry = getService(LinkProviderRegistry.class);
        log.info("Attack provider Started");
        linkProviderService = linkProviderRegistry.register(this);
    }

    @Deactivate
    protected void deactivate() {
        log.info("Attack provider Stopped");
    }
}
