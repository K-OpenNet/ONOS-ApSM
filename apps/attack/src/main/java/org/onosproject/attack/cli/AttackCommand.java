/*
 * Copyright 2015 Open Networking Laboratory
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

package org.onosproject.attack.cli;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.attack.Attack;
import org.onosproject.cli.AbstractShellCommand;


/**
 * Application security policy review commands.
 */
@Command(scope = "onos", name = "attack",
        description = "Attack the ONOS")
public class AttackCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "name", description = "Attack name",
            required = true, multiValued = false)
    String name = null;

    @Argument(index = 1, name = "type", description = "Start or Stop",
            required = true, multiValued = false)
    String type = null;

    @Override
    protected void execute() {
        if (name == null) {
            print("Please retype the command\n");
            print("Usage : attack [attack type]\n");
            print("[attack type = NB(Nortbound), SB(Southbound), SC(SystemCommand)\n");
            return;
        }


        if (name.equals("SB-LinkProviderService")) {
            printStart("SB(Southbound API)", "LinkProviderService abuse");
            get(Attack.class).sbLinkProviderStart();
            printComplete();
        } else if(name.equals(("NB-FlowRuleService"))) {
            if (type.equals("start")) {
                printStart("NB(Northbound API)", "FlowRuleService abuse");
                get(Attack.class).nbFlowRuleStart();
            } else if (type.equals("stop")) {
                get(Attack.class).nbFlowRuleStop();
                printStop();
            }
        } else if (name.equals("NB-DeviceAdminService")) {
            if (type.equals("start")) {
                printStart("NB(Northbound ADMIN API)", "DeviceAdminService abuse");
                get(Attack.class).deviceRemoveAttackStart();
            } else if (type.equals("stop")) {
                get(Attack.class).deviceRemoveAttackStop();
                printStop();
            }
        } else if (name.equals("SC-SystemExit")) {
            printStart("SC(System Command Execution)", "System command abuse");
            get(Attack.class).systemExit();
            printComplete();
        } else {
            print("Unsupported attack\n");
            return;
        }
    }

    private void printStart(String attackType, String attackName) {
        print("\n*******************************");
        print("          ATTACK START           ");
        print("*******************************");

        print("Attack type: %s", attackType);
        print("Attack name: %s ", attackName);
    }

    private void printComplete() {
        print("\n*******************************");
        print("          ATTACK Complete           ");
        print("*******************************");
    }

    private void printStop() {
        print("\n*******************************");
        print("          ATTACK STOP           ");
        print("*******************************");
    }
}
