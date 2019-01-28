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
package org.eclipse.che.selenium.core.workspace;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.inject.Named;
import org.eclipse.che.api.workspace.shared.dto.WorkspaceConfigDto;
import org.eclipse.che.selenium.core.client.TestWorkspaceServiceClient;
import org.eclipse.che.selenium.core.client.TestWorkspaceServiceClientFactory;
import org.eclipse.che.selenium.core.user.DefaultTestUser;
import org.eclipse.che.selenium.core.user.TestUser;
import org.eclipse.che.selenium.core.utils.WorkspaceDtoDeserializer;

@Singleton
public class CodeReadyTestWorkspaceProvider implements TestWorkspaceProvider {
  private CheTestWorkspaceProvider cheTestWorkspaceProvider;

  @Inject(optional = true)
  @com.google.inject.name.Named("env.crw.stack.image.registry")
  private String stackImageRegistry;

  @Inject
  CodeReadyTestWorkspaceProvider(
      @Named("che.workspace_pool_size") String poolSize,
      @Named("che.threads") int threads,
      @Named("workspace.default_memory_gb") int defaultMemoryGb,
      @Named("tests.workspacelogs_dir") String workspaceLogsDir,
      DefaultTestUser defaultUser,
      WorkspaceDtoDeserializer workspaceDtoDeserializer,
      TestWorkspaceServiceClient testWorkspaceServiceClient,
      TestWorkspaceServiceClientFactory testWorkspaceServiceClientFactory,
      TestWorkspaceLogsReader testWorkspaceLogsReader) {
    this.cheTestWorkspaceProvider =
        new CheTestWorkspaceProvider(
            poolSize,
            threads,
            defaultMemoryGb,
            workspaceLogsDir,
            defaultUser,
            workspaceDtoDeserializer,
            testWorkspaceServiceClient,
            testWorkspaceServiceClientFactory,
            testWorkspaceLogsReader);
  }

  @Override
  public TestWorkspace getWorkspace(String workspaceName, TestUser owner) {
    return cheTestWorkspaceProvider.getWorkspace(workspaceName, owner);
  }

  @Override
  public TestWorkspace createWorkspace(
      TestUser owner, int memoryGB, String templateFileName, boolean startAfterCreation)
      throws Exception {
    return cheTestWorkspaceProvider.createWorkspace(
        owner, memoryGB, templateFileName, startAfterCreation);
  }

  public TestWorkspace createWorkspace(
      String name,
      TestUser owner,
      int memoryGB,
      boolean startAfterCreation,
      WorkspaceConfigDto config) {
    if (stackImageRegistry != null) {
      String defaultEnvironmentName = config.getDefaultEnv();
      String oldStackImageAddress =
          config.getEnvironments().get(defaultEnvironmentName).getRecipe().getContent();
      String newStackImageAddress = oldStackImageAddress.replace("^[^/]*", stackImageRegistry);
      config
          .getEnvironments()
          .get(defaultEnvironmentName)
          .getRecipe()
          .setContent(newStackImageAddress);
    }

    return cheTestWorkspaceProvider.createWorkspace(
        name, owner, memoryGB, startAfterCreation, config);
  }

  @Override
  public void shutdown() {
    cheTestWorkspaceProvider.shutdown();
  }
}
