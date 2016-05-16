/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onosproject.bgp.controller;

import org.onosproject.bgpio.protocol.linkstate.BgpNodeLSNlriVer4;

/**
 * Allows for providers interested in node events to be notified.
 */
public interface BgpNodeListener {

    /**
     * Notifies that the node was added.
     *
     * @param nodeNlri node rechability info
     */
    void addNode(BgpNodeLSNlriVer4 nodeNlri);

    /**
     * Notifies that the node was removed.
     *
     * @param nodeNlri node rechability info
     */
    void deleteNode(BgpNodeLSNlriVer4 nodeNlri);
}
