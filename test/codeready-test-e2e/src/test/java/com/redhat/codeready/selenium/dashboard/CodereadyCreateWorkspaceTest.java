/*
* Copyright (c) 2018 Red Hat, Inc.

* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*   Red Hat, Inc. - initial API and implementation
*/
package com.redhat.codeready.selenium.dashboard;

import static org.eclipse.che.selenium.pageobject.dashboard.NewWorkspace.Stack.JAVA;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.google.inject.Inject;
import org.eclipse.che.selenium.dashboard.CreateWorkspaceTest;
import org.eclipse.che.selenium.pageobject.dashboard.NewWorkspace;

/** @author Serhii Skoryk */
public class CodereadyCreateWorkspaceTest extends CreateWorkspaceTest {

  @Inject protected NewWorkspace newWorkspace;

  protected String getProjectSampleName() {
    return "kitchensink-example";
  }

  protected String getProjectSampleDescription() {
    return "This is the kitchensink JBoss quickstart app";
  }

  protected void checkMachinesRam() {
    String machineName = "dev-machine";

    // change the RAM number by the increment and decrement buttons
    newWorkspace.clickOnAllStacksTab();
    newWorkspace.selectStack(JAVA);
    assertTrue(newWorkspace.isMachineExists(machineName));
    assertEquals(newWorkspace.getRAM(machineName), 2.0);
    newWorkspace.clickOnIncrementMemoryButton(machineName);
    assertEquals(newWorkspace.getRAM(machineName), 2.1);
    newWorkspace.clickOnDecrementMemoryButton(machineName);
    newWorkspace.clickOnDecrementMemoryButton(machineName);
    newWorkspace.clickOnDecrementMemoryButton(machineName);
    assertEquals(newWorkspace.getRAM(machineName), 1.8);

    // type number of memory in the RAM field
    newWorkspace.setMachineRAM(machineName, 5.0);
    assertEquals(newWorkspace.getRAM(machineName), 5.0);
  }
}
