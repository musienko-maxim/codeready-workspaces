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
package com.redhat.codeready.selenium.dashboard.workspaces.details;

import org.eclipse.che.selenium.dashboard.workspaces.details.WorkspaceDetailsMachineActionsTest;

public class CodereadyWorkspaceDetailsMachineActionsTest
    extends WorkspaceDetailsMachineActionsTest {

  protected String getImageName() {
    return "brew-pulp-docker01.web.prod.ext.phx2.redhat.com:8888/codeready-workspaces/stacks-java";
  }
}
